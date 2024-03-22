package mouse.project.algorithm.impl.tree;

public class TreeHorizontalElementImpl implements TreeHorizontalElement {
    private final int lineY;
    public TreeHorizontalElementImpl(int lineY) {
        this.lineY = lineY;
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
