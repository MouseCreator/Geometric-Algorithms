package mouse.project.algorithm.sweep;

import lombok.Getter;
import mouse.project.algorithm.data.Point;
import mouse.project.algorithm.data.PointSet;
import mouse.project.algorithm.heap.BinaryHeap;
import mouse.project.algorithm.heap.Heap;
import mouse.project.algorithm.sweep.circle.Circle;
import mouse.project.algorithm.sweep.diagram.*;
import mouse.project.algorithm.sweep.neighbors.Neighbors;
import mouse.project.algorithm.sweep.struct.Site;
import mouse.project.algorithm.sweep.struct.SiteNode;
import mouse.project.algorithm.sweep.struct.SiteStatus;
import mouse.project.math.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
public class SweepLine {
    private final SiteStatus status;
    private final Heap<Event> eventHeap;
    private final Set<Site> sitesToIgnore;
    private double currentY = 0;
    private final DiagramBuilder diagramBuilder;
    private final Logger logger = LogManager.getLogger(SweepLine.class);
    public SweepLine() {
        eventHeap = new BinaryHeap<>(eventComparator);
        status = new SiteStatus();
        sitesToIgnore= new HashSet<>();
        diagramBuilder = new VDiagramBuilder();
    }

    public Diagram diagram() {
        return diagramBuilder.getResult();
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
    @Getter
    public static class SiteEvent implements Event {

        private final Site origin;

        public SiteEvent(Site origin) {
            this.origin = origin;
        }

        @Override
        public FPosition position() {
            return origin.getPosition();
        }

        @Override
        public String toString() {
            return "SiteEvent{" +
                    origin +
                    '}';
        }
    }
    public record CircleEvent(Circle circle, Site pI, Site pJ, Site pK) implements Event {
        @Override
        public FPosition position() {
            return circle.bottom();
        }

        @Override
        public String toString() {
            return "CircleEvent{" +
                    "circle=" + circle +
                    ", pI=" + pI +
                    ", pJ=" + pJ +
                    ", pK=" + pK +
                    '}';
        }
    }

    public void scan(PointSet pointSet) {
        List<Point> all = new ArrayList<>(pointSet.getPoints());
        generateEventsFrom(all);
        scanEvents();
    }

    private void generateEventsFrom(List<Point> all) {
        List<Site> list = all.stream().map(t -> status.generateSite(t.getId(), t.getFPosition())).toList();
        list.forEach(e -> eventHeap.insert(new SiteEvent(e)));
    }

    private void scanEvents() {
        while (!eventHeap.isEmpty()) {
            Event nextEvent = eventHeap.extractMin();
            if (ignoreEvent(nextEvent))
                continue;
            logger.debug("Handling event: " + nextEvent);
            currentY = nextEvent.position().y();
            List<Event> eventList = new ArrayList<>();
            eventList.add(nextEvent);
            while (!eventHeap.isEmpty() && eventComparator.compare(eventHeap.minimum(), nextEvent) == 0) {
                eventList.add(eventHeap.extractMin());
            }
            for (Event e : eventList) {
                handleEvent(e);
            }
            logger.debug(status.print());
        }
    }

    private boolean ignoreEvent(Event event) {
        if (event instanceof CircleEvent circleEvent) {
            List<Site> sites = List.of(circleEvent.pI(), circleEvent.pJ(), circleEvent.pK());
            for (Site s : sites) {
                if (sitesToIgnore.contains(s)) {
                    return true;
                }
            }
        }
        return false;
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
        SiteNode siteNode = status.insertAndSplit(origin, currentY);
        Optional<SiteNode> next = siteNode.next();
        assert next.isPresent();
        Site pI = siteNode.getSite();
        Site pJ = next.get().getSite();

        diagramBuilder.edgeOnBisector(pI, pJ);

        //Generate new circle events
        Optional<SiteNode> pK1 = next.get().next();
        pK1.ifPresent(node -> generateCircleEvent(pI, pJ, node.getSite()));

        Optional<SiteNode> prev = siteNode.prev();
        assert prev.isPresent();
        Optional<SiteNode> pK2 = prev.get().prev();
        pK2.ifPresent(node -> generateCircleEvent(pI, pJ, node.getSite()));
    }

    private void generateCircleEvent(Site pI, Site pJ, Site pK) {
        FSegment s1 = new FSegment(pI.getPosition(), pJ.getPosition());
        FSegment s2 = new FSegment(pJ.getPosition(), pK.getPosition());
        GenLine bisector1 = s1.bisector();
        GenLine bisector2 = s2.bisector();

        if(bisector1.overlaps(bisector2)) {
            throw new IllegalArgumentException("Sites are on one line: " + List.of(pI, pJ, pK));
        }

        Optional<FPosition> centerOpt = bisector1.intersectionPoint(bisector2);
        if (centerOpt.isEmpty()) {
            throw new IllegalArgumentException("Bisectors do not intersect");
        }

        FPosition center = centerOpt.get();
        double radius = pI.getPosition().distanceTo(center);

        Circle circle = new Circle(center, radius);
        eventHeap.insert(new CircleEvent(circle, pI, pJ, pK));
    }


    private void handleCircleEvent(CircleEvent e) {
        FPosition center = e.circle().center();
        Neighbors<SiteNode> neighborNodes = status.remove(center.x(), currentY);
        VoronoiVertex vertex = diagramBuilder.generateVoronoiVertex(center);
        VoronoiEdge edge1 = diagramBuilder.edgeOnBisector(e.pI(), e.pJ());
        VoronoiEdge edge2 = diagramBuilder.edgeOnBisector(e.pJ(), e.pK());
        diagramBuilder.bindEdgeOnBisectorToVertex(edge1, vertex);
        diagramBuilder.bindEdgeOnBisectorToVertex(edge2, vertex);
        sitesToIgnore.add(e.pJ());

        VoronoiEdge edge3 = diagramBuilder.edgeOnBisector(e.pI(), e.pK());
        diagramBuilder.bindEdgeOnBisectorToVertex(edge3, vertex);

        if (neighborNodes.hasLeft()) {
            SiteNode pI = neighborNodes.left();

            Optional<SiteNode> pANode = pI.prev();
            pANode.ifPresent(pA -> generateCircleEvent(pA.getSite(), e.pI(), e.pK()));
        }

        if (neighborNodes.hasRight()) {
            SiteNode pK = neighborNodes.right();

            Optional<SiteNode> pZNode = pK.next();
            pZNode.ifPresent(pZ -> generateCircleEvent(e.pI(), e.pK(), pZ.getSite()));
        }

    }


}
