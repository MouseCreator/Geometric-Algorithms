package mouse.project.algorithm.impl.sweep;

import mouse.project.algorithm.impl.trapezoid.EdgesSet;
import mouse.project.algorithm.impl.trapezoid.VerticesSet;

public interface SweepLine {
    EdgesSet scanAndCreate(VerticesSet vertices, VertexEdgeMap map);
}
