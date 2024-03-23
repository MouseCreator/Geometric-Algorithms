package mouse.project.algorithm.impl.search;

import mouse.project.algorithm.impl.tree.Tree;

import java.util.List;

public interface Trace {
    void append(Movement movement);
    void append(Tree tree, Dir dir);
    List<Movement> getMovements();
}
