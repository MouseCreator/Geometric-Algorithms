package mouse.project.utils.math;

public record Line(Position position, Vector2 unit) {
    public Line(Position position, Vector2 unit) {
        this.position = position;
        this.unit = unit.unit();
    }

    public static Line of(Position p1, Position p2) {
        Vector2 direction = Vector2.from(p1, p2);
        return new Line(p1, direction);
    }

    public int getX(int y) {
        Position intersection = MathUtils.getIntersection(this, Line.from(Position.of(0, y), Vector2.of(0, 1)));
        return intersection.x();
    }

    private static Line from(Position of, Vector2 v) {
        return new Line(of, v);
    }

    @Override
    public String toString() {
        return position + "->" + unit.x() + "," + unit.y();
    }
}
