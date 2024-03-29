package mouse.project.algorithm.impl.gfx;

import mouse.project.utils.math.Position;

public class LineGFXImpl implements LineGFX {
    private final Position from;
    private final Position to;
    public LineGFXImpl(Position from, Position to) {
        this.from = from;
        this.to = to;
    }
}
