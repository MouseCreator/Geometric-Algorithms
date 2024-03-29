package mouse.project.ui.components.graph;

import mouse.project.state.ConstUtils;
import mouse.project.ui.components.draw.DrawManager;
import mouse.project.utils.math.Box;
import mouse.project.utils.math.MathUtils;
import mouse.project.utils.math.Position;
import mouse.project.utils.math.Vector2;

import java.util.*;
import java.util.function.Predicate;

public class Edges {
    private final Set<Edge> edges;
    private final DrawManager drawManager;
    public Edges(DrawManager drawManager) {
        this.drawManager = drawManager;
        this.edges = new HashSet<>();
    }

    public void add(Node node1, Node node2, boolean extra) {
        Edge edge = new Edge(node1, node2, extra);
        add(edge);
    }

    private boolean containsEdge(Node node1, Node node2) {
        return edges.stream().anyMatch(e -> e.hasNode(node1) && e.hasNode(node2));
    }

    public Optional<Edge> getAtPosition(Position c) {
        Predicate<Edge> isClose = edge -> {
            Position a = edge.node1().getPosition();
            Position b = edge.node2().getPosition();
            Vector2 ac = Vector2.from(a, c);
            Vector2 ad = MathUtils.getVectorProjection(a, b, c);
            double magnitude = ac.subtract(ad).magnitude();
            return insideEdgeBox(edge, c) && magnitude < ConstUtils.TARGET_EDGE_TOLERANCE;
        };
        return edges.stream().filter(isClose).findFirst();
    }

    private boolean insideEdgeBox(Edge edge, Position c) {
        Position p1 = edge.node1().getPosition();
        Position p2 = edge.node2().getPosition();
        Box box = new Box(p1, p2);
        return box.contains(c);
    }

    public boolean remove(Position position) {
        Optional<Edge> atPosition = getAtPosition(position);
        atPosition.ifPresent(this::removeEdge);
        return atPosition.isPresent();
    }

    private void removeEdge(Edge e) {
        beforeRemoval(e);
        edges.remove(e);
    }

    private void beforeRemoval(Edge e) {
        drawManager.onRemove(e);
    }

    public void removeWith(Node node) {
        List<Edge> list = edges.stream().filter(e -> e.hasNode(node)).toList();
        list.forEach(this::removeEdge);
    }

    public void removeAll() {
        edges.forEach(this::beforeRemoval);
        edges.clear();
    }

    public List<Edge> getAll() {
        return new ArrayList<>(edges);
    }

    public void add(Edge edge) {
        if(containsEdge(edge.node1(), edge.node2())) {
            return;
        }
        drawManager.onAdd(edge);
        edges.add(edge);
    }
}
