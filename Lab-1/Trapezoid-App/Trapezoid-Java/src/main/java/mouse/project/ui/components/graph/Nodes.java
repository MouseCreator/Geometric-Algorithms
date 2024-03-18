package mouse.project.ui.components.graph;

import mouse.project.state.ConstUtils;
import mouse.project.ui.components.draw.DrawManager;
import mouse.project.utils.math.Position;

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

    public void addNode(Position position, boolean extra) {
        String id = nodeIdGenerator.generateAndPut();
        addNode(id, position, extra);
    }

    public void addNode(String id, Position position, boolean extra) {
        Node node = new Node(id, position, extra);
        nodeList.add(node);
        drawManager.onAdd(node);
    }

    public Optional<Node> removeNode(Position position) {
        Optional<Node> nodeByPosition = getNodeByPosition(position);
        nodeByPosition.ifPresent(this::removeNode);
        return nodeByPosition;
    }

    private void removeNode(Node n) {
        beforeRemoval(n);
        nodeList.remove(n);
    }

    private void beforeRemoval(Node n) {
        drawManager.onRemove(n);
        nodeIdGenerator.free(n.getId());
    }

    public Optional<Node> getNodeByPosition(Position position) {
        double radius = ConstUtils.NODE_DIAMETER / 2.0;
        return nodeList.stream()
                .filter(n -> n.getPosition().distanceTo(position) < radius)
                .findFirst();
    }

    public void removeAll() {
        nodeList.forEach(this::beforeRemoval);
        nodeList.clear();
    }
}
