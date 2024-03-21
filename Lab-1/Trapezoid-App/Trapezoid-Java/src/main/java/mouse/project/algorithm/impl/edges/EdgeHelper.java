package mouse.project.algorithm.impl.edges;

import mouse.project.algorithm.impl.SEdge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EdgeHelper {
    public List<SEdge> getEdgesBetween(Collection<SEdge> edges, int bottom, int top) {
        List<SEdge> result = new ArrayList<>();
        for (SEdge edge : edges) {
            if (isInRange(edge, bottom, top)) {
                result.add(edge);
            }
        }
        return result;
    }

    private boolean isInRange(SEdge edge, int bottom, int top) {
        int y1 = edge.from().getPosition().y();
        int y2 = edge.to().getPosition().y();
        int y_min = Math.min(y1, y2);
        int y_max = Math.max(y1, y2);
        return !(y_max < top) && !(y_min > bottom);
    }
}
