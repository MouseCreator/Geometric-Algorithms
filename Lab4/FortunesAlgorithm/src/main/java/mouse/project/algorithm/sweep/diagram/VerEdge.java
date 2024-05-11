package mouse.project.algorithm.sweep.diagram;

import lombok.Data;

@Data
public class VerEdge {
    private VoronoiVertex v1;
    private VoronoiVertex v2;

    public VerEdge(VoronoiVertex v1, VoronoiVertex v2) {
        this.v1 = v1;
        this.v2 = v2;
    }
}
