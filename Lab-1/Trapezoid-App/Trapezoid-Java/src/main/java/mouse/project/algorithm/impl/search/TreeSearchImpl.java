package mouse.project.algorithm.impl.search;

import mouse.project.algorithm.impl.sweep.EdgeHelper;
import mouse.project.algorithm.impl.trapezoid.Edge;
import mouse.project.algorithm.impl.tree.Tree;
import mouse.project.algorithm.impl.tree.TreeEdgeElement;
import mouse.project.algorithm.impl.tree.TreeHorizontalElement;
import mouse.project.utils.math.Position;

public class TreeSearchImpl implements TreeSearch {
    private final static int CLOSE = 2;
    @Override
    public Tree find(Tree tree, Position position) {
        return findElement(tree, position);
    }

    private Tree findElement(Tree tree, Position position) {
        if (tree==null) {
            throw new IllegalArgumentException("Null tree while looking for: " + position);
        }
        if (tree.isEdge()) {
            TreeEdgeElement edgeElement = (TreeEdgeElement) tree;
            Edge edge = edgeElement.getEdge();
            int distance = getDistance(edge, position);
            return move(tree, position, distance);
        } else if (tree.isHorizontal()) {
            TreeHorizontalElement horizontalElement = (TreeHorizontalElement) tree;
            int lineY = horizontalElement.getLineY();
            int distance = getDistance(position, lineY);
            return move(tree, position, distance);
        } else if (tree.isLeaf()) {
            return tree;
        }
        throw new IllegalStateException("Unknown element: " + tree);
    }

    private Tree move(Tree tree, Position position, int distance) {
        if (distance > CLOSE) {
            return findElement(tree.getRight(), position);
        } else if (distance < -CLOSE) {
            return findElement(tree.getLeft(), position);
        } else {
            return tree;
        }
    }


    private int getDistance(Position position, int lineY) {
        return position.y() - lineY;
    }

    private int getDistance(Edge edge, Position position) {
        int x = EdgeHelper.getX(edge, position.y());
        return x - position.x();
    }
}
