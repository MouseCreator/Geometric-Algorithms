package mouse.project.algorithm.sweep;

import java.util.Set;

public interface SweepLineFacade {
    Set<TIntersection> findAllIntersections(TSegmentSet segmentSet);
}
