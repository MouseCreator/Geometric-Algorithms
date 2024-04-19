package mouse.project.algorithm.base;

import lombok.Data;
import mouse.project.utils.math.Position;
@Data
public class TSegment {
    private final Position upper;
    private final Position lower;

    public TSegment(Position upper, Position lower) {
        this.upper = upper;
        this.lower = lower;
    }
}
