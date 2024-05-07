package mouse.project.algorithm.sweep;

import lombok.Getter;
import mouse.project.algorithm.heap.BinaryHeap;
import mouse.project.algorithm.heap.Heap;
import mouse.project.math.*;

import java.util.*;
public class SweepLine {
    private final Status<TSegment> status;
    private final Heap<Event> eventHeap;
    @Getter
    private final Set<TIntersection> intersectionSet;
    private double currentY = 0;
    private boolean reordering = false;
    public SweepLine() {
        eventHeap = new BinaryHeap<>(eventComparator);
        status = new SegmentStatus(segmentComparator);
        intersectionSet = new HashSet<>();
    }
    private final Comparator<TSegment> segmentComparator = (o1, o2) -> {
        if (o1.equals(o2)) {
            return 0;
        }
        Optional<Double> x1Opt = o1.getX(currentY);
        Optional<Double> x2Opt = o2.getX(currentY);
        if (x1Opt.isEmpty() && x2Opt.isEmpty()) {
            return 0;
        }
        if (x1Opt.isEmpty()) {
            return 1;
        }
        if (x2Opt.isEmpty()) {
            return -1;
        }
        double x1 = x1Opt.get();
        double x2 = x2Opt.get();
        if (!Numbers.dEquals(x1, x2)) {
            return x1 > x2 ? 1 : -1;
        }
        Vector2 v1 = o1.direction();
        Vector2 v2 = o2.direction();
        Vector2 xLeftUnit = Vector2.of(1, 0);
        double diff = xLeftUnit.cos(v1) - xLeftUnit.cos(v2);
        double tolerance = 0.0001;
        if (Math.abs(diff) < tolerance) {
            return 0;
        }
        if (reordering) {
            return diff < -tolerance ? 1 : -1;
        } else {
            return diff < -tolerance ? -1 : 1;
        }
    };

    private static final Comparator<Event> eventComparator = (e1, e2) -> {
        if (Math.abs(e1.position().y() - e2.position().y()) < 0.0001) {
            if (Math.abs(e1.position().x() - e2.position().x()) < 0.0001) {
                return 0;
            }
            return  e1.position().x() > e2.position().x() ? 1 : -1;
        }

        return  e1.position().y() > e2.position().y() ? 1 : -1;
    };

    private interface Event {
        FPosition position();
    }

    private record StartEvent(FPosition position, TSegment segment) implements Event {
        @Override
        public String toString() {
            return "Start " + segment.getId() + ": " + position;
        }
    }

    private record EndEvent(FPosition position, TSegment segment) implements Event {
        @Override
        public String toString() {
            return "End " + segment.getId() + ": " + position;
        }
    }

    private record IntersectionEvent(FPosition position, TSegment s1, TSegment s2) implements Event {
        @Override
        public String toString() {
            return "Intersect " + s1.getId() + " and " + s2.getId() + ": " + position;
        }
    }

