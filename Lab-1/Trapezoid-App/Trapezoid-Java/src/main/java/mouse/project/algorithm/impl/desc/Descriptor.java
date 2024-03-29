package mouse.project.algorithm.impl.desc;

import mouse.project.algorithm.common.CommonGraph;
import mouse.project.algorithm.impl.tree.Tree;

public interface Descriptor {
    void describe(Tree tree);
    void inspect(CommonGraph commonGraph);
}
