package mouse.project.algorithm.mapper;

import mouse.project.algorithm.common.*;
import mouse.project.ui.components.point.PointSet;
import mouse.project.ui.components.point.TPoint;
import mouse.project.ui.components.point.WrapBox;
import mouse.project.utils.math.Position;

import java.util.List;

public class Mapper {
    public static CPoint toCPoints(TPoint tPoint) {
        return new CPointImpl(tPoint.getId(), tPoint.position());
    }
    public static CSet toCSet(PointSet set) {
        List<TPoint> points = set.getPoints();
        List<CPoint> list = points.stream().map(Mapper::toCPoints).toList();
        return new CSetImpl(list);
    }

    public static CArea toArea(WrapBox target) {
        Position p1 = target.getFrom();
        Position p2 = target.getTo();
        int lowerX = Math.min(p1.x(), p2.x());
        int lowerY = Math.min(p1.y(), p2.y());
        int upperX = Math.max(p1.x(), p2.x());
        int upperY = Math.max(p1.y(), p2.y());

        return new CAreaImpl(Position.of(lowerX, lowerY), Position.of(upperX, upperY));
    }
}
