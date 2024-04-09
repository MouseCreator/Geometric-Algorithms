package mouse.project.algorithm.descriptor;

import mouse.project.algorithm.common.CPoint;
import mouse.project.algorithm.tree.SegmentTree;
import mouse.project.algorithm.tree.SegmentTreeNode;

import java.util.List;
public class SegmentTreeDescriptor implements Descriptor<String> {
    public String describe(SegmentTree tree) {
        SegmentTreeNode root = tree.getRoot();
        StringBuilder builder = new StringBuilder();
        describeNode(root, builder, 0);
        return builder.toString();
    }

    private void describeNode(SegmentTreeNode node, StringBuilder builder, int level) {
        if (node == null) {
            return;
        }
        builder.append("\t".repeat(level));
        List<CPoint> allPoints = node.getYTree().getAll();
        builder.append("[")
                .append(node.getLower())
                .append(", ")
                .append(node.getUpper())
                .append(") = {");
        allPoints.forEach(p -> builder.append(" ").append(p.id()));
        builder.append(" }\n");
        describeNode(node.getLeft(), builder, level+1);
        describeNode(node.getRight(), builder, level+1);
    }
}
