package mouse.project.algorithm.impl.gfx;

import mouse.project.state.ConstUtils;
import mouse.project.utils.math.Position;

import java.awt.*;

public class EdgeLineGFX implements LineGFX {
    private final Position from;
    private final Position to;
    private Color color;
    public EdgeLineGFX(Position from, Position to) {
        this.from = from;
        this.to = to;
        dehighlight();
    }
    @Override
    public void dehighlight() {
        color = ConstUtils.TRANSPARENT;
    }

    @Override
    public void highlight() {
        color = ConstUtils.HIGHLIGHT_LINE;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(from.x(), from.y(), to.x(), to.y());
    }
}
