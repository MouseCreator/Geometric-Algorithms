package mouse.project.algorithm.sweep.diagram;

import lombok.Data;
import mouse.project.algorithm.sweep.struct.Site;
@Data
public class VoronoiEdge {
    private Site site1;
    private Site site2;
    public VoronoiEdge(Site site1, Site site2) {
        this.site1 = site1;
        this.site2 = site2;
    }
}
