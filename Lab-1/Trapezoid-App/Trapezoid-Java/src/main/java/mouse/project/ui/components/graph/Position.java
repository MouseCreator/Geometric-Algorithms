package mouse.project.ui.components.graph;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class Position {
    private final int x;
    private final int y;
    private Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public static Position of(int x, int y) {
        return new Position(x, y);
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public double distanceTo(Position other) {
        return Math.sqrt((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y));
    }
}
