package mouse.project.algorithm.edge;

import lombok.Data;
import mouse.project.algorithm.sweep.struct.Site;
import mouse.project.math.FPosition;
import mouse.project.math.Numbers;

import java.util.Comparator;
@Data
public class SitePair {
    private final Site s1;
    private final Site s2;
    private SitePair(Site s1, Site s2) {
        this.s1 = s1;
        this.s2 = s2;
    }
    private static final Comparator<FPosition> positionComparator = (p1, p2) -> {
        if (Numbers.dEquals(p1.y(), p2.y())) {
            if (Numbers.dEquals(p1.x(), p2.x())) {
                return 0;
            }
            return p1.x() > p2.x() ? 1 : -1;
        }
        return p1.y() > p2.y() ? 1 : -1;
    };
    public static SitePair of(Site gen1, Site gen2) {
        FPosition p1 = gen1.getPosition();
        FPosition p2 = gen2.getPosition();
        boolean firstSmaller = positionComparator.compare(p1, p2) < 0;
        Site low = firstSmaller ? gen1 : gen2;
        Site high = firstSmaller ? gen2 : gen1;
        return new SitePair(low, high);
    }
}
