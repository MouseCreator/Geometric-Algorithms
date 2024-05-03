package mouse.project.algorithm.sweep;

import lombok.Data;
import mouse.project.math.FPosition;
import mouse.project.math.GenLine;
import mouse.project.math.Vector2;

import java.util.Optional;

@Data
public class TSegment {
    private final String id;
    private final FPosition upper;
    private final FPosition lower;

    public TSegment(String id, FPosition from, FPosition to) {
        upper= from;
        lower = to;
        this.id = id;
    }

    public Vector2 direction() {
        return Vector2.from(upper, lower).unit();
    }
    public Optional<Double> getX(double y) {
        return asLine().calculateX(y);
    }
    public GenLine asLine() {
        double x0 = upper.x();
        double x1 = lower.x();
        double y0 = upper.y();
        double y1 = lower.y();
        if (x0 == x1) {
           return new GenLine(1, 0, -x0);
        }
        double k = (double) (y1 - y0) / (double) (x1 - x0);
        double b = y1 - k * x1;

        return new GenLine(k, -1, b);
    }

    @Override
    public String toString() {
        return id;
    }
}
