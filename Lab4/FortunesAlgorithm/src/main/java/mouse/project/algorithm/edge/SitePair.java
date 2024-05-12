package mouse.project.algorithm.edge;

import lombok.Data;
import mouse.project.algorithm.sweep.struct.Site;
import mouse.project.math.FPosition;
import mouse.project.math.Numbers;

import java.util.Comparator;
@Data
public class SitePair {
    private final String s1;
    private final String s2;
    private SitePair(String s1, String s2) {
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
        String key1 = gen1.getLetter();
        String key2 = gen2.getLetter();
        boolean firstSmaller = key1.compareTo(key2) < 0;
        String low = firstSmaller ? key1 : key2;
        String high = firstSmaller ? key2 : key1;
        return new SitePair(low, high);
    }
}
