package mouse.project.algorithm.sweep;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TSegmentSetImpl implements TSegmentSet {
    private final List<TSegment> segmentList;
    public TSegmentSetImpl(List<TSegment> segmentList) {
        this.segmentList = segmentList;
    }

    @Override
    public Collection<TSegment> getAll() {
        return new ArrayList<>(segmentList);
    }
}
