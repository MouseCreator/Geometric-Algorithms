package mouse.project.algorithm.sweep.diagram;


import java.util.Collection;
import java.util.List;

public interface Diagram {
    void initialize(List<VoronoiVertex> vertices, List<VerEdge> edges);
    Collection<Face> getFaces();
}
