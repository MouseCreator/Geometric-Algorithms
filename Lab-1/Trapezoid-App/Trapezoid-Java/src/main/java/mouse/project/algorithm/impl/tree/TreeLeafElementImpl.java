package mouse.project.algorithm.impl.tree;

public class TreeLeafElementImpl implements TreeLeafElement {
    @Override
    public int weight() {
        return 0;
    }

    @Override
    public void setLeft(Tree element) {
        throw new UnsupportedOperationException("Leaf cannot have left child");
    }

    @Override
    public void setRight(Tree element) {
        throw new UnsupportedOperationException("Leaf cannot have right child");
    }

    @Override
    public Tree getLeft() {
        return null;
    }

    @Override
    public Tree getRight() {
        return null;
    }

    @Override
    public boolean isEdge() {
        return false;
    }

    @Override
    public boolean isHorizontal() {
        return false;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public String toString() {
        return "Leaf";
    }
}
