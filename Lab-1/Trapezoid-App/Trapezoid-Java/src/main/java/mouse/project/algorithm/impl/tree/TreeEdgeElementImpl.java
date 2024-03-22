package mouse.project.algorithm.impl.tree;

import mouse.project.algorithm.impl.trapezoid.Edge;

public class TreeEdgeElementImpl implements TreeEdgeElement {
    private final Edge edge;
    public TreeEdgeElementImpl(Edge edge) {
        this.edge = edge;
    }

    @Override
    public int weight() {
        return 0;
    }

    @Override
    public void setLeft(Tree element) {

    }

    @Override
    public void setRight(Tree element) {

    }

    @Override
    public boolean isEdge() {
        return true;
    }

    @Override
    public boolean isHorizontal() {
        return false;
    }

    @Override
    public Edge getEdge() {
        return edge;
    }
}
