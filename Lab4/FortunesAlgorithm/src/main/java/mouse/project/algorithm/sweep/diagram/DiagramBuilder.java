package mouse.project.algorithm.sweep.diagram;

import mouse.project.math.FPosition;

public interface DiagramBuilder {
    void appendDanglingEdgeBetween(VoronoiEdge voronoiEdge);
    Diagram getResult();
    void createAndJoin(FPosition center, VoronoiEdge voronoiEdge, VoronoiEdge voronoiEdge1);
}
