package mouse.project.graphics;

public interface GraphicsChangeListener {
    void add(GFX gfx);
    void clear();
    void show();
    void highlight(GFX gfx);
    void dehighlight();
}
