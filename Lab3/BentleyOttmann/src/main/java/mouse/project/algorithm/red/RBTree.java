package mouse.project.algorithm.red;


import java.util.Collection;

public interface RBTree<T> {
    void insert(T value);
    boolean contains(T value);
    boolean delete(T value);
    void print();
    Collection<T> collect();
}
