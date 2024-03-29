package mouse.project.algorithm.impl.trapezoid;

import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@ToString
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

    @Override
    public Edge last() {
        return edges.get(edges.size()-1);
    }
}
