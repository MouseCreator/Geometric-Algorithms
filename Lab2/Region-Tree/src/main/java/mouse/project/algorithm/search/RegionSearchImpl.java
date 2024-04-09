package mouse.project.algorithm.search;

import mouse.project.algorithm.common.CArea;
import mouse.project.algorithm.common.CPoint;
import mouse.project.algorithm.tree.SegmentTree;
import mouse.project.algorithm.tree.SegmentTreeNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RegionSearchImpl implements RegionSearch {
    @Override
    public List<CPoint> find(SegmentTree segmentTree, CArea area) {
        Interval yRange = new Interval(area.bottomLeft().y(), area.topRight().y());
        Set<CPoint> fromRoot = exploreTree(segmentTree, new Interval(area.bottomLeft().x(), area.topRight().x()), yRange);
        return new ArrayList<>(fromRoot);
    }

    private record Interval(int low, int high) {}

    private Set<CPoint> exploreTree(SegmentTree segmentTree, Interval xInterval, Interval yRange) {
        int nLow = segmentTree.normalizeSearch(xInterval.low());
        int nHigh = segmentTree.normalizeSearch(xInterval.high());
        if (nLow == nHigh) {
            return new HashSet<>();
        }
        Set<CPoint> result = new HashSet<>();
        exploreNode(result, segmentTree.getRoot(), new Interval(nLow, nHigh), yRange);
        return result;
    }

    private void exploreNode(Set<CPoint> result, SegmentTreeNode current, Interval xRange, Interval yRange) {
        if (current == null) {
            return;
        }
        if (current.getLower() == xRange.low() && current.getUpper() == xRange.high()) {
            result.addAll(current.getYTree().getRange(yRange.low(), yRange.high()));
            return;
        }
        if (current.getLower() <= xRange.low()) {
            if (current.getUpper() < xRange.high()) {
                return;
            }
            exploreNode(result, current.getLeft(), xRange, yRange);
        }
        if (current.getUpper() > xRange.high()) {
            exploreNode(result, current.getRight(), xRange, yRange);
        }

    }



}
