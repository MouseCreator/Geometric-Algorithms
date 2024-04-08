package mouse.project.algorithm;

import mouse.project.ui.components.point.PointSet;
import mouse.project.ui.components.point.WrapBox;


public interface Algorithm {
    void build(PointSet set);
    void find(WrapBox box);
    void clear();
}
