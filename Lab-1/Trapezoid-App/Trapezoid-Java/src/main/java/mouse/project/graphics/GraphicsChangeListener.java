package mouse.project.graphics;

import mouse.project.algorithm.impl.gfx.GFX;

public interface GraphicsChangeListener {
    void add(GFX gfx);
    void clear();
    void show();
    void highlight(GFX gfx);
    void dehighlight();
}
