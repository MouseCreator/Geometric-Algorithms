package mouse.project.algorithm.impl.trapezoid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EdgesSetImpl implements EdgesSet {
    private final List<Edge> edges;
    public EdgesSetImpl() {
        edges = new ArrayList<>();
    }

    @Override
    public void addAll(Collection<Edge> edges) {
        this.edges.addAll(edges);
    }

    @Override
    public Collection<Edge> getAll() {
        return new ArrayList<>(edges);
    }

    @Override
    public void add(Edge edge) {
        this.edges.add(edge);
    }
}
