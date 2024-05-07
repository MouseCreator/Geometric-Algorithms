package mouse.project.algorithm.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PointSetImpl implements PointSet {
    private final List<Point> pointList;
    public PointSetImpl(List<Point> pointList) {
        this.pointList = pointList;
    }

    @Override
    public Collection<Point> getPoints() {
        return new ArrayList<>(pointList);
    }
}
