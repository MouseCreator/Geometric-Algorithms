package mouse.project.algorithm.sweep;

import mouse.project.algorithm.data.Point;
import mouse.project.algorithm.data.PointSet;
import mouse.project.algorithm.heap.BinaryHeap;
import mouse.project.algorithm.heap.Heap;
import mouse.project.algorithm.sweep.struct.Arc;
import mouse.project.algorithm.sweep.struct.ArcNode;
import mouse.project.algorithm.sweep.struct.ArcStatus;
import mouse.project.algorithm.sweep.struct.Diagram;
import mouse.project.math.*;

import java.util.*;
public class SweepLine {
    private ArcStatus status;
    private final Heap<Event> eventHeap;
    private Set<CircleEvent> eventsToIgnore;
    private double currentY = 0;
    private Diagram diagram;
    public SweepLine() {
        eventHeap = new BinaryHeap<>(eventComparator);
    }
    private final Comparator<Arc> archComparator = (o1, o2) -> {
        if (o1.equals(o2)) {
            return 0;
        }
        return o1.hashCode() - o2.hashCode();
    };

    private static final Comparator<Event> eventComparator = (e1, e2) -> {
        if (Numbers.dEquals(e1.position().y(), e2.position().y())) {
            if (Numbers.dEquals(e1.position().x(), e2.position().x())) {
                return 0;
            }
            return e1.position().x() > e2.position().x() ? 1 : -1;
        }

        return e1.position().y() > e2.position().y() ? 1 : -1;
    };

    private interface Event {
        FPosition position();
    }

    public static class SiteEvent implements Event {
        private final Point origin;

        public SiteEvent(Point origin) {
            this.origin = origin;
        }

        @Override
        public FPosition position() {
            return origin.getFPosition();
        }
    }
    public static class CircleEvent implements Event {

        @Override
        public FPosition position() {
            return FPosition.zeros();
        }
    }

    public void scan(PointSet pointSet) {
        List<Point> all = new ArrayList<>(pointSet.getPoints());
        generateEventsFrom(all);
        scanEvents();
    }

    private void generateEventsFrom(List<Point> all) {
        all.forEach(e -> eventHeap.insert(new SiteEvent(e)));
    }

    private void scanEvents() {
        while (!eventHeap.isEmpty()) {
            Event nextEvent = eventHeap.extractMin();
            currentY = nextEvent.position().y();
            List<Event> eventList = new ArrayList<>();
            eventList.add(nextEvent);
            while (!eventHeap.isEmpty() && eventComparator.compare(eventHeap.minimum(), nextEvent) == 0) {
                eventList.add(eventHeap.extractMin());
            }
            for (Event e : eventList) {
                handleEvent(e);
            }
        }
    }

    private void handleEvent(Event e) {
        if (e instanceof SiteEvent) {
            handleSiteEvent((SiteEvent) e);
        }
        else if (e instanceof CircleEvent) {
            handleCircleEvent((CircleEvent) e);
        } else {
            throw new IllegalArgumentException("Cannot handle event: " + e);
        }
    }
    private void handleSiteEvent(SiteEvent e) {
        FPosition Pi = e.position();
        if (status.isEmpty()) {
            Arc arc = createArc(Pi);
            status.insert(arc);
            return;
        }
        ArcNode arcNode = status.getAbove(Pi);
        Arc arc = arcNode.getArc();
        Collection<CircleEvent> circleEvents = arc.getCircleEvents();
        eventsToIgnore.addAll(circleEvents);
        FPosition Pj = arc.getOrigin();
        arcNode.splitAndRebalance();
        diagram.addEdge(Pi, Pj);
    }

    private Arc createArc(FPosition position) {
        return null;
    }

    private void handleCircleEvent(CircleEvent e) {

    }


}
