package mouse.project.ui.components.graph;

import mouse.project.ui.components.draw.DrawManager;

import java.util.HashSet;
import java.util.Set;

public class Edges {
    private final Set<Edge> edges;
    private final DrawManager drawManager;
    public Edges(DrawManager drawManager) {
        this.drawManager = drawManager;
        this.edges = new HashSet<>();
    }

    public void add(Node node1, Node node2, boolean extra) {
        Edge edge = new Edge(node1, node2, extra);
        edges.add(edge);
        drawManager.onAdd(edge);
    }
}
