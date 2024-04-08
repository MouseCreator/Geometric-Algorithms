package mouse.project.algorithm.descriptor;

import mouse.project.algorithm.common.CPoint;
import mouse.project.algorithm.tree.SegmentTree;
import mouse.project.algorithm.tree.SegmentTreeNode;

import java.util.List;
public class SegmentTreeDescriptor implements Descriptor<String> {
    public String describe(SegmentTree tree) {
        SegmentTreeNode root = tree.getRoot();
        StringBuilder builder = new StringBuilder();
        describeNode(root, builder);
        return builder.toString();
    }

    private void describeNode(SegmentTreeNode node, StringBuilder builder) {
        if (node == null) {
            return;
        }
        List<CPoint> allPoints = node.getYTree().getAll();
        builder.append("[")
                .append(node.getLower())
                .append(", ")
                .append(node.getUpper())
                .append("] = {");
        allPoints.forEach(p -> builder.append(" ").append(p.id()));
        builder.append(" }");
        describeNode(node.getLeft(), builder);
        describeNode(node.getRight(), builder);
    }
}
