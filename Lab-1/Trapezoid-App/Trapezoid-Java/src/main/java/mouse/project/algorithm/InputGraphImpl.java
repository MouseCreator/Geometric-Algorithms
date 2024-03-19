package mouse.project.algorithm;

import mouse.project.algorithm.common.CommonEdge;
import mouse.project.algorithm.common.CommonNode;

import java.util.ArrayList;
import java.util.List;

public class InputGraphImpl implements InputGraph{

    private final List<CommonNode> nodes;
    private final List<CommonEdge> edges;

    public InputGraphImpl() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void addNode(CommonNode node) {
        nodes.add(node);
    }
    public void addEdge(CommonEdge edge) {
        edges.add(edge);
    }

    @Override
    public List<CommonNode> getNodes() {
        return new ArrayList<>(nodes);
    }

    @Override
    public List<CommonEdge> getEdges() {
        return new ArrayList<>(edges);
    }
}
