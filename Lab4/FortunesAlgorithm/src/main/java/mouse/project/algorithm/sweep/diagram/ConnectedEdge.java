package mouse.project.algorithm.sweep.diagram;

import lombok.Data;
import mouse.project.algorithm.sweep.struct.Site;
import mouse.project.math.FPosition;
import mouse.project.math.FSegment;
import mouse.project.math.GenLine;

@Data
public class ConnectedEdge {
    private VoronoiVertex start;
    private VoronoiVertex end;
    private Site s1;
    private Site s2;
    private GenLine bisector;
    private FPosition origin;

    public static ConnectedEdge fromSites(Site s1, Site s2) {
        ConnectedEdge edge = new ConnectedEdge();
        edge.s1 = s1;
        edge.s2 = s2;
        FSegment segment = new FSegment(s1.getPosition(), s2.getPosition());
        edge.origin = segment.middle();
        edge.bisector = segment.bisector();
        return edge;
    }
}
