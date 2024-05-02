package mouse.project.math;

import java.util.Optional;

public record GenLine(double a, double b, double c){

    private static final double TOLERANCE = 0.00001;

    @Override
    public String toString() {
        return String.format("%.2f*x+%.2f*y+%.2f",a,b,c);
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

    public Optional<Integer> calculateX(int y) {
        if (Math.abs(a) < TOLERANCE) {
            return Optional.empty();
        }
        int res = (int) ((b*y+c) / -a);
        return Optional.of(res);
    }
    public Optional<Integer> calculateY(int x) {
        if (Math.abs(b) < TOLERANCE) {
            return Optional.empty();
        }
        int res = (int) ((a*x+c) / -b);
        return Optional.of(res);
    }

    public boolean isParallelToOx() {
        return Math.abs(b) < TOLERANCE;
    }

    public Optional<Position> intersectionPoint(GenLine o) {
        if (isParallelTo(o)) {
            return Optional.empty();
        }
        if (isParallelToOx()) {
            int x = (int) (-c / a);
            Optional<Integer> yOpt = o.calculateY(x);
            return yOpt.map(y -> Position.of(x,y));
        }
        if (o.isParallelToOx()) {
            int x = (int) (-o.c / -o.a);
            Optional<Integer> yOpt = calculateY(x);
            return yOpt.map(y -> Position.of(x, y));
        }

        int y = (int) ((c * o.a - o.c * a) / (o.b * a - b * o.a));
        int x = (int) ((- b*y - c) / a);
        return Optional.of(Position.of(x, y));
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
