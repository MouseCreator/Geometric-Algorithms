package mouse.project.algorithm.gfx;

import lombok.EqualsAndHashCode;
import mouse.project.state.ConstUtils;
import mouse.project.utils.math.Position;

import java.awt.*;
@EqualsAndHashCode
public class CircleGFX implements GFX{

    private final Position at;
    private final int radius;
    public CircleGFX(Position from, int radius) {
        this.at = from;
        this.radius = radius;
    }

    @Override
    public int depth() {
        return 10;
    }
    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(ConstUtils.POINT_SELECTED_COLOR);
        int diameter = ConstUtils.SELECT_NODE_DIAMETER;
        int radius = diameter >>> 1;
        g2d.fillOval(at.x() - radius, at.y() - radius, diameter, diameter);
    }
}
