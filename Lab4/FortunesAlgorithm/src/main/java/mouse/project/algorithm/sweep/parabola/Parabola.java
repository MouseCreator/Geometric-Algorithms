package mouse.project.algorithm.sweep.parabola;

import mouse.project.math.FPosition;


public record Parabola(double a, double b, double c) {
    public double yAt(double x) {
        return x * x * a + b * x + c;
    }

    public FPosition lowestPoint() {
        double x = -b / (2 * a);
        double y = yAt(x);
        return FPosition.of(x, y);
    }

}
