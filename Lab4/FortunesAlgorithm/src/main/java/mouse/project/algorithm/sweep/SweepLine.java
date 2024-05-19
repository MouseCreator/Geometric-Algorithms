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
    private final Set<HandledCircle> handledCircles;
    private double currentY = 0;
    private final DiagramBuilder diagramBuilder;
    private final Logger logger = LogManager.getLogger(SweepLine.class);

    private record HandledCircle(String s1, String s2, String s3) {
        public HandledCircle(String s1, String s2, String s3) {
            List<String> strings = new ArrayList<>(List.of(s1, s2, s3));
            strings.sort(String::compareTo);
            this.s1 = strings.get(0);
            this.s2 = strings.get(1);
            this.s3 = strings.get(2);
        }
    }
    public SweepLine() {
        eventHeap = new BinaryHeap<>(eventComparator);
        status = new SiteStatus();
        sitesToIgnore= new HashSet<>();
        handledCircles = new HashSet<>();
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

            currentY = nextEvent.position().y();
            List<Event> eventList = new ArrayList<>();
            eventList.add(nextEvent);
            while (!eventHeap.isEmpty() && eventComparator.compare(eventHeap.minimum(), nextEvent) == 0) {
                eventList.add(eventHeap.extractMin());
            }
            for (Event e : eventList) {
                if (ignoreEvent(nextEvent))
                    continue;
                logger.debug("Handling event: " + e);
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

        diagramBuilder.withConnectedEdge(pI, pJ);
        //Generate new circle events
        Optional<SiteNode> pK1 = next.get().next();
        pK1.ifPresent(pk -> generateCircleEvent(pI, pJ, pk.getSite()));

        Optional<SiteNode> prev = siteNode.prev();
        assert prev.isPresent();
        Site pJ2 = prev.get().getSite();
        Optional<SiteNode> pK2 = prev.get().prev();
        pK2.ifPresent(pk -> generateCircleEvent(pk.getSite(), pJ2, pI));
    }

    private void generateCircleEvent(Site pI, Site pJ, Site pK) {
        if (!areUniqueLetters(pI, pJ, pK))
            return;
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
        HandledCircle handledCircle = new HandledCircle(pI.getLetter(), pJ.getLetter(), pK.getLetter());
        if (handledCircles.contains(handledCircle)) {
            return;
        }
        if (isStepBack(circle)) {
            return;
        }
        eventHeap.insert(new CircleEvent(circle, pI, pJ, pK));
    }

    private boolean isStepBack(Circle circle) {
        return Numbers.dLess(circle.bottom().y(), currentY);
    }

    private static boolean areUniqueLetters(Site pI, Site pJ, Site pK) {
        HashSet<String> letterSet = new HashSet<>();
        letterSet.add(pI.getLetter());
        letterSet.add(pJ.getLetter());
        letterSet.add(pK.getLetter());
        return letterSet.size() == 3;
    }


    private void handleCircleEvent(CircleEvent e) {
        FPosition center = e.circle().center();
        if (!checkBreakpointsConverge(e)) {
            return;
        }

        double targetX = center.x();
        Neighbors<SiteNode> neighborNodes = status.remove(targetX, currentY);
        VoronoiVertex vertex = diagramBuilder.generateVoronoiVertex(center);
        sitesToIgnore.add(e.pJ());

        determineWhichEdgesStartAndWhichEnd(e, vertex);

        handledCircles.add(new HandledCircle(e.pI().getLetter(), e.pJ().getLetter(), e.pK().getLetter()));
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

    private boolean checkBreakpointsConverge(CircleEvent e) {
        Site pI = e.pI;
        Site pJ = e.pJ;
        Site pK = e.pK;
        double breakpoint1 = status.calculateBreakpoint(pI, pJ, currentY);
        double breakpoint2 = status.calculateBreakpoint(pJ, pK, currentY);
        boolean converge = Numbers.dEquals(breakpoint1, breakpoint2);
        if (converge) logger.debug("Converge: " + pI + ", " + pJ + ", " + pK);
        else logger.debug("Diverge: " + pI + ", " + pJ + ", " + pK);
        return converge;
    }

    private void determineWhichEdgesStartAndWhichEnd(CircleEvent e, VoronoiVertex vertex) {
        Circle circle = e.circle();
        findStartEnd(e.pI(), e.pJ(), e.pK(), circle, vertex);
        findStartEnd(e.pJ(), e.pI(), e.pK(), circle, vertex);
        findStartEnd(e.pK(), e.pI(), e.pJ(), circle, vertex);
    }
    private final Comparator<FPosition> positionComparator = (p1, p2) -> {
        if (Numbers.dEquals(p1.y(), p2.y())) {
            if (Numbers.dEquals(p1.x(), p2.x())) {
                return 0;
            }
            return p1.x() > p2.x() ? 1 : -1;
        }
        return p1.y() > p2.y() ? 1 : -1;
    };
    private void findStartEnd(Site out, Site end1, Site end2, Circle circle, VoronoiVertex vertex) {
        ConnectedEdge connectedEdge = diagramBuilder.withConnectedEdge(end1, end2);
        GenLine bisector = connectedEdge.getBisector();
        FPosition[] intersections = circle.intersectionsWith(bisector);
        assert intersections.length == 2;
        FPosition origin = connectedEdge.getOrigin();

        boolean firstLower = positionComparator.compare(intersections[0], intersections[1]) < 0;
        FPosition lowerIntersection = firstLower ? intersections[0] : intersections[1];
        FPosition upperIntersection = firstLower ? intersections[1] : intersections[0];

        Vector2 toLower = Vector2.from(origin, lowerIntersection);
        Vector2 toUpper = Vector2.from(origin, upperIntersection);

        Vector2 toOut = Vector2.from(origin, out.getPosition());

        double cosToLower = toLower.cos(toOut);
        double cosToUpper = toUpper.cos(toOut);
        if (Numbers.dLess(cosToLower, cosToUpper)) {
            diagramBuilder.setEndVertex(connectedEdge, vertex);
        } else {
            diagramBuilder.setStartVertex(connectedEdge, vertex);
        }
    }


}
