package mouse.project.algorithm.tree;

import mouse.project.algorithm.common.CPoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface YTree {
    void buildBalancedFrom(Collection<CPoint> point);

    List<CPoint> getAll();
    List<CPoint> getRange(int yMin, int yMax);

}
