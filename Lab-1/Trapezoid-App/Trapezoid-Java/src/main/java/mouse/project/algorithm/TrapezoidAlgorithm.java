package mouse.project.algorithm;

import mouse.project.graphics.GraphicsChangeListener;
import mouse.project.utils.math.Position;

public interface TrapezoidAlgorithm {
    void build(InputGraph uiGraph, GraphicsChangeListener graphicsChangeListener);
    void find(Position target, GraphicsChangeListener graphicsChangeListener);
}
