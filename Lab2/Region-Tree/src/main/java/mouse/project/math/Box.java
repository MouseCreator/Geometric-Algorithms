package mouse.project.math;

public class Box {

    private final Position bottomLeft;
    private final Position topRight;

    public Box(Position p1, Position p2) {
        int lowerX = Math.min(p1.x(), p2.x());
        int lowerY = Math.min(p1.y(), p2.y());
        int upperX = Math.max(p1.x(), p2.x());
        int upperY = Math.max(p1.y(), p2.y());
        bottomLeft = Position.of(lowerX, lowerY);
        topRight = Position.of(upperX, upperY);
    }

    public boolean contains(Position a) {
        int y = a.y();
        int x = a.x();
        return left() <= x && bottom() <= y && x <= right() && y <= top();
    }

    public int top() {
        return topRight.y();
    }
    public int bottom() {
        return bottomLeft.y();
    }
    public int left() {
        return bottomLeft.x();
    }
    public int right() {
        return topRight.x();
    }
}
