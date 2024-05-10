package mouse.project.math;

import java.util.Optional;

public record GenLine(double a, double b, double c){

    private static final double TOLERANCE = 0.00001;

    public static GenLine of(Vector2 direction, FPosition point) {
        if (Numbers.dEquals(direction.magnitude(), 0)) {
            throw new IllegalArgumentException("Cannot construct a line with no direction");
        }
        if (Numbers.dEquals(direction.x(), 0)) {
            double a = 1;
            double c = -a * point.x();
            return new GenLine(a, 0, c);
        }
        double b = 1;
        FPosition v2 = point.move(direction);

        double x1 = point.x();
        double y1 = point.y();
        double x2 = v2.x();
        double y2 = v2.y();

        double a = (y1 - y2) / (x2 - x1);
        double c = - a * x1 - y1;

        return new GenLine(a, b, c);
    }

    @Override
    public String toString() {

        String a1 = String.format("%.2f*x", a);
        String b1 = String.format("%.2f*y", b);
        String c1 = String.format("%.2f", c);
        String result = a1;
        if (b > 0) {
            result += "+";
        }
        result += b1;
        if (c > 0) {
            result += "+";
        }
        result += c1;
        return result;
    }

    public boolean isParallelTo(GenLine o) {
        if (Math.abs(a) < TOLERANCE && Math.abs(o.a) < TOLERANCE) {
            return true;
        }
        if (Math.abs(b) < TOLERANCE && Math.abs(o.b) < TOLERANCE) {
            return true;
        }

        return Math.abs(a / o.a - b / o.b) < TOLERANCE;
    }

    public Optional<Double> calculateX(double y) {
        if (Math.abs(a) < TOLERANCE) {
            return Optional.empty();
        }
        double res = ((b*y+c) / -a);
        return Optional.of(res);
    }
    public Optional<Double> calculateY(double x) {
        if (Math.abs(b) < TOLERANCE) {
            return Optional.empty();
        }
        double res = ((a*x+c) / -b);
        return Optional.of(res);
    }

    public boolean isParallelToOx() {
        return Math.abs(a) < TOLERANCE;
    }

    public Optional<FPosition> intersectionPoint(GenLine o) {
        if (isParallelTo(o)) {
            return Optional.empty();
        }
        if (isParallelToOx()) {
            double y = (-c / b);
            Optional<Double> xOpt = o.calculateX(y);
            return xOpt.map(x -> FPosition.of(x,y));
        }
        if (o.isParallelToOx()) {
            double y = (-o.c / o.b);
            Optional<Double> xOpt = calculateX(y);
            return xOpt.map(x -> FPosition.of(x,y));
        }

        double y = ((c * o.a - o.c * a) / (o.b * a - b * o.a));
        double x = ((- b*y - c) / a);
        return FPosition.opt(x, y);
    }

    public boolean overlaps(GenLine o) {
        if (!isParallelTo(o)) {
            return false;
        }

        if (Math.abs(c) < TOLERANCE && Math.abs(o.c) < TOLERANCE) {
            return true;
        }

        if (isParallelToOx() && o.isParallelToOx()) {
            return Numbers.dEquals(c, o.c);
        }

        return Math.abs(a / o.a - c / o.c) < TOLERANCE;

    }
}
