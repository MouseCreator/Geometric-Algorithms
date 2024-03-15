package mouse.project.ui.components.draw;

import mouse.project.ui.components.main.Drawable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DrawManagerImpl implements DrawManager {

    private final List<Drawable> list;

    public DrawManagerImpl() {
        list = new ArrayList<>();
    }

    @Override
    public void onAdd(Drawable drawable) {
        list.add(drawable);
        list.sort(Comparator.comparingInt(Drawable::depth));
    }

    @Override
    public void drawAll(Graphics2D g2d) {
        list.forEach(d -> d.draw(g2d));
    }

    @Override
    public void onRemove(Drawable drawable) {
        list.remove(drawable);
    }
}
