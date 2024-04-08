package mouse.project.algorithm.builder;

import mouse.project.algorithm.tree.SegmentTree;

public class SegmentTreeBuilder {
    public SegmentTree createSegmentTree(int lowerMost, int upperMost) {
        return createTreeRecursive(lowerMost, upperMost);
    }

    private SegmentTree createTreeRecursive(int lowerMost, int upperMost) {
        if (lowerMost >= upperMost) {
            return null;
        }
        SegmentTree segmentTree = new SegmentTree(lowerMost, upperMost);
        int middle = (lowerMost + upperMost) >>> 1;

        segmentTree.setLeft(createSegmentTree(lowerMost, middle));
        segmentTree.setRight(createSegmentTree(middle, upperMost));
        return segmentTree;
    }
}
