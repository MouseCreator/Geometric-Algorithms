package mouse.project.algorithm.tree;

import mouse.project.algorithm.common.CPoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class YTreeImpl implements YTree {
    private YTreeNode root;
    @Override
    public void buildBalancedFrom(Collection<CPoint> pointsYSorted) {
        List<CPoint> points = new ArrayList<>(pointsYSorted);
        root = buildBalanced(0, points.size(), points, null, false);
    }

    private YTreeNode buildBalanced(int from, int to, List<CPoint> points, YTreeNode parent, boolean movedLeft) {
        if (points.size()==3) {
            int ii = 0;
            ii++;
        }
        if (from >= to) {
            return null;
        }
        YTreeNode node;
        int mid = (from + to) >>> 1;
        CPoint targetPoint = points.get(mid);
        if (parent == null) {
            node = new YTreeNode();
            node.point = targetPoint;
        } else {
            if (movedLeft) {
                node = parent.createLeft(targetPoint);
            } else {
                node = parent.createRight(targetPoint);
            }
        }
        if (to - from == 1) {
            return node;
        }
        node.left = buildBalanced(from, mid, points, node, true);
        node.right = buildBalanced(mid+1, to, points, node, false);
        return node;
    }

    public List<CPoint> getAll() {
        return getRange(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    public List<CPoint> getRange(int yMin, int yMax) {
        YTreeNode current = findGreaterOrEquals(yMin);
        List<CPoint> result = new ArrayList<>();
        while (current != null && current.point.position().y() <= yMax) {
            result.add(current.point);
            current = current.next();
        }
        return result;
    }

    private YTreeNode findGreaterOrEquals(int targetY) {
        if (root == null) {
            return null;
        }
        YTreeNode startPoint = null;
        YTreeNode current = root;
        while (current != null) {
            if(targetY < current.point.position().y()) {
                startPoint = current;
                if (current.hasNoLeft()) {
                    break;
                } else {
                    current = current.left;
                }
            } else {
                if (current.hasNoRight()) {
                    break;
                } else {
                    current = current.right;
                }
            }
        }
        return startPoint;
    }
    private static class YTreeNode {
        CPoint point;
        boolean leftBr = false;
        boolean rightBr = false;
        YTreeNode left = null;
        YTreeNode right = null;

        private boolean hasNoRight() {
            return right == null || rightBr;
        }

        private boolean hasNoLeft() {
            return left == null || leftBr;
        }

        private YTreeNode createLeft(CPoint value) {
            YTreeNode newOne = new YTreeNode();
            newOne.point = value;
            if (hasNoLeft()) {
                newOne.left = this.left;
            }
            this.leftBr = false;
            newOne.right = this;
            newOne.rightBr = true;
            return newOne;
        }

        private YTreeNode createRight(CPoint value) {
            YTreeNode newOne = new YTreeNode();
            newOne.point = value;
            if (hasNoRight()) {
                newOne.right = this.right;
            }
            this.rightBr = false;
            newOne.left = this;
            newOne.leftBr = true;
            return newOne;
        }

        public YTreeNode next() {
            return right;
        }
        public YTreeNode prev() {
            return left;
        }
    }
}
