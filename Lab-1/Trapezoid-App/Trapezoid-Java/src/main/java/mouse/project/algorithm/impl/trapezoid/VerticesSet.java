package mouse.project.algorithm.impl.trapezoid;

import mouse.project.utils.math.Position;

import java.util.Collection;

public interface VerticesSet {
    boolean isEmpty();
    Position medianPosition();
    void add(Vertex vertex);
    Collection<Vertex> getAllSortedByY();
}
