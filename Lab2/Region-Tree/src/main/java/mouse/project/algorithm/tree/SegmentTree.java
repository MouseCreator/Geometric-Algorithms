package mouse.project.algorithm.tree;

import lombok.Data;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
public class SegmentTree {

    private final List<Integer> xCoordinates;

    private final SegmentTreeNode root;

    public SegmentTree(List<Integer> xCoordinates, SegmentTreeNode segmentTreeNode) {
        this.xCoordinates = xCoordinates;
        this.root = segmentTreeNode;
    }

    public int normalize(int x) {
        int result = Collections.binarySearch(xCoordinates, x);
        if (result < 0) {
            return -result + 1;
        }
        return result;
    }
}
