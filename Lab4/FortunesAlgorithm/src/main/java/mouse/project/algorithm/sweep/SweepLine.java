package mouse.project.algorithm.sweep;

import mouse.project.algorithm.data.Point;
import mouse.project.algorithm.data.PointSet;
import mouse.project.algorithm.heap.BinaryHeap;
import mouse.project.algorithm.heap.Heap;
import mouse.project.algorithm.sweep.struct.SiteStatus;
import mouse.project.algorithm.sweep.struct.Diagram;
import mouse.project.math.*;

import java.util.*;
public class SweepLine {
    private SiteStatus status;
    private final Heap<Event> eventHeap;
    private Set<CircleEvent> eventsToIgnore;
    private double currentY = 0;
    private Diagram diagram;
    public SweepLine() {
        eventHeap = new BinaryHeap<>(eventComparator);
    }
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
    }


    private void handleCircleEvent(CircleEvent e) {

    }


}
