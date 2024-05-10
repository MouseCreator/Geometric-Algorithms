package mouse.project.algorithm.sweep.neighbors;

public class NeighborsImpl<T> implements Neighbors<T> {

    private T left;
    private T right;

    public NeighborsImpl() {
        left = null;
        right = null;
    }
    public NeighborsImpl(T left, T right) {
        this.left = left;
        this.right = right;
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
