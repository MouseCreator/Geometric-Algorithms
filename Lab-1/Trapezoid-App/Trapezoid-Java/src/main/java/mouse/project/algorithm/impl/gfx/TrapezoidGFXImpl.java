package mouse.project.algorithm.impl.gfx;

import mouse.project.utils.math.Position;

public class TrapezoidGFXImpl implements TrapezoidGFX {
    private final Position p1;
    private final Position p2;
    private final Position p3;
    private final Position p4;

    public TrapezoidGFXImpl(Position p1, Position p2, Position p3, Position p4) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
    }
}
