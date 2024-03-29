package mouse.project.algorithm.impl.tree;

import mouse.project.algorithm.impl.trapezoid.Edge;

public class TreeEdgeElementImpl implements TreeEdgeElement {
    private final Edge edge;
    private Tree right = null;
    private Tree left = null;
    public TreeEdgeElementImpl(Edge edge) {
        this.edge = edge;
    }

    @Override
    public int weight() {
        return 0;
    }

    @Override
    public void setLeft(Tree element) {
        left = element;
    }

    @Override
    public void setRight(Tree element) {
        right = element;
    }

    @Override
    public Tree getLeft() {
        return left;
    }

    @Override
    public Tree getRight() {
        return right;
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
    public boolean isLeaf() {
        return false;
    }

    @Override
    public Edge getEdge() {
        return edge;
    }

    @Override
    public String toString() {
        return "TreeEdge{" +
                "edge=" + edge +
                '}';
    }
}
