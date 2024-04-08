package mouse.project.ui.components.point;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import mouse.project.saver.Savable;
import mouse.project.state.ConstUtils;
import mouse.project.state.State;
import mouse.project.ui.components.main.Drawable;
import mouse.project.utils.math.Position;

import java.util.List;
import java.awt.*;
import java.util.Collection;
import java.util.Objects;
@ToString
public final class TPoint implements Drawable, Savable {

    public static final String KEY = "point";
    private Position position;

    @Getter
    @Setter
    private String id;
    public boolean hasId() {
        return id != null;
    }
    public TPoint(Position position) {
        this.position = position;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(ConstUtils.POINT_COLOR);
        int diameter = ConstUtils.NODE_DIAMETER;
        int radius = diameter >> 1;
        g2d.fillOval(position.x() - radius, position.y() - radius, diameter, diameter);
        drawText(g2d, radius);
    }

    private void drawText(Graphics2D g2d, int radius) {
        g2d.setColor(Color.BLACK);
        String positionText = createLabel();
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(positionText);
        int textHeight = fontMetrics.getHeight();
        int textX = position.x() - radius - textWidth - 5;
        int textY = position.y() + (textHeight / 2);
        g2d.drawString(positionText, textX, textY);
    }

    private String createLabel() {
        String s = "";
        if (State.get().getGraphicState().isShowNames()) {
            s += id;
        }
        if (State.get().getGraphicState().isShowCoordinates()) {
            s += position.toString();
        }
        return s;
    }

    @Override
    public int depth() {
        return 10;
    }

    @Override
    public String key() {
        return KEY;
    }

    @Override
    public Collection<Object> supply() {
        return List.of(id, position.x(), position.y());
    }

    @Override
    public void consume(List<String> strings) {
        String id = strings.get(0);
        String x = strings.get(1);
        String y = strings.get(2);
        this.position = Position.of(Integer.parseInt(x), Integer.parseInt(y));
        this.id = id;
    }

    public void moveTo(Position position) {
        this.position = position;
    }

    public Position position() {
        return position;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (TPoint) obj;
        return Objects.equals(this.position, that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }


}
