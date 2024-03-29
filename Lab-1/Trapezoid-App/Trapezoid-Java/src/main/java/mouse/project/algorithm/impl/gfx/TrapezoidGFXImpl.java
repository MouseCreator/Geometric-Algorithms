package mouse.project.algorithm.impl.gfx;

import mouse.project.utils.math.Position;

import java.awt.*;

public class TrapezoidGFXImpl implements TrapezoidGFX {
    private final Position p1;
    private final Position p2;
    private final Position p3;
    private final Position p4;
    private Color color;

    public TrapezoidGFXImpl(Position p1, Position p2, Position p3, Position p4) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        color = Colors.generateSubtle();
    }

    @Override
    public void dehighlight() {
        color = Colors.dehighlight(color);
    }

    @Override
    public void highlight() {
        color = Colors.highlight(color);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        int[] xPoints = {p1.x(), p2.x(), p3.x(), p4.x()};
        int[] yPoints = {p1.y(), p2.y(), p3.y(), p4.y()};
        int numPoints = 4;

        Polygon polygon = new Polygon(xPoints, yPoints, numPoints);
        g2d.draw(polygon);
    }
}
