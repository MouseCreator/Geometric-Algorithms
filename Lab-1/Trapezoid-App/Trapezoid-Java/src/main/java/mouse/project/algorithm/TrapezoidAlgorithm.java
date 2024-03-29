package mouse.project.algorithm;

import mouse.project.algorithm.common.CommonGraph;
import mouse.project.graphics.GraphicsChangeListener;
import mouse.project.utils.math.Position;

public interface TrapezoidAlgorithm {
    void build(CommonGraph uiGraph, GraphicsChangeListener graphicsChangeListener);
    void find(Position target, GraphicsChangeListener graphicsChangeListener);
    void clear();
}
