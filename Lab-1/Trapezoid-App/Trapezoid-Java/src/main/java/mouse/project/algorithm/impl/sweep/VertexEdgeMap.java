package mouse.project.algorithm.impl.sweep;

import mouse.project.algorithm.impl.trapezoid.Edge;
import mouse.project.algorithm.impl.trapezoid.Vertex;

import java.util.List;

public interface VertexEdgeMap {
    List<Edge> getAllEdges(Vertex vertex);
}
