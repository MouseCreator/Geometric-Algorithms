package mouse.project.algorithm.red.node;

public interface RBNode<T> {
    T key();
    Color color();
    RBNode<T> left();
    RBNode<T> right();
    RBNode<T> parent();
    boolean isLeaf();
    void setRight(RBNode<T> left);
    void setLeft(RBNode<T> left);
    void setParent(RBNode<T> x);
    void setColor(Color color);
    void setKey(T key);
}

