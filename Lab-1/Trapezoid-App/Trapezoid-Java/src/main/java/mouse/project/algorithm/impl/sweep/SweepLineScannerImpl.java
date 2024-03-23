package mouse.project.algorithm.impl.sweep;

import mouse.project.algorithm.impl.trapezoid.*;
import mouse.project.utils.math.Position;

import java.util.*;

public class SweepLineScannerImpl implements SweepLineScanner {
    @Override
    public EdgesSet scanAndCreate(VerticesSet vertices, VertexEdgeMap map) {
        Collection<Vertex> allVertices = vertices.getAllSortedByY();
        Status status = new Status();
        for (Vertex v : allVertices) {
            int y = v.position().y();
            List<Edge> leftToRight = map.getLeftToRight(v);
            leftToRight.forEach(e -> {
                if(status.remove(e, y)) {
                   return;
                }
                status.add(e, y);
            });
        }
        return new EdgesSetImpl();
    }

    private static class LeftRightPair {
        Edge left;
        Edge right;
    }

    private void addToEnds(Vertex v, Map<Vertex, Set<Edge>> expectedEnds, VertexEdgeMap map) {
        map.getLeftToRight(v).forEach(e -> {
            Set<Edge> edges = expectedEnds.computeIfAbsent(v, s -> new HashSet<>());
            edges.add(e);
        });
    }

    private static class Status {
        private final List<Edge> statusList;

        public Status() {
            statusList = new ArrayList<>();
        }

        public Edge findLeft(Vertex v) {
            Edge edge = new EdgeImpl(v, v);
            int indexOfTarget = findIndexOf(edge, v.position().y());
            return statusList.get(indexOfTarget);
        }
        public Edge findRight(Vertex v) {
            Edge edge = new EdgeImpl(v, v);
            int indexOfTarget = findIndexOf(edge, v.position().y());
            return statusList.get(indexOfTarget+1);
        }

        public void add(Edge edge, int currentY) {
            int target = getX(edge.end1().position(), edge.end2().position(), currentY);
            int index = findIndexOf(edge, currentY);
            statusList.add(index, edge);
        }

        public boolean remove(Edge edge, int y) {
            int index = findIndexGeneral(edge, y);
            if (index >= 0) {
                statusList.remove(index);
                return true;
            }
            return false;
        }

        private int findIndexOf(Edge node, int y) {
            int index = findIndexGeneral(node, y);
            if (index < 0) {
                index = -(index + 1);
            }
            return index;
        }

        private int findIndexGeneral(Edge node, int y) {
            return Collections.binarySearch(statusList, node, Comparator.comparingInt(e -> getX(e, y)));
        }

        private int getX(Edge e, int y) {
            return getX(e.end1().position(), e.end2().position(), y);
        }

        private int getX(Position p1, Position p2, double y) {
            if (p1.y() == p2.y()) {
                if (y == p1.y()) {
                    return p1.x();
                } else {
                    throw new IllegalArgumentException("The given y-coordinate is not on the segment.");
                }
            }
            double slope = (double) (p2.y() - p1.y()) / (p2.x() - p1.x());
            return (int) ((y - p1.y()) / slope + p1.x());
        }
    }

}
