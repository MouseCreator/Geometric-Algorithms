package mouse.project.algorithm.edge;

import mouse.project.algorithm.sweep.struct.Site;

import java.util.Map;

public class DoublyConnectedEdgeList {
    private Map<Edge, EdgeInfo> edgeMap;
    private Map<SitePair, DanglingEdge> danglingEdgeMap;
    public void addEdge(int vertex1, int vertex2) {

    }

    public void addDanglingEdge(DanglingEdge danglingEdge) {
        Site gen1 = danglingEdge.getGenerator1();
        Site gen2 = danglingEdge.getGenerator2();
        SitePair sitePair = SitePair.of(gen1, gen2);
        danglingEdgeMap.put(sitePair, danglingEdge);
    }
}
