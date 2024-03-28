package mouse.project.algorithm.impl.tree;

import mouse.project.algorithm.impl.trapezoid.Edge;

public interface TreeSequence {
    void addTree(Tree element);
    void addEdge(Edge element);
    Tree balance();
}
