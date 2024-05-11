package mouse.project.algorithm.sweep.diagram;

import lombok.Data;
import mouse.project.algorithm.sweep.struct.Site;
import mouse.project.math.FPosition;
import mouse.project.math.GenLine;

@Data
public class VoronoiEdge {
    private Site s1;
    private Site s2;

    public VoronoiEdge(Site s1, Site s2) {
        this.s1 = s1;
        this.s2 = s2;
    }
}
