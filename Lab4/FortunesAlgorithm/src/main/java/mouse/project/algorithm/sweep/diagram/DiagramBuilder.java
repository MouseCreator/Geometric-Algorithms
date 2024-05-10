package mouse.project.algorithm.sweep.diagram;

import mouse.project.algorithm.sweep.struct.Site;
import mouse.project.math.FPosition;

public interface DiagramBuilder {
    Diagram getResult();
    VoronoiVertex createVertex(FPosition position);
    void joinEdge(VoronoiVertex vertex, VoronoiEdge edge);
    VoronoiEdge appendEdgeOnBisector(Site pI, Site pJ);
}
