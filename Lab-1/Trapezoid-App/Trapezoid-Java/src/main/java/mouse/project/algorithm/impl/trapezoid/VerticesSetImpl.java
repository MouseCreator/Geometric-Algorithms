package mouse.project.algorithm.impl.trapezoid;

import mouse.project.utils.math.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
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
        vertexList.add(vertex);
    }

    @Override
    public Collection<Vertex> getAllSortedByY() {
        return vertexList;
    }

    @Override
    public int size() {
        return vertexList.size();
    }
}
