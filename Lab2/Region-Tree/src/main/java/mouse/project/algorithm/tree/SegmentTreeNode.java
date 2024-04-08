package mouse.project.algorithm.tree;

import lombok.Data;
import mouse.project.algorithm.common.CPoint;

@Data
public class SegmentTreeNode {

    private int lower;
    private int upper;
    private YTree yTree;

    private SegmentTreeNode left;
    private SegmentTreeNode right;


    public SegmentTreeNode(int lowerMost, int upperMost) {
        this.lower = lowerMost;
        this.upper = upperMost;
        left = null;
        right = null;
        yTree = new YTreeImpl();
    }

    public void addPoint(CPoint point) {
        yTree.add(point);
    }


    public boolean hasLeft() {
        return left != null;
    }

    public boolean hasRight() {
        return right != null;
    }

    public int median() {
        return (lower + upper) >>> 1;
    }
}
