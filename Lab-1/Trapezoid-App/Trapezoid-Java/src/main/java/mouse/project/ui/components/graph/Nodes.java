package mouse.project.ui.components.graph;

import mouse.project.state.ConstUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Nodes {
    private final List<Node> nodeList;
    private final NodeIdGenerator nodeIdGenerator;
    public Nodes() {
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
    }

    public Optional<Node> getNodeByPosition(Position position) {
        double radius =  ConstUtils.NODE_DIAMETER / 2.0;
        return nodeList.stream()
                .filter(n -> n.getPosition().distanceTo(position) < radius)
                .findFirst();
    }
}
