package mouse.project.ui.components.main;

import java.awt.*;

public interface Drawable extends Comparable<Drawable> {
    void draw(Graphics2D g2d);
    default int depth() {
        return 0;
    }
    default int compareTo(Drawable o) {
        return depth() - o.depth();
    }
}
