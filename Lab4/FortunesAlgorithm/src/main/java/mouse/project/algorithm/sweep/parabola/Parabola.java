package mouse.project.algorithm.sweep.parabola;

import mouse.project.math.FPosition;

public class Parabola {
    final double a;
    final double b;
    final double c;

    public Parabola(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    public double yAt(double x) {
        return x * x * a + b * x + c;
    }
    public FPosition lowestPoint() {
        double x = -b / (2 * a);
        double y = yAt(x);
        return FPosition.of(x, y);
    }

}
