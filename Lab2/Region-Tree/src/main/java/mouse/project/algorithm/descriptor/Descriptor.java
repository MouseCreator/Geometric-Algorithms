package mouse.project.algorithm.descriptor;

import mouse.project.algorithm.tree.SegmentTree;

public interface Descriptor<T> {
    T describe(SegmentTree tree);
}
