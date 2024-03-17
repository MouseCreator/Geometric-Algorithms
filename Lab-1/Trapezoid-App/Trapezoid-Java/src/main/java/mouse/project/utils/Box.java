package mouse.project.utils;

import mouse.project.ui.components.graph.Position;

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
        return bottomLeft.x() <= x && bottomLeft.y() <= y && x <= topRight.x() && y <= topRight.y();
    }
}
