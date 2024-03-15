package mouse.project.ui.components.graph;

import mouse.project.state.State;
import mouse.project.ui.components.draw.DrawManager;
import mouse.project.ui.components.draw.DrawManagerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UIGraph {
    private final DrawManager drawManager;
    private final Nodes nodes;
    private final List<Edge> edges;
    public UIGraph() {
        drawManager = State.get().getProgramState().getDrawManager();
        this.nodes = new Nodes(drawManager);
        this.edges = new ArrayList<>();
    }

    public void addNodeAt(Position position) {
        nodes.addNode(position);
    }
    public Optional<Node> getNodeAt(Position position) {
        return nodes.getNodeByPosition(position);
    }

}
