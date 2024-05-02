package mouse.project.algorithm.sweep;

import lombok.Data;
import mouse.project.math.GenLine;
import mouse.project.math.Position;
import mouse.project.math.Vector2;

import java.util.Optional;

@Data
public class TSegment {
    private final String id;
    private final Position upper;
    private final Position lower;

    public TSegment(String id, Position from, Position to) {
        upper= from;
        lower = to;
        this.id = id;
    }

    public Vector2 direction() {
        return Vector2.from(upper, lower).unit();
    }
    public Optional<Integer> getX(int y) {
        Optional<Integer> optX = asLine().calculateX(y);
        if (optX.isEmpty()) {
            return optX;
        }
        int x = optX.get();
        int min = Math.min(upper.x(), lower.x());
        int max = Math.max(upper.x(), lower.x());
        if (x < min || x > max) {
            return Optional.empty();
        }
        return optX;
    }
    public GenLine asLine() {
        int x0 = upper.x();
        int x1 = lower.x();
        int y0 = upper.y();
        int y1 = lower.y();
        if (x0 == x1) {
           return new GenLine(1, 0, -x0);
        }
        double k = (double) (y1 - y0) / (double) (x1 - x0);
        double b = y1 - k * x1;

        return new GenLine(k, -1, b);
    }

    @Override
    public String toString() {
        return id + ":" + upper + "->" + lower;
    }
}
