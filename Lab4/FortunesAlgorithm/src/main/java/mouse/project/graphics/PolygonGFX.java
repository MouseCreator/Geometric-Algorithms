package mouse.project.graphics;

import mouse.project.math.Position;
import mouse.project.state.ConstUtils;

import java.awt.*;
import java.util.List;
public class PolygonGFX implements GFX{
    private final List<Position> positions;
    private final Color color;
    public PolygonGFX(List<Position> positions, Color color) {
        this.positions = positions;
        this.color = color;
    }

    @Override
    public void draw(Graphics2D g2d) {
        Polygon polygon = new Polygon();

        for (Position position : positions) {
            polygon.addPoint(position.x(), position.y());
        }

        g2d.setColor(color);
        g2d.fillPolygon(polygon);

        g2d.setColor(ConstUtils.BORDER_COLOR);
        g2d.setStroke(new java.awt.BasicStroke(ConstUtils.BORDER_THICKNESS));
        g2d.drawPolygon(polygon);
    }
}
