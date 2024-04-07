package mouse.project.ui.components.point;

import lombok.Getter;
import mouse.project.utils.math.Position;
@Getter
public class Point {
    private final Position position;
    public Point(Position position) {
        this.position = position;
    }
}
