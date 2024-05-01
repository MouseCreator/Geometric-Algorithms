package mouse.project.algorithm.sweep;

import mouse.project.algorithm.heap.BinaryHeap;
import mouse.project.algorithm.heap.Heap;
import mouse.project.math.Position;
import mouse.project.math.Vector2;

import java.util.*;

public class SweepLine {
    private Status<TSegment> status;
    private final Heap<Event> eventHeap;

    public SweepLine() {
        eventHeap = new BinaryHeap<>(eventComparator);
    }

    private static final Comparator<Event> eventComparator = (e1, e2) -> {
        if (e1.position().y() < e2.position().y()) {
            return -1;
        }
        if (e1.position().y() > e2.position().y()) {
            return 1;
        }
        return e1.position().x() - e2.position().x();
    };

    private static final Comparator<TSegment> angleComparator = (s1, s2) -> {
        Vector2 v1 = s1.direction();
        Vector2 v2 = s2.direction();
        Vector2 left = Vector2.of(1, 0);
        double diff = left.cos(v1) - left.cos(v2);
        double tolerance = 0.0001;
        if (Math.abs(diff) < tolerance) {
            return 0;
        }
        if (diff < -tolerance) {
            return -1;
        }
        return 1;
    };

    private interface Event {
        Position position();
    }

    private record StartEvent(Position position, TSegment segment) implements Event {
    }

    private record EndEvent(Position position, TSegment segment) implements Event {
    }

    private record IntersectionEvent(Position position, TSegment s1, TSegment s2) implements Event {
    }

    public void scan(TSegmentSet segmentSet) {
        List<TSegment> all = new ArrayList<>(segmentSet.getAll());
        generateEventsFrom(all);
    }

    private void generateEventsFrom(List<TSegment> all) {
        all.forEach(e -> {
            eventHeap.insert(new StartEvent(e.getUpper(), e));
            eventHeap.insert(new EndEvent(e.getLower(), e));
        });
    }

    private void scanEvents() {
        while (!eventHeap.isEmpty()) {
            Event nextEvent = eventHeap.extractMin();
            List<Event> eventList = new ArrayList<>();
            eventList.add(nextEvent);
            while (eventComparator.compare(eventHeap.minimum(), nextEvent) == 0) {
                eventList.add(eventHeap.extractMin());
            }
            List<IntersectionEvent> intersections = new ArrayList<>();
            eventList.forEach(e -> {
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
                    Neighbors<TSegment> neighbors = status.delete(((EndEvent) e).segment());
                    if (neighbors.hasLeft() && neighbors.hasRight()) {
                        testIntersection(neighbors.left(), neighbors.right());
                    }
                } else if (e instanceof IntersectionEvent) {
                    intersections.add((IntersectionEvent) e);
                } else {
                    throw new IllegalStateException("Unexpected event: " + e);
                }
            });
            detectAllIntersections(eventList);
            processIntersections(intersections);
        }
    }

    private void detectAllIntersections(List<Event> eventList) {
        // all segments in the events intersect at some point
    }

    private void processIntersections(List<IntersectionEvent> intersections) {
        Set<TSegment> set = new HashSet<>();
        intersections.forEach(i -> {
            set.add(i.s1());
            set.add(i.s2());
        });
        status.reorder(set);
    }

    private void testIntersection(TSegment s1, TSegment s2) {
    }


}
