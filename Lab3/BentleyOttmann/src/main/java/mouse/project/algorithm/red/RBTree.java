package mouse.project.algorithm.red;


public interface RBTree<T> {
    void insert(T value);
    boolean contains(T value);
    boolean delete(T value);
    void print();
}
