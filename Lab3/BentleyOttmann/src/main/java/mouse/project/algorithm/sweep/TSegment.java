package mouse.project.algorithm.sweep;

import lombok.Data;
import mouse.project.math.Position;
import mouse.project.math.Vector2;

@Data
public class TSegment {
    private Position upper;
    private Position lower;
    public Vector2 direction() {
        return Vector2.from(upper, lower).unit();
    }
}
