package mouse.project.algorithm.red;


import mouse.project.algorithm.red.node.RBNode;

import java.util.Collection;
import java.util.Optional;

public interface RBTree<T> {
    RBNode<T> insert(T value);
    boolean contains(T value);
    Optional<RBNode<T>> find(T value);
    Optional<RBNode<T>> successor(RBNode<T> node);
    Optional<RBNode<T>> predecessor(RBNode<T> node);
    boolean delete(T value);
    void print();
    void clear();
    Collection<T> collect();
    int size();
    boolean isEmpty();
}
