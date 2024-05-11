package mouse.project.algorithm.sweep.diagram;

import lombok.Data;
import mouse.project.math.FPosition;
@Data
public class VoronoiVertex {
    private FPosition position;
    private boolean imaginary;
    public VoronoiVertex(FPosition position, boolean imaginary) {
        this.position = position;
    }
}
