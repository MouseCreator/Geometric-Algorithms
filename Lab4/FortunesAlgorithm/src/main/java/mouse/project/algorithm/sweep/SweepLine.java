package mouse.project.algorithm.sweep;

import lombok.Getter;
import mouse.project.algorithm.data.Point;
import mouse.project.algorithm.data.PointSet;
import mouse.project.algorithm.heap.BinaryHeap;
import mouse.project.algorithm.heap.Heap;
import mouse.project.algorithm.sweep.circle.Circle;
import mouse.project.algorithm.sweep.diagram.DiagramBuilder;
import mouse.project.algorithm.sweep.diagram.VoronoiEdge;
import mouse.project.algorithm.sweep.struct.Site;
import mouse.project.algorithm.sweep.struct.SiteNode;
import mouse.project.algorithm.sweep.struct.SiteStatus;
import mouse.project.math.*;

import java.util.*;
public class SweepLine {
    private SiteStatus status;
    private final Heap<Event> eventHeap;
    private Set<Site> sitesToIgnore;
    private double currentY = 0;
    private DiagramBuilder diagramBuilder;
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
        @Getter
        private final Site origin;

        public SiteEvent(Site origin) {
            this.origin = origin;
        }

        @Override
        public FPosition position() {
            return origin.getPosition();
        }
    }
    public record CircleEvent(Circle circle, Site pI, Site pJ, Site pK) implements Event {
        @Override
        public FPosition position() {
            return circle.bottom();
        }
    }

    public void scan(PointSet pointSet) {
        List<Point> all = new ArrayList<>(pointSet.getPoints());
        generateEventsFrom(all);
        scanEvents();
    }

    private void generateEventsFrom(List<Point> all) {
        List<Site> list = all.stream().map(t -> new Site(t.getFPosition())).toList();
        list.forEach(e -> eventHeap.insert(new SiteEvent(e)));
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
        Site origin = e.getOrigin();
        if (status.isEmpty()) {
            status.insertFirst(origin);
            return;
        }
        SiteNode siteNode = status.insertAndSplit(origin);
        Optional<SiteNode> next = siteNode.next();
        assert next.isPresent();
        Site pI = siteNode.getSite();
        Site pJ = next.get().getSite();

        diagramBuilder.appendDanglingEdgeBetween(new VoronoiEdge(pI, pJ));

    }


    private void handleCircleEvent(CircleEvent e) {
        Site pk = e.pK();
        status.remove(pk);
        FPosition center = e.circle().center();
        diagramBuilder.createAndJoin(center, new VoronoiEdge(e.pI(), e.pI()), new VoronoiEdge(e.pJ(), e.pK()));
        diagramBuilder.appendDanglingEdgeBetween(new VoronoiEdge(e.pI(), e.pK()));
        sitesToIgnore.add(e.pJ());

        // generate event for pA - pI - pK
        // generate event for pI - pK - pZ
    }


}
