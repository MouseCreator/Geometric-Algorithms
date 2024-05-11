package mouse.project.algorithm.sweep.diagram;

import mouse.project.algorithm.sweep.struct.Site;
import mouse.project.math.FPosition;

public interface DiagramBuilder {
    Diagram getResult();
    VoronoiVertex generateVoronoiVertex(FPosition fPosition);
    VoronoiEdge edgeOnBisector(Site s1, Site s2);
    void bindEdgeOnBisectorToVertex(VoronoiEdge edge, VoronoiVertex startingVertex);
}
