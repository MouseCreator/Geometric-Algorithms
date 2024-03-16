package mouse.project.ui.components.graph;

import mouse.project.state.ConstUtils;
import mouse.project.state.State;
import mouse.project.ui.components.draw.DrawManager;

import java.util.Optional;
import java.util.function.Predicate;

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
        return nodes.removeNode(position);
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
}
