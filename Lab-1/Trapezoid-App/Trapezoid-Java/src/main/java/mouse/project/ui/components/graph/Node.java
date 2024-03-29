package mouse.project.ui.components.graph;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import mouse.project.state.ConstUtils;
import mouse.project.state.State;
import mouse.project.ui.components.main.Drawable;
import mouse.project.utils.math.Position;

import java.awt.*;
@Getter
@EqualsAndHashCode
public class Node implements Drawable {
    @EqualsAndHashCode.Exclude
    private Position position;

    private final String id;

    private final boolean isExtra;
    public Node(String id, Position position, boolean isExtra) {
        this.position = position;
        this.isExtra = isExtra;
        this.id = id;
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (isExtra) {
            g2d.setColor(ConstUtils.EXTRA_NODE_COLOR);
        } else {
            g2d.setColor(ConstUtils.NODE_COLOR);
        }
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
        return 120;
    }

    public void moveTo(Position position) {
        this.position = position;
    }


}
