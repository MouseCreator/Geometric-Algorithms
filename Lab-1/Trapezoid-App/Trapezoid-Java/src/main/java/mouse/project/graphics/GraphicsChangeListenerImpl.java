package mouse.project.graphics;

import mouse.project.algorithm.impl.gfx.GFX;
import mouse.project.ui.components.draw.DrawManager;

import java.util.HashSet;
import java.util.Set;

public class GraphicsChangeListenerImpl implements GraphicsChangeListener {
    private final Set<GFX> gfxSet;
    private final DrawManager drawManager;

    public GraphicsChangeListenerImpl(DrawManager drawManager) {
        this.gfxSet = new HashSet<>();
        this.drawManager = drawManager;
    }

    @Override
    public void add(GFX gfx) {
        gfxSet.add(gfx);
    }

    @Override
    public void clear() {
        gfxSet.forEach(drawManager::onRemove);
        gfxSet.clear();
    }

    @Override
    public void show() {
        gfxSet.forEach(drawManager::onAdd);
    }

    @Override
    public void highlight(GFX gfx) {
        drawManager.onRemove(gfx);
        gfxSet.forEach(GFX::dehighlight);
        gfx.highlight();
        drawManager.onAdd(gfx);
    }
}
