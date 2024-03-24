package mouse.project.algorithm.impl.trapezoid;

import java.util.Collection;

public interface EdgesSet {
    void addAll(Collection<Edge> edges);
    Collection<Edge> getAll();
    void add(Edge edge);
}
