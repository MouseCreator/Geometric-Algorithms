package mouse.project.algorithm.heap;

public interface Heap<T> {
    void insert(T value);
    T minimum();
    T extractMin();
    int size();
    boolean isEmpty();
}
