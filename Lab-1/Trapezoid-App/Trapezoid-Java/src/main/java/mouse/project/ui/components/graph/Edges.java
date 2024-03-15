package mouse.project.ui.components.graph;

import mouse.project.ui.components.draw.DrawManager;

import java.util.ArrayList;
import java.util.List;

public class Edges {
    private final List<Edge> edges;
    private final DrawManager drawManager;
    public Edges(DrawManager drawManager) {
        this.drawManager = drawManager;
        this.edges = new ArrayList<>();
    }

    public void add(Node node1, Node node2, boolean extra) {
        Edge edge = new Edge(node1, node2, extra);
        edges.add(edge);
        drawManager.onAdd(edge);
    }
}
