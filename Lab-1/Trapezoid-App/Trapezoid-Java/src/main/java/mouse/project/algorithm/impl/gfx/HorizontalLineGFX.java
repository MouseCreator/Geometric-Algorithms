package mouse.project.algorithm.impl.gfx;

import lombok.EqualsAndHashCode;
import mouse.project.state.ConstUtils;
import mouse.project.utils.math.Position;

import java.awt.*;
@EqualsAndHashCode
public class HorizontalLineGFX implements LineGFX {
    private final Position from;
    private final Position to;
    @EqualsAndHashCode.Exclude
    private Color color;

    @EqualsAndHashCode.Exclude
    private int depth;
    @EqualsAndHashCode.Exclude
    private boolean highlighted = true;
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
        depth = 10;
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
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(from.x(), from.y(), to.x(), to.y());
    }
}
