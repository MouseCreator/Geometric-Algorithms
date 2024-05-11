package mouse.project.algorithm.sweep.diagram;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class DiagramData {
    private final List<VoronoiVertex> vertices;
    private final List<ConnectedEdge> edges;
    public DiagramData() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public DiagramData(List<VoronoiVertex> vertices, List<ConnectedEdge> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }
}
