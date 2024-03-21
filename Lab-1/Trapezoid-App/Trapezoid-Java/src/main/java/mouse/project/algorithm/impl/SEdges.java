package mouse.project.algorithm.impl;

import mouse.project.algorithm.impl.edges.EdgeHelper;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class SEdges {

    private final TreeSet<SEdge> edgesSet;

    private final EdgeHelper helper;

    public SEdges() {
        Comparator<SEdge> leftToRightComparator = Comparator.comparingInt(e -> e.middle().x());
        edgesSet = new TreeSet<>(leftToRightComparator);
        helper = new EdgeHelper();
    }

    public void addEdge(SEdge edge) {
        edgesSet.add(edge);
    }
    public List<SEdge> edgesBetween(int topY, int bottomY) {
        return helper.getEdgesBetween(getInSortedOrder(), topY, bottomY);
    }

    public List<SEdge> getInSortedOrder() {
        return edgesSet.stream().toList();
    }
}
