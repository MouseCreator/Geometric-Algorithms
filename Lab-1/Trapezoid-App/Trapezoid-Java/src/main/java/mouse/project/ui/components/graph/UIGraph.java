package mouse.project.ui.components.graph;

import mouse.project.ui.components.draw.DrawManager;

import java.util.ArrayList;
import java.util.List;
public class UIGraph {
    private DrawManager drawManager;
    private NodeIdGeneratorImpl nodeIdGenerator;
    private final Nodes nodes;
    private final List<Edge> edges;
    public UIGraph() {
        this.nodes = new Nodes();
        this.edges = new ArrayList<>();
    }

    public void addNodeAt(Position position) {
        nodes.addNode(position);
    }
}
