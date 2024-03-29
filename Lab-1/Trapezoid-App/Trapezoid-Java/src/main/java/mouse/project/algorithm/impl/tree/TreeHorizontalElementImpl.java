package mouse.project.algorithm.impl.tree;

public class TreeHorizontalElementImpl implements TreeHorizontalElement {
    private final int lineY;
    private Tree left = null;
    private Tree right = null;
    private final int weight;
    public TreeHorizontalElementImpl(int lineY, int weight) {
        this.lineY = lineY;
        this.weight = weight;
    }

    @Override
    public int weight() {
        return weight;
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
    public boolean isLeaf() {
        return false;
    }

    @Override
    public int getLineY() {
        return lineY;
    }

    @Override
    public String toString() {
        return "Horizontal{" +
                "Y=" + lineY +
                ", weight=" + weight +
                '}';
    }
}
