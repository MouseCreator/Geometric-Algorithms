package mouse.project.algorithm.sweep.struct;

public interface Neighbors<T> {
    T left();
    T right();
    boolean hasLeft();
    boolean hasRight();
    void setLeft(T left);
    void setRight(T right);
}
