package mouse.project.algorithm.mapper;

import mouse.project.algorithm.data.Point;
import mouse.project.algorithm.data.PointSet;
import mouse.project.algorithm.data.PointSetImpl;

import java.util.List;

public class Mapper {
    public PointSet mapPointSet(mouse.project.ui.components.point.PointSet pointSet) {
        List<Point> list = pointSet.getPoints()
                .stream()
                .map(p -> new Point(p.position().floats()))
                .toList();
        return new PointSetImpl(list);
    }
}
