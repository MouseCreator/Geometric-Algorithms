package mouse.project.algorithm.impl.trapezoid;

import mouse.project.utils.math.Position;

public interface VerticesSet {
    boolean isEmpty();
    Position medianPosition();
    void add(Vertex vertex);
}
