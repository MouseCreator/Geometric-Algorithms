package mouse.project.ui.components.point;

import lombok.Data;
import mouse.project.saver.Savable;
import mouse.project.state.ConstUtils;
import mouse.project.ui.components.main.Drawable;
import mouse.project.utils.math.Box;
import mouse.project.utils.math.MathUtils;
import mouse.project.utils.math.Position;
import mouse.project.utils.math.Vector2;

import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Data
public class Segment implements Savable, Drawable {

    private String id;
    private SegmentEnd from;
    private SegmentEnd to;
    @Override
    public String key() {
        return "segment";
    }

    @Override
    public Collection<Object> supply() {
        if (id == null) {
            throw new IllegalStateException("Cannot save Segment if key is null");
        }
        Integer x1 = from.getPosition().x();
        Integer y1 = from.getPosition().y();
        Integer x2 = to.getPosition().x();
        Integer y2 = to.getPosition().y();
        return List.of(id, x1, y1, x2, y2);
    }

    @Override
    public void consume(List<String> strings) {
        Integer expected = 5;
        Integer actual = strings.size();
        if (!actual.equals(expected)) {
            throw new IllegalStateException("Cannot load Segment. Expected " + expected + " but got " + actual + " strings");
        }
        id = strings.get(0);
        Integer x1 = fromInt(strings.get(1));
        Integer y1 = fromInt(strings.get(2));
        Integer x2 = fromInt(strings.get(3));
        Integer y2 = fromInt(strings.get(4));

        from = new SegmentEnd(Position.of(x1, y1));
        to = new SegmentEnd(Position.of(x2, y2));
    }

    private Integer fromInt(String s) {
        return Integer.parseInt(s);
    }

    public Optional<SegmentEnd> getEndAt(Position position) {
        double p1 = position.distanceTo(from.getPosition());
        double p2 = position.distanceTo(to.getPosition());
        boolean p1Close = p1 < ConstUtils.END_AT_TOLERANCE;
        boolean p2Close = p2 < ConstUtils.END_AT_TOLERANCE;
        if (p1Close && p2Close) {
            return p1 < p2 ? Optional.of(from) : Optional.of(to);
        }
        if (p1Close) {
            return Optional.of(from);
        }
        if (p2Close) {
            return Optional.of(to);
        }
        return Optional.empty();
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(ConstUtils.SEGMENT_THICKNESS));
        g2d.setColor(ConstUtils.SEGMENT_COLOR);
        int x1 = from.getPosition().x();
        int y1 = from.getPosition().y();
        int x2 = to.getPosition().x();
        int y2 = to.getPosition().y();

        g2d.drawLine(x1, y1, x2, y2);

        int diameter = ConstUtils.SEGMENT_END_DIAMETER;
        int radius = diameter >> 1;
        g2d.fillOval(from.getPosition().x() - radius, from.getPosition().y() - radius, diameter, diameter);
        g2d.fillOval(to.getPosition().x() - radius, to.getPosition().y() - radius, diameter, diameter);
    }

    @Override
    public int depth() {
        return 5;
    }

    public double length() {
        return from.getPosition().distanceTo(to.getPosition());
    }

    public boolean goesThrough(Position position) {
        return isClose(position);
    }

    private boolean isClose(Position c) {
        Position a = from.getPosition();
        Position b = to.getPosition();
        Vector2 ac = Vector2.from(a, c);
        Vector2 ad = MathUtils.getVectorProjection(a, b, c);
        double magnitude = ac.subtract(ad).magnitude();
        return insideEdgeBox(c) && magnitude < ConstUtils.END_AT_TOLERANCE;
    }

    private boolean insideEdgeBox(Position c) {
        Position p1 = from.getPosition();
        Position p2 = to.getPosition();
        Box box = new Box(p1, p2);
        return box.contains(c);
    }
}
