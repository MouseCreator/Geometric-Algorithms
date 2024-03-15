package mouse.project.ui.components.graph;

import mouse.project.state.ConstUtils;
import mouse.project.ui.components.draw.DrawManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Nodes {
    private final List<Node> nodeList;
    private final DrawManager drawManager;
    private final NodeIdGenerator nodeIdGenerator;
    public Nodes(DrawManager drawManager) {
        this.drawManager = drawManager;
        nodeIdGenerator = new NodeIdGeneratorImpl();
        nodeList = new ArrayList<>();
    }

    public void addNode(Position position) {
        String id = nodeIdGenerator.generateAndPut();
        addNode(id, position);
    }

    public void addNode(String id, Position position) {
        Node node = new Node(id, position);
        nodeList.add(node);
        drawManager.onAdd(node);
    }

    public void removeNode(Position position) {
        Optional<Node> nodeByPosition = getNodeByPosition(position);
        nodeByPosition.ifPresent(n -> {
            nodeList.remove(n);
            drawManager.onRemove(n);
        });
    }

    public Optional<Node> getNodeByPosition(Position position) {
        double radius =  ConstUtils.NODE_DIAMETER / 2.0;
        return nodeList.stream()
                .filter(n -> n.getPosition().distanceTo(position) < radius)
                .findFirst();
    }
}
