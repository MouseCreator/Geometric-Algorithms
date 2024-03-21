package mouse.project.algorithm.impl;

import mouse.project.algorithm.common.CommonNode;
import mouse.project.utils.math.Position;

public class SNode implements CommonNode {
    private Position position;
    private boolean isExtra;
    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public boolean isExtra() {
        return isExtra;
    }
}
