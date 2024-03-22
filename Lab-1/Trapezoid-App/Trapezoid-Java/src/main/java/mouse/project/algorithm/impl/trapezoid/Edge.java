package mouse.project.algorithm.impl.trapezoid;

public interface Edge {
    Vertex end1();
    Vertex end2();
    boolean isLimitingEdge();
}
