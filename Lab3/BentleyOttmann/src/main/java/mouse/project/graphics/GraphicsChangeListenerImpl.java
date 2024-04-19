package mouse.project.graphics;

import mouse.project.ui.components.draw.DrawManager;

import java.util.ArrayList;
import java.util.List;

public class GraphicsChangeListenerImpl implements GraphicsChangeListener {
    private final List<GFX> gfxSet;
    private final DrawManager drawManager;

    public GraphicsChangeListenerImpl(DrawManager drawManager) {
        this.gfxSet = new ArrayList<>();
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
        gfxSet.forEach(drawManager::onRemove);
        gfxSet.forEach(GFX::dehighlight);
        gfx.highlight();
        gfxSet.forEach(drawManager::onAdd);
    }

    @Override
    public void dehighlight() {
        gfxSet.forEach(drawManager::onRemove);
        gfxSet.forEach(GFX::dehighlight);
        gfxSet.forEach(drawManager::onAdd);
    }
}
