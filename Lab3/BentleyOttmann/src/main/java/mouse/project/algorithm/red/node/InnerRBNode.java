package mouse.project.algorithm.red.node;

import lombok.Setter;

public class InnerRBNode<T> implements RBNode<T> {
    private T key;
    @Setter
    private Color color;
    private RBNode<T> left;
    private RBNode<T> right;
    private RBNode<T> parent;

    public InnerRBNode(T key, Color color, RBNode<T> left, RBNode<T> right) {
        this.key = key;
        this.color = color;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return color + ": " + key;
    }

    @Override
    public T key() {
        return key;
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
        return false;
    }

    @Override
    public void setRight(RBNode<T> right) {
        this.right = right;
    }

    @Override
    public void setLeft(RBNode<T> left) {
        this.left = left;
    }

    @Override
    public void setParent(RBNode<T> parent) {
        this.parent = parent;
    }

    @Override
    public void setKey(T key) {
        this.key = key;
    }
}
