package mouse.project.algorithm.impl.call;

import mouse.project.algorithm.impl.trapezoid.Edge;
import mouse.project.algorithm.impl.tree.Tree;
import mouse.project.algorithm.impl.tree.TreeEdgeElement;
import mouse.project.algorithm.impl.tree.TreeHorizontalElement;
import mouse.project.utils.math.Position;

public class Descriptor {
    public String describe(Tree tree) {
        StringBuilder builder = new StringBuilder();
        describe(tree, builder, 0);
        return builder.toString();
    }

    private void describe(Tree tree, StringBuilder builder, int level) {
        builder.append("-".repeat(level));
        if (tree == null) {
            builder.append("NULL\n");
            return;
        }
        describeCurrentElement(tree, builder);
        describe(tree.getLeft(), builder, level+1);
        describe(tree.getRight(), builder, level+1);
    }

    private void describeCurrentElement(Tree tree, StringBuilder builder) {
        if (tree.isHorizontal()) {
            TreeHorizontalElement horiz = (TreeHorizontalElement) tree;
            int lineY = horiz.getLineY();
            builder.append("LINE: ").append(lineY).append("\n");
        } else if (tree.isEdge()) {
            TreeEdgeElement elem = (TreeEdgeElement) tree;
            Edge edge = elem.getEdge();
            Position position = edge.end1().position();
            builder.append("EDGE: ").append(position).append(" ").append(position).append("\n");
        } else if (tree.isLeaf()) {
            builder.append("LEAF\n");
        } else {
            throw new IllegalArgumentException("Unknown tree element");
        }
    }
}
