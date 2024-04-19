package mouse.project.ui.components.point;

import lombok.Data;
import mouse.project.saver.Savable;
import mouse.project.ui.components.main.Drawable;
import mouse.project.utils.math.Position;

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
        return Optional.empty();
    }

    @Override
    public void draw(Graphics2D g2d) {
    }

    @Override
    public int depth() {
        return 5;
    }
}
