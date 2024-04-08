package mouse.project.algorithm;

import mouse.project.graphics.GraphicsChangeListener;
import mouse.project.ui.components.point.PointSet;
import mouse.project.ui.components.point.WrapBox;

public interface RegionAlg {
    void build(PointSet pointSet, GraphicsChangeListener graphicsChangeListener);
    void find(WrapBox target, GraphicsChangeListener graphicsChangeListener);
    void clear();
}
