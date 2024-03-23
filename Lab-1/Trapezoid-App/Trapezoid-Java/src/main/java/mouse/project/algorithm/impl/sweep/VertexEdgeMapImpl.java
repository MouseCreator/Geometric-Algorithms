package mouse.project.algorithm.impl.sweep;

import mouse.project.algorithm.impl.trapezoid.Edge;
import mouse.project.algorithm.impl.trapezoid.EdgeImpl;
import mouse.project.algorithm.impl.trapezoid.Vertex;
import mouse.project.utils.math.Vector2;

import java.util.*;

public class VertexEdgeMapImpl implements VertexEdgeMap {

    private final Map<Vertex, List<Edge>> map;

    public VertexEdgeMapImpl() {
        map = new HashMap<>();
    }

    public void add(Edge edge) {
        Vertex v1 = edge.end1();
        Vertex v2 = edge.end2();
        putAndSort(v1, v2, new EdgeImpl(v1, v2));
        putAndSort(v2, v1, new EdgeImpl(v2, v1));
    }

    private void putAndSort(Vertex from, Vertex to, Edge edge) {
        List<Edge> edges = map.computeIfAbsent(from, f -> new ArrayList<>());
        Vector2 edgeDirection = getEdgeDirection(from, to);
        Vector2 horizontalDir = Vector2.of(1, 0);
        double dot = edgeDirection.dot(horizontalDir);
        int index = edges.size();
        for (int i = 0; i < edges.size(); i++) {
            double v = calculateDotProduct(edges.get(i), horizontalDir);
            if (v < dot) {
                continue;
            }
            index = i;
            break;
        }
        edges.add(index, edge);
    }

    private double calculateDotProduct(Edge edge, Vector2 horizontalDir) {
        Vector2 edgeDir = getEdgeDirection(edge.end1(), edge.end2());
        return edgeDir.dot(horizontalDir);
    }

    private Vector2 getEdgeDirection(Vertex from, Vertex to) {
        return Vector2.from(from.position(), to.position()).unit();
    }

    @Override
    public List<Edge> getLeftToRight(Vertex v) {
        return new ArrayList<>(map.get(v));
    }
}
