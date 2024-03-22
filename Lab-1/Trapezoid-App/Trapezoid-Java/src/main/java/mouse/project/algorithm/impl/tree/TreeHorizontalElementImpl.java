package mouse.project.algorithm.impl.tree;

public class TreeHorizontalElementImpl implements TreeHorizontalElement {
    private final int lineY;
    private Tree left = null;
    private Tree right = null;
    public TreeHorizontalElementImpl(int lineY) {
        this.lineY = lineY;
    }

    @Override
    public int weight() {
        return 0;
    }

    @Override
    public void setLeft(Tree element) {
        this.left = element;
    }

    @Override
    public void setRight(Tree element) {
        this.right = element;
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
        return false;
    }

    @Override
    public boolean isHorizontal() {
        return true;
    }

    @Override
    public int getLineY() {
        return lineY;
    }
}
