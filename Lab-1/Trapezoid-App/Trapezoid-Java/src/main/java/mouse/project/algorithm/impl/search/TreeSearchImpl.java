package mouse.project.algorithm.impl.search;

import mouse.project.algorithm.impl.sweep.EdgeHelper;
import mouse.project.algorithm.impl.trapezoid.Edge;
import mouse.project.algorithm.impl.tree.Tree;
import mouse.project.algorithm.impl.tree.TreeEdgeElement;
import mouse.project.algorithm.impl.tree.TreeHorizontalElement;
import mouse.project.utils.math.Position;

public class TreeSearchImpl implements TreeSearch {
    @Override
    public Trace find(Tree tree, Position position) {
        TraceImpl trace = new TraceImpl();
        find(tree, position, trace);
        return trace;
    }

    private void find(Tree tree, Position position, Trace trace) {
        if (tree.isEdge()) {
            TreeEdgeElement edgeElement = (TreeEdgeElement) tree;
            Edge edge = edgeElement.getEdge();
            int distance = getDistance(edge, position);
            appendAndMoveDeeper(tree, position, trace, distance);
        } else if (tree.isHorizontal()) {
            TreeHorizontalElement horizontalElement = (TreeHorizontalElement) tree;
            int lineY = horizontalElement.getLineY();
            int distance = getDistance(position, lineY);
            appendAndMoveDeeper(tree, position, trace, distance);
        } else if (tree.isLeaf()) {
            trace.append(tree, Dir.NONE);
        }
    }

    private void appendAndMoveDeeper(Tree tree, Position position, Trace trace, int distance) {
        if (distance > 0) {
            trace.append(tree, Dir.RIGHT_UP);
            find(tree.getRight(), position, trace);
        } else {
            trace.append(tree, Dir.LEFT_DOWN);
            find(tree.getLeft(), position, trace);
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
