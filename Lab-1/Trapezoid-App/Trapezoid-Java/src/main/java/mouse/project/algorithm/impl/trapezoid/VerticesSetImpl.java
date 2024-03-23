package mouse.project.algorithm.impl.trapezoid;

import mouse.project.utils.math.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class VerticesSetImpl implements VerticesSet {

    private final List<Vertex> vertexList;

    public VerticesSetImpl() {
        this.vertexList = new ArrayList<>();
    }

    @Override
    public boolean isEmpty() {
        return vertexList.isEmpty();
    }

    @Override
    public Position medianPosition() {
        return vertexList.get(vertexList.size()>>>1).position();
    }

    @Override
    public void add(Vertex vertex) {
        if (vertexList.isEmpty()) {
            vertexList.add(vertex);
            return;
        }
        Vertex last = vertexList.get(vertexList.size() - 1);
        int y1 = vertex.position().y();
        int y2 = last.position().y();
        if (y1 < y2) {
            throw new IllegalArgumentException("Vertices are not sorted! Got: " + y1 + " > " + y2);
        }
        vertexList.add(vertex);
    }

    @Override
    public Collection<Vertex> getAllSortedByY() {
        return vertexList;
    }
}
