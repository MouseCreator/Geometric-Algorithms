package mouse.project.math;

import lombok.EqualsAndHashCode;

import java.util.Optional;

@EqualsAndHashCode
public class FPosition {
    private final double x;
    private final double y;
    private FPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public static FPosition of(double x, double y) {
        return new FPosition(x, y);
    }

    public static FPosition zeros() {
        return FPosition.of(0,0);
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double distanceTo(FPosition other) {
        return Math.sqrt((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y));
    }

    @Override
    public String toString() {
        return String.format("{%.2f %.2f}", x, y);
    }

    public static Optional<FPosition> opt(double x, double y) {
        return Optional.of(FPosition.of(x,y));
    }

    public FPosition move(Vector2 vector2) {
        return FPosition.of(x + vector2.x(), y + vector2.y());
    }

    public Position ints() {
        int xR = (int) Math.round(x);
        int yR = (int) Math.round(y);
        return Position.of(xR, yR);
    }
}