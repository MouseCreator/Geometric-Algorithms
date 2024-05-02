package mouse.project.algorithm.mapper;

import mouse.project.algorithm.sweep.TSegment;
import mouse.project.algorithm.sweep.TSegmentSet;
import mouse.project.algorithm.sweep.TSegmentSetImpl;
import mouse.project.math.Position;
import mouse.project.ui.components.point.Segment;
import mouse.project.ui.components.point.Segments;

import java.util.Comparator;
import java.util.List;

public class Mapper {

    private final Comparator<Position> positionComparator = (p1, p2) -> {
        if (p1 == p2) {
            return 0;
        }
        if (p1.y() != p2.y()) {
            return p1.y() - p2.y();
        }
        return p1.x() - p2.x();
    };

    public TSegmentSet mapSegments(Segments segments) {

        List<Segment> all = segments.getAll();
        List<TSegment> list = all.stream().map(s -> {

            Position from = s.getFrom().getPosition();
            Position to = s.getTo().getPosition();
            if (positionComparator.compare(from, to) < 0) {
                return new TSegment(s.getId(), from, to);
            }
            return new TSegment(s.getId(), to, from);
        }).toList();
        return new TSegmentSetImpl(list);
    }
}
