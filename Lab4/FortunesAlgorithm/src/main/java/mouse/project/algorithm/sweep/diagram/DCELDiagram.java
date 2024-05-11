package mouse.project.algorithm.sweep.diagram;

import mouse.project.math.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DCELDiagram {

    public Diagram buildBoundedDiagram(DiagramData diagramData, FBox box) {
        DiagramVertexEdgeCollections diagramVertexEdgeCollections = getVerEdges(diagramData, box);
        return buildDiagram(diagramVertexEdgeCollections);
    }

    private record DiagramVertexEdgeCollections(List<VoronoiVertex> vertexList, List<VerEdge> edges) {}
    private DiagramVertexEdgeCollections getVerEdges(DiagramData diagramData, FBox box) {
        double left = box.left();
        double right = box.right();
        double top = box.top();
        double bottom = box.bottom();
        List<VoronoiVertex> frame = new ArrayList<>();

        addVertex(frame, left, top);
        addVertex(frame, left, bottom);
        addVertex(frame, right, top);
        addVertex(frame, right, bottom);

        GenLine topLine = GenLine.of(Vector2.of(1, 0), FPosition.of(left, top));
        GenLine leftLine = GenLine.of(Vector2.of(0, 1), FPosition.of(left, top));

        GenLine bottomLine = GenLine.of(Vector2.of(1, 0), FPosition.of(right, bottom));
        GenLine rightLine = GenLine.of(Vector2.of(0, 1), FPosition.of(right, bottom));
        List<VerEdge> verEdges = new ArrayList<>();
        for (ConnectedEdge edge : diagramData.getEdges()) {
            if (isOutOfBounds(box, edge.getStart())) {
                Optional<FPosition> topIntersectionOpt = edge.getBisector().intersectionPoint(topLine);
                FPosition newVertexPos;
                if (topIntersectionOpt.isPresent()) {
                    newVertexPos = topIntersectionOpt.get();
                } else {
                    Optional<FPosition> leftIntersectionOpt = edge.getBisector().intersectionPoint(leftLine);
                    assert leftIntersectionOpt.isPresent();
                    newVertexPos = leftIntersectionOpt.get();
                }
                VoronoiVertex v = addVertex(frame, newVertexPos);
                edge.setStart(v);
            }
            if (isOutOfBounds(box, edge.getEnd())) {
                Optional<FPosition> bottomIntersectionOpt = edge.getBisector().intersectionPoint(bottomLine);
                FPosition newVertexPos;
                if (bottomIntersectionOpt.isPresent()) {
                    newVertexPos = bottomIntersectionOpt.get();
                } else {
                    Optional<FPosition> rightIntersection = edge.getBisector().intersectionPoint(rightLine);
                    assert rightIntersection.isPresent();
                    newVertexPos = rightIntersection.get();
                }
                VoronoiVertex v = addVertex(frame, newVertexPos);
                edge.setEnd(v);
            }
            verEdges.add(toVerEdge(edge));
        }

        List<VerEdge> frameEdges = connectFrame(frame, box);
        verEdges.addAll(frameEdges);

        List<VoronoiVertex> vertices = diagramData.getVertices();
        vertices.addAll(frame);

        return new DiagramVertexEdgeCollections(vertices, verEdges);
    }

    private boolean isOutOfBounds(FBox box, VoronoiVertex start) {
        if (start.isImaginary()) {
            return true;
        }
        FPosition position = start.getPosition();
        return !box.contains(position);
    }

    private VerEdge toVerEdge(ConnectedEdge edge) {
        return new VerEdge(edge.getStart(), edge.getEnd());
    }

    private List<VerEdge> connectFrame(List<VoronoiVertex> frame, FBox box) {

        double left = box.left();
        double right = box.right();
        double top = box.top();
        double bottom = box.bottom();
        FPosition center = FPosition.of((left + right) / 2, (top + bottom) / 2);
        List<VoronoiVertex> sortedFrame = new ArrayList<>(frame);
        Vector2 Ox = Vector2.of(1, 0);

        sortedFrame.sort((v1, v2) -> {
            FPosition p1 = v1.getPosition();
            FPosition p2 = v2.getPosition();
            Vector2 vector1 = Vector2.from(center, p1);
            Vector2 vector2 = Vector2.from(center, p2);
            double angle1 = Ox.angle(vector1);
            double angle2 = Ox.angle(vector2);
            if (Numbers.dEquals(angle1, angle2)) {
                return 0;
            }
            return angle1 < angle2 ? 1 : -1;
        });

        List<VerEdge> result = new ArrayList<>();
        for (int i = 0; i < sortedFrame.size() - 1; i++) {
            VoronoiVertex vert1 = sortedFrame.get(i);
            VoronoiVertex vert2 = sortedFrame.get(i + 1);
            result.add(new VerEdge(vert1, vert2));
        }
        if (sortedFrame.size() > 1)
            result.add(new VerEdge(sortedFrame.getLast(), sortedFrame.getFirst()));
        return result;
    }

    private void addVertex(List<VoronoiVertex> list, double x, double y) {
        list.add(new VoronoiVertex(FPosition.of(x, y), false));
    }
    private VoronoiVertex addVertex(List<VoronoiVertex> list, FPosition position) {
        VoronoiVertex vertex = new VoronoiVertex(position, false);
        list.add(vertex);
        return vertex;
    }

    private Diagram buildDiagram(DiagramVertexEdgeCollections col) {
        List<VoronoiVertex> vertices = col.vertexList();
        List<VerEdge> edges = col.edges();
        Diagram diagram = new DiagramImpl();
        diagram.initialize(vertices, edges);
        return diagram;
    }
}
