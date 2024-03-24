package mouse.project.algorithm.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CommonGraphImpl implements CommonGraph {
    private final List<CommonEdge> edgeList;
    private final List<CommonNode> nodeList;
    public CommonGraphImpl() {
        edgeList = new ArrayList<>();
        nodeList = new ArrayList<>();
    }

    public void addEdge(CommonEdge e) {
        edgeList.add(e);
    }
    public void addNode(CommonNode n) {
        nodeList.add(n);
    }

    @Override
    public Collection<CommonEdge> edges() {
        return new ArrayList<>(edgeList);
    }

    @Override
    public Collection<CommonNode> nodes() {
        return new ArrayList<>(nodeList);
    }
}
