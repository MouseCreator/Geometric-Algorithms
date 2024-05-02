package mouse.project.graphics;

import mouse.project.math.Position;
import mouse.project.state.ConstUtils;

import java.awt.*;

public class IntersectionsGFX implements GFX {

    private final Position at;
    public IntersectionsGFX(Position at) {
        this.at = at;
    }
    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(ConstUtils.INTERSECTION_COLOR);
        int diameter = ConstUtils.INTERSECTION_DIAMETER;
        int radius = diameter >>> 1;
        g2d.fillOval(at.x() - radius, at.y() - radius, diameter, diameter);
    }

    @Override
    public int depth() {
        return 12;
    }
}
