package mouse.project.algorithm.sweep.diagram;

import lombok.Data;
import mouse.project.math.FPosition;
@Data
public class VoronoiVertex {
    private FPosition position;
    public VoronoiVertex(FPosition position) {
        this.position = position;
    }
}
