package mouse.project.algorithm.tree;

import mouse.project.algorithm.common.CPoint;

import java.util.*;

public class YTreeImpl implements YTree {
    private YTreeNode root;
    @Override
    public void buildBalancedFrom(Collection<CPoint> pointsYSorted) {
        List<CPoint> points = new ArrayList<>(pointsYSorted);
        root = buildBalanced(0, points.size(), points, null, false);
    }

    private YTreeNode buildBalanced(int from, int to, List<CPoint> points, YTreeNode parent, boolean movedLeft) {
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
        YTreeNode onLeft = buildBalanced(from, mid, points, node, true);
        if (onLeft != null) {
            node.left = onLeft;
        }
        YTreeNode onRight = buildBalanced(mid+1, to, points, node, false);
        if (onRight != null) {
            node.right = onRight;
        }
        return node;
    }

    public List<CPoint> getAll() {
        return getRange(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    public List<CPoint> getRange(int yMin, int yMax) {
        YTreeNode current = findGreaterOrEquals(yMin);
        if (current == null) {
            return new ArrayList<>();
        }
        List<CPoint> result = new ArrayList<>();
        findInRangeRight(current, result, yMax);
        return new ArrayList<>(result);
    }

    private void findInRangeLeft(YTreeNode current, List<CPoint> result, int yMax) {
        if (current == null) {
            return;
        }
        if (current.hasNoLeft()) {
            findInRangeRight(current, result, yMax);
        } else {
            TreeSet set = new TreeSet();
            set.subSet(0, 1)
            findInRangeLeft(current.prev(), result, yMax);
        }
    }

    private void findInRangeRight(YTreeNode current, List<CPoint> result, int yMax) {
        if (current == null) {
            return;
        }
        if (current.point.position().y() > yMax) {
            return;
        }
        result.add(current.point);
        if (current.rightBr) {
            findInRangeRight(current.next(), result, yMax);
        } else {
            findInRangeLeft(current.next(), result, yMax);
        }
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
                newOne.leftBr = true;
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
                newOne.rightBr = true;
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
