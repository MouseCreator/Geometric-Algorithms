package mouse.project.ui.components.graph;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
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
}
