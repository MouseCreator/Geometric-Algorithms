package mouse.project.ui.components.point;

import lombok.Data;
import mouse.project.utils.math.Position;
@Data
public class SegmentEnd {
    private Position position;
    public SegmentEnd(Position of) {
        this.position = of;
    }
}
