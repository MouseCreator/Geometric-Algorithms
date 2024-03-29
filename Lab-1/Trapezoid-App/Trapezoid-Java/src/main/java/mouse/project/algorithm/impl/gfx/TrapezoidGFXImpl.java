package mouse.project.algorithm.impl.gfx;

import lombok.EqualsAndHashCode;
import mouse.project.state.ConstUtils;
import mouse.project.utils.math.Position;

import java.awt.*;
@EqualsAndHashCode
public class TrapezoidGFXImpl implements TrapezoidGFX {
    private final Position p1;
    private final Position p2;
    private final Position p3;
    private final Position p4;
    @EqualsAndHashCode.Exclude
    private Color color;

    @EqualsAndHashCode.Exclude
    private final Color origin;
    @EqualsAndHashCode.Exclude
    private boolean highlighted = true;
    @EqualsAndHashCode.Exclude
    private int depth;

    public TrapezoidGFXImpl(Position p1, Position p2, Position p3, Position p4) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        origin = Colors.generateSubtle();
        dehighlight();
    }

    @Override
    public int depth() {
        return depth;
    }

    @Override
    public void dehighlight() {
        if (!highlighted) return;
        highlighted = false;
        color = origin;
        depth = -6;
    }

    @Override
    public void highlight() {
        if (highlighted) return;
        highlighted = true;
        color = ConstUtils.HIGHLIGHT_LINE;
        depth = 6;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        int[] xPoints = {p1.x(), p2.x(), p3.x(), p4.x()};
        int[] yPoints = {p1.y(), p2.y(), p3.y(), p4.y()};
        int numPoints = 4;

        Polygon polygon = new Polygon(xPoints, yPoints, numPoints);
        g2d.fillPolygon(polygon);
    }
}
