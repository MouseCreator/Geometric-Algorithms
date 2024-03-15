package mouse.project.ui.components.draw;

import mouse.project.ui.components.main.Drawable;

import java.awt.*;

public interface DrawManager {
    void onAdd(Drawable drawable);
    void drawAll(Graphics2D g2d);
    void onRemove(Drawable drawable);
}
