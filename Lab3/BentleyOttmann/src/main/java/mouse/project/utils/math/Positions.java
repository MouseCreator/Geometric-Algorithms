package mouse.project.utils.math;

public class Positions {
    public static Position average(Position p1, Position p2) {
        int x_avg = (p1.x() + p2.x()) >>> 1;
        int y_avg = (p1.y() + p2.y()) >>> 1;
        return Position.of(x_avg, y_avg);
    }
}
