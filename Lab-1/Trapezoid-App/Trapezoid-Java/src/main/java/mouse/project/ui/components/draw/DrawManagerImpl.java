package mouse.project.ui.components.draw;

import mouse.project.ui.components.main.Drawable;

import java.awt.*;
import java.util.TreeSet;

public class DrawManagerImpl implements DrawManager {

    private final TreeSet<Drawable> set;

    public DrawManagerImpl() {
        set = new TreeSet<>();
    }

    @Override
    public void onAdd(Drawable drawable) {
        set.add(drawable);
    }

    @Override
    public void drawAll(Graphics2D g2d) {
        set.forEach(d -> d.draw(g2d));
    }

    @Override
    public void onRemove(Drawable drawable) {
        set.remove(drawable);
    }
}
