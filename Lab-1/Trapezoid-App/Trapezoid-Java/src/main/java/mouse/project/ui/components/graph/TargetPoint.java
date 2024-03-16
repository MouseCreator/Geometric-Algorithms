package mouse.project.ui.components.graph;

import lombok.EqualsAndHashCode;
import mouse.project.state.ConstUtils;
import mouse.project.ui.components.main.Drawable;

import java.awt.*;
@EqualsAndHashCode
public final class TargetPoint implements Drawable {
    private Position position;

    public TargetPoint(Position position) {
        this.position = position;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(ConstUtils.TARGET_COLOR);
        int diameter = ConstUtils.NODE_DIAMETER;
        int radius = diameter >> 1;
        g2d.fillOval(position.x() - radius, position.y() - radius, diameter, diameter);
    }

    @Override
    public int depth() {
        return 20;
    }

    public void moveTo(Position position) {
        this.position = position;
    }

    public Position position() {
        return position;
    }

    @Override
    public String toString() {
        return "TargetPoint[" +
                "position=" + position + ']';
    }

}
