package mouse.project.algorithm.impl.tree;

import mouse.project.algorithm.impl.trapezoid.Edge;

public interface TreeSequence {
    void add(Tree element);
    void add(Edge element);
    Tree balance();
}
