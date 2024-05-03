package mouse.project.math;

import java.util.Optional;

public record GenLine(double a, double b, double c){

    private static final double TOLERANCE = 0.00001;

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
        return Math.abs(b) < TOLERANCE;
    }

    public Optional<FPosition> intersectionPoint(GenLine o) {
        if (isParallelTo(o)) {
            return Optional.empty();
        }
        if (isParallelToOx()) {
            double x = (-c / a);
            Optional<Double> yOpt = o.calculateY(x);
            return yOpt.map(y -> FPosition.of(x,y));
        }
        if (o.isParallelToOx()) {
            double x = (-o.c / -o.a);
            Optional<Double> yOpt = calculateY(x);
            return yOpt.map(y -> FPosition.of(x, y));
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

        return Math.abs(a / o.a - c / o.c) < TOLERANCE;

    }
}
