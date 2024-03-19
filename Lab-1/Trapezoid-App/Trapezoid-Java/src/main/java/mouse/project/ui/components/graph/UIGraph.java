package mouse.project.ui.components.graph;

import mouse.project.state.State;
import mouse.project.ui.components.draw.DrawManager;
import mouse.project.utils.math.Position;

import java.util.List;
import java.util.Optional;

public class UIGraph {
    private final Nodes nodes;
    private final Edges edges;
    public UIGraph() {
        DrawManager drawManager = State.get().getProgramState().getDrawManager();
        this.nodes = new Nodes(drawManager);
        this.edges = new Edges(drawManager);
    }

    public void addNodeAt(Position position, boolean extra) {
        nodes.addNode(position, extra);
    }
    public Optional<Node> getNodeAt(Position position) {
        return nodes.getNodeByPosition(position);
    }
    public boolean removeNodeAt(Position position) {
        Optional<Node> node = nodes.removeNode(position);
        node.ifPresent(edges::removeWith);
        return node.isPresent();
    }
    public void addEdge(Position p1, Position p2, boolean extra) {
        Optional<Node> node1 = nodes.getNodeByPosition(p1);
        Optional<Node> node2 = nodes.getNodeByPosition(p2);
        if (node1.isEmpty() || node2.isEmpty()) {
            return;
        }
        edges.add(node1.get(), node2.get(), extra);
    }

    public Optional<Edge> getEdgeAt(Position position) {
        return edges.getAtPosition(position);
    }

    public boolean removeEdgeAt(Position position) {
        return edges.remove(position);
    }

    public void removeAll() {
        nodes.removeAll();
        edges.removeAll();
    }

    public List<Node> getNodes() {
        return nodes.getAll();
    }

    public List<Edge>  getEdges() {
        return edges.getAll();
    }

    public void addNamedNode(Node node) {
        nodes.addNode(node);
    }

    public void addEdge(Edge edge) {
        validateContains(edge.node1());
        validateContains(edge.node2());
        edges.add(edge);
    }

    private void validateContains(Node node) {
        if (!nodes.contains(node)) {
            throw new IllegalArgumentException("Passed edge with external node " + node);
        }
    }
}
