package mouse.project.ui.components.graph;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import mouse.project.state.ConstUtils;
import mouse.project.ui.components.main.Drawable;

import java.awt.*;
@Getter
@EqualsAndHashCode
public class Node implements Drawable {
    @EqualsAndHashCode.Exclude
    private Position position;

    private final String id;

    private final boolean isExtra;
    public Node(String id, Position position, boolean isExtra) {
        this.position = position;
        this.isExtra = isExtra;
        this.id = id;
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (isExtra) {
            g2d.setColor(ConstUtils.EXTRA_NODE_COLOR);
        } else {
            g2d.setColor(ConstUtils.NODE_COLOR);
        }
        int diameter = ConstUtils.NODE_DIAMETER;
        int radius = diameter >> 1;
        g2d.fillOval(position.x() - radius, position.y() - radius, diameter, diameter);
    }

    @Override
    public int depth() {
        return 3;
    }

    public void moveTo(Position position) {
        this.position = position;
    }


}
