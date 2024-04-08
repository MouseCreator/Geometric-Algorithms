package mouse.project.algorithm.search;

import mouse.project.algorithm.common.CArea;
import mouse.project.algorithm.common.CPoint;
import mouse.project.algorithm.tree.SegmentTree;

import java.util.List;

public interface RegionSearch {
    List<CPoint> find(SegmentTree segmentTree, CArea area);
}