    public void scan(TSegmentSet segmentSet) {
        List<TSegment> all = new ArrayList<>(segmentSet.getAll());
        generateEventsFrom(all);
        scanEvents();
    }
    private void generateEventsFrom(List<TSegment> all) {
        all.forEach(e -> {
            eventHeap.insert(new StartEvent(e.getUpper(), e));
            eventHeap.insert(new EndEvent(e.getLower(), e));
        });
    }
    private final Comparator<Event> typeComparator = (e1, e2) -> {
        if (e1 instanceof StartEvent) {
            if (e2 instanceof StartEvent) {
                return 0;
            }
            return 1;
        }
        if (e2 instanceof StartEvent) {
            return -1;
        }
        if (e1 instanceof IntersectionEvent) {
            if (e2 instanceof IntersectionEvent) {
                return 0;
            }
            return 1;
        }
        if (e2 instanceof IntersectionEvent) {
            return -1;
        }
        if (e1 instanceof EndEvent) {
            if (e2 instanceof EndEvent) {
                return 0;
            }
            return 1;
        }
        if (e2 instanceof EndEvent) {
            return -1;
        }
        return 0;
    };
    private void scanEvents() {
        while (!eventHeap.isEmpty()) {
            Event nextEvent = eventHeap.extractMin();
            currentY = nextEvent.position().y();
            List<Event> eventList = new ArrayList<>();
            eventList.add(nextEvent);
            while (!eventHeap.isEmpty() && eventComparator.compare(eventHeap.minimum(), nextEvent) == 0) {
                eventList.add(eventHeap.extractMin());
            }
            eventList.sort(typeComparator);
            List<IntersectionEvent> intersections = new ArrayList<>();
            for (Event e : eventList) {
                if (e instanceof StartEvent) {
                    TSegment segment = ((StartEvent) e).segment();
                    Neighbors<TSegment> neighbors = status.insert(segment);
                    if (neighbors.hasLeft()) {
                        testIntersection(segment, neighbors.left());
                    }
                    if (neighbors.hasRight()) {
                        testIntersection(segment, neighbors.right());
                    }
                } else if (e instanceof EndEvent) {
                    TSegment segment = ((EndEvent) e).segment();
                    Neighbors<TSegment> neighbors = status.delete(segment);
                    if (neighbors.hasLeft() && neighbors.hasRight()) {
                        testIntersection(neighbors.left(), neighbors.right());
                    }
                } else if (e instanceof IntersectionEvent) {
                    intersections.add((IntersectionEvent) e);
                } else {
                    throw new IllegalStateException("Unexpected event: " + e);
                }
            }
            detectAllIntersections(eventList);
            processIntersections(intersections);
        }
    }
    private void detectAllIntersections(List<Event> eventList) {
        if (eventList.isEmpty()) {
            return;
        }
        FPosition position = eventList.get(0).position();
        Set<TSegment> set = new HashSet<>();
        eventList.forEach(e -> {
            if (e instanceof IntersectionEvent) {
                set.add(((IntersectionEvent) e).s1());
                set.add(((IntersectionEvent) e).s2());
            }
            else if (e instanceof StartEvent) {
                set.add(((StartEvent) e).segment);
            }
            else if (e instanceof EndEvent) {
                set.add(((EndEvent) e).segment);
            }
        });
        List<TSegment> list = new ArrayList<>(set);
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < i; j++) {
                TSegment s1 = list.get(i);
                TSegment s2 = list.get(j);
                intersectionSet.add(new TIntersection(s1, s2, position));
            }
        }
    }

    private void processIntersections(List<IntersectionEvent> intersections) {
        Set<TSegment> set = new HashSet<>();
        intersections.forEach(i -> {
            set.add(i.s1());
            set.add(i.s2());
        });
        if (set.isEmpty()) {
            return;
        }
        reordering = true;

        for (TSegment tSegment : set) {
            status.delete(tSegment);
        }
        reordering = false;
        for (TSegment tSegment : set) {
            Neighbors<TSegment> neighbors = status.insert(tSegment);
            if (neighbors.hasLeft() && !set.contains(neighbors.left())) {
                testIntersection(tSegment, neighbors.left());
            }
            if (neighbors.hasRight() && !set.contains(neighbors.right())) {
                testIntersection(tSegment, neighbors.right());
            }
        }

    }

    private boolean testIntersection(TSegment s1, TSegment s2) {
        Optional<FPosition> p1 = findIntersectionPosition(s1, s2);
        p1.ifPresent(position -> eventHeap.insert(new IntersectionEvent(position, s1, s2)));
        return p1.isPresent();
    }
    private Optional<FPosition> findIntersectionPosition(TSegment s1, TSegment s2) {
        GenLine line1 = s1.asLine();
        GenLine line2 = s2.asLine();

        if (line1.overlaps(line2)) {
            if (line1.isParallelToOx()) {
                TSegment rightMost = s1.getLower().x() > s2.getLower().x() ? s1 : s2;
                TSegment leftMost = s1.getLower().x() > s2.getLower().x() ? s2 : s1;
                if (leftMost.getLower().x() > rightMost.getUpper().x()) {
                    return FPosition.opt(rightMost.getUpper().x(), currentY);
                }
                return Optional.empty();
            }
            Optional<Double> x1Opt = line1.calculateX(currentY);
            Optional<Double> x2Opt = line2.calculateX(currentY);
            assert x1Opt.isPresent() && x2Opt.isPresent();
            double x1 = x1Opt.get();
            double x2 = x2Opt.get();
            return (Numbers.dEquals(x1, x2)) ? FPosition.opt(x1, currentY) : Optional.empty();
        }

        Optional<FPosition> pOpt = line1.intersectionPoint(line2);
        if (pOpt.isEmpty()) {
            return Optional.empty();
        }
        FPosition lineIntersection = pOpt.get();
        if (lineIntersection.y() < currentY) {
            return Optional.empty();
        }
        FBox box1 = new FBox(s1.getLower(), s1.getUpper());
        FBox box2 = new FBox(s2.getUpper(), s2.getLower());
        if (box1.contains(lineIntersection) && box2.contains(lineIntersection)) {
            return Optional.of(lineIntersection);
        }
        return Optional.empty();
    }


}
