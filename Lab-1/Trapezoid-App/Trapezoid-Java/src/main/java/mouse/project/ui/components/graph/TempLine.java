package mouse.project.ui.components.graph;

import lombok.Data;
import mouse.project.state.ConstUtils;
import mouse.project.ui.components.main.Drawable;
import mouse.project.utils.math.Position;

import java.awt.*;

@Data
public class TempLine implements Drawable {
    private Position startAt;
    private Position endAt;

    public TempLine(Position startAt, Position endAt) {
        this.startAt = startAt;
        this.endAt = endAt;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(ConstUtils.EDGE_COLOR);
        g2d.setStroke(new BasicStroke(2));

        int x1 = startAt.x();
        int y1 = startAt.y();
        int x2 = endAt.x();
        int y2 = endAt.y();

        g2d.drawLine(x1, y1, x2, y2);
    }

    @Override
    public int depth() {
        return 10;
    }
}
