package mouse.project.algorithm.sweep.circle;

import mouse.project.math.FPosition;
import mouse.project.math.Vector2;

public class Circle {

    private final FPosition center;
    private final double radius;
    public Circle(FPosition center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public FPosition bottom() {
        return center.move(Vector2.of(0, radius));
    }
    public FPosition center() {
        return center;
    }
}
