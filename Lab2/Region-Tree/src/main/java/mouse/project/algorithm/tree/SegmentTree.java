package mouse.project.algorithm.tree;

import lombok.Data;

@Data
public class SegmentTree {
    private int lower;
    private int upper;
    private YTree yTree;

    public SegmentTree(int lowerMost, int upperMost) {
        this.lower = lowerMost;
        this.upper = upperMost;
        left = null;
        right = null;
        yTree = new YTreeImpl();
    }

    private SegmentTree left;
    private SegmentTree right;
}
