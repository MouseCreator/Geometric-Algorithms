package mouse.project.ui.components.main;

import java.awt.*;

public interface Drawable{
    void draw(Graphics2D g2d);
    default int depth() {
        return 0;
    }
}
