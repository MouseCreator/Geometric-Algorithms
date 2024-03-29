package mouse.project.algorithm.impl.gfx;

import lombok.EqualsAndHashCode;
import mouse.project.state.ConstUtils;
import mouse.project.utils.math.Position;

import java.awt.*;
@EqualsAndHashCode
public class EdgeLineGFX implements LineGFX {
    private final Position from;
    private final Position to;
    @EqualsAndHashCode.Exclude
    private Color color;
    @EqualsAndHashCode.Exclude
    private boolean highlight = true;
    public EdgeLineGFX(Position from, Position to) {
        this.from = from;
        this.to = to;
        dehighlight();
    }
    @Override
    public void dehighlight() {
        if (!highlight) return;
        highlight = false;
        color = ConstUtils.TRANSPARENT;
    }

    @Override
    public int depth() {
        return 60;
    }

    @Override
    public void highlight() {
        if (highlight) return;
        highlight = true;
        color = ConstUtils.HIGHLIGHT_LINE;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(from.x(), from.y(), to.x(), to.y());
    }
}
