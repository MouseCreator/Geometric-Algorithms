package mouse.project.ui.components.point;

import java.util.ArrayList;
import java.util.List;

public class PointSet {

    private final List<Point> points;

    public PointSet() {
        points = new ArrayList<>();
    }

    public List<Point> getPoints() {
        return new ArrayList<>(points);
    }

    public void clear() {
        points.clear();
    }

    public void add(Point point) {
        this.points.add(point);
    }
}
