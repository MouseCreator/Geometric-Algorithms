package mouse.project.algorithm.tree;

import lombok.Data;
import mouse.project.algorithm.common.CPoint;

import java.util.Collection;
import java.util.Objects;

@Data
public class SegmentTreeNode {

    private int lower;
    private int upper;
    private YTree yTree;

    private SegmentTreeNode left;
    private SegmentTreeNode right;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SegmentTreeNode that = (SegmentTreeNode) object;
        return lower == that.lower && upper == that.upper;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lower, upper);
    }

    public SegmentTreeNode(int lowerMost, int upperMost) {
        this.lower = lowerMost;
        this.upper = upperMost;
        left = null;
        right = null;
        yTree = new YTreeImpl();
    }

    public void initYTree(Collection<CPoint> points) {
        yTree.buildBalancedFrom(points);
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
