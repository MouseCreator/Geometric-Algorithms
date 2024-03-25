package mouse.project.mapper;


import mouse.project.algorithm.common.*;
import mouse.project.ui.components.graph.Edge;
import mouse.project.ui.components.graph.Node;
import mouse.project.ui.components.graph.UIGraph;

import java.util.HashMap;
import java.util.Map;


public class GraphMapperImpl implements GraphMapper {
    @Override
    public CommonGraph fromUI(UIGraph uiGraph) {
        CommonGraphImpl commonGraph = new CommonGraphImpl();
        Map<String, CommonNode> nodeMap = new HashMap<>();
        for (Node node : uiGraph.getNodes()) {
            CommonNode commonNode = new CommonNodeImpl(node.getPosition(), node.getId(), node.isExtra());
            nodeMap.put(node.getId(), commonNode);
            commonGraph.addNode(commonNode);
        }
        for (Edge edge : uiGraph.getEdges()) {
            String id1 = edge.node1().getId();
            String id2 = edge.node2().getId();
            CommonNode n1 = nodeMap.get(id1);
            assert n1 != null;
            CommonNode n2 = nodeMap.get(id2);
            assert n2 != null;
            CommonEdge commonEdge = new CommonEdgeImpl(n1, n2, edge.isExtra());
            commonGraph.addEdge(commonEdge);
        }
        return commonGraph;
    }
}
