package mouse.project.ui.components.point;

import mouse.project.state.ConstUtils;
import mouse.project.ui.components.main.Drawable;
import mouse.project.utils.math.Position;

import java.awt.*;

public class WrapBox implements Drawable {
    public WrapBox(Position from) {
        this.from = from;
        this.to = from;
    }
    private Position from;
    private Position to;

    public void moveFrom(Position position) {
        this.from = position;
    }

    public void moveTo(Position position) {
        this.to = position;
    }
    @Override
    public void draw(Graphics2D g2d) {
        Color semiTransparentRed = ConstUtils.FILL_COLOR;
        g2d.setColor(semiTransparentRed);
        Position point1 = from;
        Position point2 = to;
        int x = Math.min(point1.x(), point2.x());
        int y = Math.min(point1.y(), point2.y());
        int width = Math.abs(point1.x() - point2.x());
        int height = Math.abs(point1.y() - point2.y());

        g2d.fillRect(x, y, width, height);

        g2d.setColor(ConstUtils.BOX_COLOR);
        g2d.drawRect(x, y, width, height);
    }

    @Override
    public int depth() {
        return 2;
    }
}
