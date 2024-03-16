package mouse.project.ui.components.graph;

import mouse.project.state.ConstUtils;
import mouse.project.ui.components.main.Drawable;

import java.awt.*;

public record Edge(Node node1, Node node2, boolean isExtra) implements Drawable {
    @Override
    public void draw(Graphics2D g2d) {
        if (isExtra) {
            g2d.setColor(ConstUtils.EXTRA_EDGE_COLOR);
        } else {
            g2d.setColor(ConstUtils.EDGE_COLOR);
        }
        g2d.setStroke(new BasicStroke(2));

        int x1 = node1.getPosition().x();
        int y1 = node1.getPosition().y();
        int x2 = node2.getPosition().x();
        int y2 = node2.getPosition().y();

        g2d.drawLine(x1, y1, x2, y2);
    }

    @Override
    public int depth() {
        return 2;
    }

    public boolean hasNode(Node node) {
        return node.equals(node1) || node.equals(node2);
    }
}
