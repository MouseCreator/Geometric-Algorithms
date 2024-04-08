package mouse.project.algorithm.builder;

import mouse.project.algorithm.tree.SegmentTree;
import mouse.project.algorithm.tree.SegmentTreeNode;

import java.util.List;

public class SegmentTreeBuilder {
    public SegmentTree createSegmentTree(List<Integer> sortedX) {
        SegmentTreeNode root = createTreeRecursive(0, sortedX.size());
        return new SegmentTree(sortedX, root);
    }

    private SegmentTreeNode createTreeRecursive(int lowerMost, int upperMost) {
        if (lowerMost >= upperMost) {
            return null;
        }
        SegmentTreeNode segmentTree = new SegmentTreeNode(lowerMost, upperMost);
        int middle = (lowerMost + upperMost) >>> 1;

        segmentTree.setLeft(createTreeRecursive(lowerMost, middle));
        segmentTree.setRight(createTreeRecursive(middle, upperMost));
        return segmentTree;
    }
}
