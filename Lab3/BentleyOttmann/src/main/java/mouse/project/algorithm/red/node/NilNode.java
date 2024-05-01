package mouse.project.algorithm.red.node;


import mouse.project.algorithm.red.RBTreeException;

public class NilNode<T> implements RBNode<T> {
    private Color color;
    private RBNode<T> parent = this;
    private RBNode<T> left = this;
    private RBNode<T> right = this;
    public NilNode() {
        color = Color.BLACK;
    }

    @Override
    public T key() {
        throw new RBTreeException("Nil node has no key");
    }

    @Override
    public Color color() {
        return color;
    }

    @Override
    public RBNode<T> left() {
        return left;
    }

    @Override
    public RBNode<T> right() {
        return right;
    }

    @Override
    public RBNode<T> parent() {
        return parent;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public void setRight(RBNode<T> node) {
        this.right = node;
    }

    @Override
    public void setLeft(RBNode<T> left) {
        this.left = left;
    }

    @Override
    public void setParent(RBNode<T> x) {
        this.parent = x;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void setKey(T key) {
        throw new RBTreeException("Nil cannot have a key");
    }

    @Override
    public String toString() {
        return "nil";
    }

    public void reset() {
        this.left = this;
        this.right = this;
        this.parent = this;
    }
}
