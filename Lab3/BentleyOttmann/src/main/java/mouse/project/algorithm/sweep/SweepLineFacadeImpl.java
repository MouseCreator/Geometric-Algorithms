package mouse.project.algorithm.sweep;

import java.util.Set;

public class SweepLineFacadeImpl implements SweepLineFacade {
    @Override
    public Set<TIntersection> findAllIntersections(TSegmentSet segmentSet) {
        SweepLine sweepLine = new SweepLine();
        sweepLine.scan(segmentSet);
        return sweepLine.getIntersectionSet();
    }
}
