package mouse.project.algorithm.sweep.struct;

public class NeighborsImpl<T> implements Neighbors<T> {

    private T left;
    private T right;

    public NeighborsImpl() {
        left = null;
        right = null;
    }

    @Override
    public T left() {
        return left;
    }

    @Override
    public T right() {
        return right;
    }

    @Override
    public boolean hasLeft() {
        return left != null;
    }

    @Override
    public boolean hasRight() {
        return right != null;
    }

    @Override
    public void setLeft(T left) {
        this.left = left;
    }

    @Override
    public void setRight(T right) {
        this.right = right;
    }
}
