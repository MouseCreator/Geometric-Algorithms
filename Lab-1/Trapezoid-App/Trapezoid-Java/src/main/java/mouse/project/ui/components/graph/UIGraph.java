package mouse.project.ui.components.graph;

import mouse.project.ui.components.draw.DrawManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UIGraph {
    private DrawManager drawManager;
    private final Nodes nodes;
    private final List<Edge> edges;
    public UIGraph() {
        this.nodes = new Nodes();
        this.edges = new ArrayList<>();
    }

    public void addNodeAt(Position position) {
        nodes.addNode(position);
    }
    public Optional<Node> getNodeAt(Position position) {
        return nodes.getNodeByPosition(position);
    }
}
