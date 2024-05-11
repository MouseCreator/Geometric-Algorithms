package mouse.project.algorithm.sweep.diagram;

import mouse.project.math.FPosition;
import mouse.project.math.Numbers;
import mouse.project.math.Vector2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DiagramImpl implements Diagram{
    private Map<VoronoiVertex, VertexInfo> vertexInfoMap;
    @Override
    public void initialize(List<VoronoiVertex> vertices, List<VerEdge> edges) {
        for (VoronoiVertex vertex : vertices) {
            vertexInfoMap.put(vertex , new VertexInfo(vertex));
        }
    }
    private class HalfEdge {
        private VoronoiVertex from;
        private VoronoiVertex to;
    }
    private static class VertexInfo {
        private final List<VoronoiVertex> sortedNeighbors;
        private final VoronoiVertex myVertex;
        public VertexInfo(VoronoiVertex vertex) {
            sortedNeighbors = new ArrayList<>();
            myVertex = vertex;
        }
        public void appendEdgeTo(VoronoiVertex vertex) {
            if (sortedNeighbors.isEmpty()) {
                sortedNeighbors.add(vertex);
                return;
            }
            int index = Collections.binarySearch(sortedNeighbors, vertex, (v1, v2) -> {
                FPosition p1 = myVertex.getPosition();
                FPosition p2 = v1.getPosition();
                double angle1 = Vector2.of(1, 0).angle(Vector2.from(p1, p2));

                p2 = v2.getPosition();
                double angle2 = Vector2.of(1, 0).angle(Vector2.from(p1, p2));

                return Numbers.dCompare(angle1, angle2);
            });
            if (index < 0) {
                index = - index - 1;
            }
            sortedNeighbors.add(index, vertex);
        }
    }
}
