package mouse.project.algorithm.impl;

import mouse.project.algorithm.common.CommonEdge;
import mouse.project.algorithm.common.CommonGraph;
import mouse.project.algorithm.common.CommonNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SGraph implements CommonGraph {

    private SEdges edges;
    private SNodes nodes;
    @Override
    public Collection<CommonEdge> edges() {
        List<SEdge> edgeList = this.edges.getInSortedOrder();
        return new ArrayList<>(edgeList);
    }

    @Override
    public Collection<CommonNode> nodes() {
        List<SNode> nodeList = this.nodes.getInSortedOrder();
        return new ArrayList<>(nodeList);
    }
}
