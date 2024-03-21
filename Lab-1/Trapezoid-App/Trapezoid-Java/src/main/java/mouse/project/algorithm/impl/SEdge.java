package mouse.project.algorithm.impl;

import mouse.project.algorithm.common.CommonEdge;
import mouse.project.algorithm.common.CommonNode;

public class SEdge implements CommonEdge {
    private CommonNode node1;
    private CommonNode node2;
    private boolean isExtra;
    @Override
    public CommonNode from() {
        return node1;
    }

    @Override
    public CommonNode to() {
        return node2;
    }
    @Override
    public boolean isExtra() {
        return isExtra;
    }
}
