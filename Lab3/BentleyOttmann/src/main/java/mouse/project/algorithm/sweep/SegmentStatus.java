package mouse.project.algorithm.sweep;

import java.util.Set;

public class SegmentStatus implements Status<TSegment> {
    @Override
    public Neighbors<TSegment> insert(TSegment segment) {
        return null;
    }

    @Override
    public Neighbors<TSegment> delete(TSegment segment) {
        return null;
    }

    @Override
    public void reorder(Set<TSegment> set) {

    }
}
