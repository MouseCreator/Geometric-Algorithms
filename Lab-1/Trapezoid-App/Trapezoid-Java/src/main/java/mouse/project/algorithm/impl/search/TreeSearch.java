package mouse.project.algorithm.impl.search;

import mouse.project.algorithm.impl.tree.Tree;
import mouse.project.utils.math.Position;

public interface TreeSearch {
    Trace find(Tree tree, Position position);
}
