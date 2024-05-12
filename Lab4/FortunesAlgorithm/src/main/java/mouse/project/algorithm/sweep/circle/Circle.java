package mouse.project.algorithm.sweep.circle;

import mouse.project.algorithm.sweep.parabola.Solver;
import mouse.project.math.FPosition;
import mouse.project.math.GenLine;
import mouse.project.math.Numbers;
import mouse.project.math.Vector2;

import java.util.Optional;

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

    @Override
    public String toString() {
        return "Circle{" +
                 center + "->" +
                 radius +
                '}';
    }
    private record CircleEquation(double a, double b, double r) {
    }
    private CircleEquation toCircleEquation() {
        return new CircleEquation(center.x(), center.y(), radius);
    }
    public FPosition[] intersectionsWith(GenLine line) {
        CircleEquation ce = toCircleEquation();
        double a = line.a();
        double b = line.b();
        double c = line.c();
        double d = ce.a();
        double e = ce.b();
        double r = ce.r();
        if (Numbers.dEquals(b, 0)) {
            double x = - c / a;
            double arg1 = 1;
            double arg2 = - 2 * e;
            double arg3 = e * e - r * r + d * d + c * c / a * a + 2 * c * d / a;
            double[] solutions = Solver.solveQuadratic(arg1, arg2, arg3);
            if (solutions.length == 0) {
                return new FPosition[0];
            }
            if (solutions.length == 1) {
                return new FPosition[]{FPosition.of(x, solutions[0])};
            }
            if (solutions.length == 2) {
                return new FPosition[]{FPosition.of(x, solutions[0]), FPosition.of(x, solutions[1])};
            }
            throw new IllegalStateException("Unexpected number of solutions: " + solutions.length);
        } else {
            double arg1 = 1 + a*a / b*b;
            double arg2 = -2 * d + 2 * a * c / b * b + 2 * e * a / b;
            double arg3 = d * d + c * c / b * b + 2 * e * c / b + e * e - r * r;
            double[] solutions = Solver.solveQuadratic(arg1, arg2, arg3);
            if (solutions.length == 0) {
                return new FPosition[0];
            }
            if (solutions.length == 1) {
                double x = solutions[0];
                double y = findYOfIntersection(line, x);
                return new FPosition[]{FPosition.of(x, y)};
            }
            if (solutions.length == 2) {
                double x1 = solutions[0];
                double x2 = solutions[1];
                double y1 = findYOfIntersection(line, x1);
                double y2 = findYOfIntersection(line, x2);
                return new FPosition[]{FPosition.of(x1, y1), FPosition.of(x2, y2)};
            }
            throw new IllegalStateException("Unexpected number of solutions: " + solutions.length);
        }
    }

    private static Double findYOfIntersection(GenLine line, double x) {
        Optional<Double> yAt = line.calculateY(x);
        assert yAt.isPresent();
        Double y = yAt.get();
        return y;
    }
}
