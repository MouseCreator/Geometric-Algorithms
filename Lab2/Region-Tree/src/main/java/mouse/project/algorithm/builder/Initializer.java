package mouse.project.algorithm.builder;

import mouse.project.algorithm.common.CPoint;
import mouse.project.algorithm.common.CSet;
import mouse.project.algorithm.tree.SegmentTree;
import mouse.project.algorithm.tree.SegmentTreeNode;

import java.util.*;

public class Initializer {
    public SegmentTree createTreeFor(CSet pointSet) {
        Collection<CPoint> points = pointSet.getPoints();
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
        Map<SegmentTreeNode, List<CPoint>> map = new HashMap<>();
        ySortedPoints.forEach(p -> insertPoint(segmentTree, p, map));
        map.keySet().forEach(k -> k.initYTree(map.get(k)));
    }

    private void insertPoint(SegmentTree segmentTree, CPoint p, Map<SegmentTreeNode, List<CPoint>> map) {
        SegmentTreeNode root = segmentTree.getRoot();
        int v = segmentTree.normalize(p.position().x());
        insertInNode(v, root, p, map);
    }

    private void insertInNode(int v, SegmentTreeNode node, CPoint p, Map<SegmentTreeNode, List<CPoint>> map) {
        if (node==null) {
            return;
        }
        if (node.getLower() <= v && node.getUpper() > v) {
            List<CPoint> list = map.computeIfAbsent(node, n -> new ArrayList<>());
            list.add(p);
        }
        if (node.getLower() <= v && node.hasLeft()) {
            insertInNode(v, node.getLeft(), p, map);
        }
        if (node.getUpper() > v && node.hasRight()) {
            insertInNode(v, node.getRight(), p, map);
        }
    }
}
