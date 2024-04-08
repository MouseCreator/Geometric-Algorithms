package mouse.project.algorithm.builder;

import mouse.project.algorithm.common.CPoint;
import mouse.project.algorithm.common.CSet;
import mouse.project.algorithm.tree.SegmentTree;
import mouse.project.algorithm.tree.SegmentTreeNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class Initializer {
    public SegmentTree createTreeFor(CSet pointSet) {
        Collection<CPoint> points = pointSet.getPoints();
        CPoint cPoint = () -> null;
        cPoint.position();
        List<Integer> xCoordinates = points.stream()
                .sorted(Comparator.comparingInt(p -> p.position().x()))
                .distinct().map(pt -> pt.position().x())
                .toList();
        List<CPoint> ySortedPoints = new ArrayList<>(points);
        ySortedPoints.sort(Comparator.comparingInt(p -> p.position().y()));

        SegmentTreeBuilder builder = new SegmentTreeBuilder();
        SegmentTree segmentTree = builder.createSegmentTree(xCoordinates);
        addPoints(segmentTree, ySortedPoints);
        return segmentTree;
    }

    private void addPoints(SegmentTree segmentTree, List<CPoint> ySortedPoints) {
        ySortedPoints.forEach(p -> insertPoint(segmentTree, p));
    }

    private void insertPoint(SegmentTree segmentTree, CPoint p) {
        SegmentTreeNode root = segmentTree.getRoot();
        int v = segmentTree.normalize(p.position().x());
        insertInNode(v, root, p);
    }

    private void insertInNode(int v, SegmentTreeNode node, CPoint p) {
        if (node==null) {
            return;
        }
        node.addPoint(p);
        if (node.median() < v && node.hasLeft()) {
            insertInNode(v, node.getLeft(), p);
        } else if (node.hasRight()) {
            insertInNode(v, node.getRight(), p);
        }
    }
}
