package mouse.project.math;

import lombok.Data;

@Data
public class FSegment {
    private FPosition from;
    private FPosition to;

    public FSegment(FPosition pI, FPosition pJ) {
        this.from = pI;
        this.to = pJ;
    }

    public GenLine bisector() {
        return GenLine.of(vector().orthogonal(), middle());
    }
    public FPosition middle() {
        return FPosition.of((from.x() + to.x()) / 2.0, (from.y() + to.y()) / 2.0);
    }
    public double length() {
        return vector().magnitude();
    }
    public Vector2 vector() {
        return Vector2.from(from, to);
    }
}
