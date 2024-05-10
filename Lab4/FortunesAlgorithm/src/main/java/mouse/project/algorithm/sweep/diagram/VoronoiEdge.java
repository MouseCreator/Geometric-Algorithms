package mouse.project.algorithm.sweep.diagram;

import lombok.Data;
import mouse.project.algorithm.sweep.struct.Site;
import mouse.project.math.FPosition;
import mouse.project.math.GenLine;

@Data
public class VoronoiEdge {
    private GenLine line;
    public VoronoiEdge(GenLine line) {
        this.line = line;
    }
}
