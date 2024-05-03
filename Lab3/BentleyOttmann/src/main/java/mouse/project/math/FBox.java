package mouse.project.math;

public class FBox {
    private final FPosition bottomLeft;
    private final FPosition topRight;

    public FBox(FPosition p1, FPosition p2) {
        double lowerX = Math.min(p1.x(), p2.x());
        double lowerY = Math.min(p1.y(), p2.y());
        double upperX = Math.max(p1.x(), p2.x());
        double upperY = Math.max(p1.y(), p2.y());
        bottomLeft = FPosition.of(lowerX, lowerY);
        topRight = FPosition.of(upperX, upperY);
    }

    public FBox(double x1, double y1, double x2, double y2) {
        this(FPosition.of(x1, y1), FPosition.of(x2, y2));
    }

    public boolean contains(FPosition a) {
        double y = a.y();
        double x = a.x();
        return left() <= x && bottom() <= y && x <= right() && y <= top();
    }

    public double top() {
        return topRight.y();
    }
    public double bottom() {
        return bottomLeft.y();
    }
    public double left() {
        return bottomLeft.x();
    }
    public double right() {
        return topRight.x();
    }
}
