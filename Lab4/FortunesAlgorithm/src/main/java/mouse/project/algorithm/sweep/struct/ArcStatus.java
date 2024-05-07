package mouse.project.algorithm.sweep.struct;


import mouse.project.math.FPosition;

public interface ArcStatus {
    void insert(Arc arc);
    void delete(Arc arc);
    boolean isEmpty();
    ArcNode getAbove(FPosition position);
}
