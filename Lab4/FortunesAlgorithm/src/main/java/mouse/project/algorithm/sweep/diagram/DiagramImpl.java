package mouse.project.algorithm.sweep.diagram;

import mouse.project.math.FPosition;
import mouse.project.math.Numbers;
import mouse.project.math.Vector2;

import java.util.*;

public class DiagramImpl implements Diagram{

    private List<Face> faces = null;
    @Override
    public void initialize(List<VoronoiVertex> vertices, List<VerEdge> edges) {
        Map<VoronoiVertex, TempVertexInfo> vertexInfoMap = new HashMap<>();
        if (faces != null) {
            return;
        }
        for (VoronoiVertex vertex : vertices) {
            vertexInfoMap.put(vertex , new TempVertexInfo(vertex));
        }
        Set<HalfEdge> halfEdges = new HashSet<>();
        int index = 0;
        for (VerEdge verEdge : edges) {
            VoronoiVertex v1 = verEdge.getV1();
            VoronoiVertex v2 = verEdge.getV2();

            TempVertexInfo info1 = vertexInfoMap.get(v1);
            TempVertexInfo info2 = vertexInfoMap.get(v2);

            HalfEdge halfEdge1 = new HalfEdge(index++, v2);
            HalfEdge halfEdge2 = new HalfEdge(index++, v1);

            info1.appendEdgeTo(halfEdge1);
            info2.appendEdgeTo(halfEdge2);

            halfEdge1.twin = halfEdge2;
            halfEdge2.twin = halfEdge1;

            halfEdges.add(halfEdge1);
            halfEdges.add(halfEdge2);
        }
        for (VoronoiVertex vertex : vertexInfoMap.keySet()) {
            TempVertexInfo tempVertexInfo = vertexInfoMap.get(vertex);
            tempVertexInfo.orderHalfEdges();
        }

        List<HalfEdge> halfEdgesNotInFace = new ArrayList<>(halfEdges);

        faces = new ArrayList<>();
        while (!halfEdgesNotInFace.isEmpty()) {
            HalfEdge first = halfEdgesNotInFace.getFirst();
            Face face = createFaceStartingAt(halfEdgesNotInFace, first);
            faces.add(face);
        }

    }

    private Face createFaceStartingAt(List<HalfEdge> halfEdgesNotInFace, HalfEdge start) {
        HalfEdge current = start;
        List<FPosition> positionList = new ArrayList<>();
        do {
            halfEdgesNotInFace.remove(current);
            positionList.add(current.to.getPosition());
            current = current.nextOrder;
        } while (current != start);
        return new Face(positionList);
    }

    @Override
    public Collection<Face> getFaces() {
        return faces;
    }
    private static class HalfEdge {
        private final VoronoiVertex to;
        private HalfEdge twin;
        private HalfEdge nextInVertex;
        private HalfEdge prevInVertex;
        private HalfEdge nextOrder;
        private HalfEdge prevOrder;
        private final int index;
        public HalfEdge(int index, VoronoiVertex to) {
            this.to = to;
            this.index = index;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HalfEdge halfEdge = (HalfEdge) o;
            return index == halfEdge.index;
        }

        @Override
        public int hashCode() {
            return Objects.hash(index);
        }
    }
    private static class TempVertexInfo {
        private final List<HalfEdge> sortedHalfEdges;
        private final VoronoiVertex myVertex;
        public TempVertexInfo(VoronoiVertex vertex) {
            sortedHalfEdges = new ArrayList<>();
            myVertex = vertex;
        }
        public void appendEdgeTo(HalfEdge halfEdge) {
            if (sortedHalfEdges.isEmpty()) {
                sortedHalfEdges.add(halfEdge);
                return;
            }
            int index = Collections.binarySearch(sortedHalfEdges, halfEdge, (e1, e2) -> {
                FPosition p1 = myVertex.getPosition();
                FPosition p2 = e1.to.getPosition();
                double angle1 = Vector2.of(1, 0).angle(Vector2.from(p1, p2));

                p2 = e2.to.getPosition();
                double angle2 = Vector2.of(1, 0).angle(Vector2.from(p1, p2));

                return Numbers.dCompare(angle1, angle2);
            });
            if (index < 0) {
                index = - index - 1;
            }
            sortedHalfEdges.add(index, halfEdge);
        }

        public void orderHalfEdges() {
            if (sortedHalfEdges.isEmpty()) {
                return;
            }
            int size = sortedHalfEdges.size();
            for (int i = 0; i < size - 1; i++) {
                HalfEdge e = sortedHalfEdges.get(i);
                HalfEdge nextToE = sortedHalfEdges.get(i + 1);

                orderPair(e, nextToE);
            }
            HalfEdge first = sortedHalfEdges.getFirst();
            HalfEdge last = sortedHalfEdges.getLast();

            orderPair(first, last);
        }

        private void orderPair(HalfEdge e, HalfEdge nextToE) {
            e.nextInVertex = nextToE;
            nextToE.prevInVertex = e;

            e.twin.nextOrder = nextToE;
            nextToE.prevOrder = e.twin;
        }
    }
}
