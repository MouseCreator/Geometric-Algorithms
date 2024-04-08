package mouse.project.algorithm.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CSetImpl implements CSet {
    private final List<CPoint> pointList;

    public CSetImpl(Collection<CPoint> points) {
        this.pointList = new ArrayList<>(points);
    }

    @Override
    public Collection<CPoint> getPoints() {
        return new ArrayList<>(pointList);
    }
}
