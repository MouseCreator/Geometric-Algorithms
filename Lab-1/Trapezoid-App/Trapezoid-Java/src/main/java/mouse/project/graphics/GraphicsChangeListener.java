package mouse.project.graphics;

import mouse.project.algorithm.impl.gfx.GFX;

import java.util.Collection;

public interface GraphicsChangeListener {
    void add(GFX gfx);
    void clear();
    Collection<GFX> getAll();
    void highlight(GFX gfx);
}
