package mouse.project.algorithm.impl.gfx;

import mouse.project.state.ConstUtils;
import mouse.project.utils.math.Position;

import java.awt.*;

public class HorizontalLineGFX implements LineGFX {
    private final Position from;
    private final Position to;
    private Color color;
    private int depth;
    private boolean highlighted = false;
    @Override
    public int depth() {
        return depth;
    }

    public HorizontalLineGFX(Position from, Position to) {
        this.from = from;
        this.to = to;
        dehighlight();
    }

    @Override
    public void dehighlight() {
        if (!highlighted) return;
        highlighted = false;
        color = ConstUtils.EXTRA_LINE;
        depth = 1;
    }

    @Override
    public void highlight() {
        if (highlighted) return;
        highlighted = true;
        color = ConstUtils.HIGHLIGHT_LINE;
        depth = 60;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(from.x(), from.y(), to.x(), to.y());
    }
}
