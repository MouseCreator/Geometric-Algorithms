package mouse.project.algorithm.sweep.diagram;

import mouse.project.algorithm.sweep.struct.Site;
import mouse.project.math.FPosition;

public interface DiagramBuilder {
    Diagram getResult();
    VoronoiVertex generateVoronoiVertex(FPosition fPosition);
    ConnectedEdge withConnectedEdge(Site end1, Site end2);
    void setStartVertex(ConnectedEdge connectedEdge, VoronoiVertex vertex);
    void setEndVertex(ConnectedEdge connectedEdge, VoronoiVertex vertex);
}
