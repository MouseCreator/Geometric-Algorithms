package mouse.project.algorithm.impl.call;

import mouse.project.algorithm.common.CommonEdge;
import mouse.project.algorithm.common.CommonGraph;
import mouse.project.algorithm.common.CommonNode;
import mouse.project.algorithm.impl.sweep.SweepLineScanner;
import mouse.project.algorithm.impl.sweep.SweepLineScannerImpl;
import mouse.project.algorithm.impl.sweep.VertexEdgeMap;
import mouse.project.algorithm.impl.sweep.VertexEdgeMapImpl;
import mouse.project.algorithm.impl.trapezoid.*;
import mouse.project.algorithm.impl.tree.Tree;
import mouse.project.utils.math.Box;
import mouse.project.utils.math.Position;

import java.util.*;

public class TrapezoidCall {
    private final TrapezoidBuilder builder;
    private final SweepLineScanner scanner;

    public TrapezoidCall() {
        builder = new TrapezoidBuilder();
        scanner = new SweepLineScannerImpl();
    }

    public Tree prepareAndCall(CommonGraph graph) {
        Collection<CommonEdge> edges = graph.edges();
        Collection<CommonNode> nodes = graph.nodes();
        Box bounds = getBounds(nodes);
        Map<CommonNode, Vertex> vertexMap = mapToVertex(nodes);

        List<Edge> orderedEdges = mapEdges(edges, vertexMap);
        VerticesSet verticesSet = new VerticesSetImpl();
        vertexMap.values().stream().sorted(Comparator.comparingInt(v -> v.position().y())).forEach(verticesSet::add);
        EdgesSet edgesSet = createEdgesSet(orderedEdges, verticesSet);
        SInterval interval = createInterval(bounds);
        return builder.trapezoid(edgesSet, verticesSet, interval, verticesSet.size());
    }

    private EdgesSet createEdgesSet(List<Edge> orderedEdges, VerticesSet verticesSet) {
        VertexEdgeMap ve = new VertexEdgeMapImpl();
        orderedEdges.forEach(ve::add);
        EdgesSet edgesSet = scanner.scanAndCreate(verticesSet, ve);
        edgesSet.add(getLimitingEdge());
        return edgesSet;
    }

    private Edge getLimitingEdge() {
        Vertex limitVertex = new VertexImpl(Position.of(25000, 25000), "LIMIT");
        return new EdgeImpl(limitVertex, limitVertex, true);
    }

    private SInterval createInterval(Box bounds) {
        return new SIntervalImpl(bounds.top(), bounds.bottom());
    }

    private Map<CommonNode, Vertex> mapToVertex(Collection<CommonNode> nodes) {
        Map<CommonNode, Vertex> map = new HashMap<>();
        for (CommonNode node : nodes) {
            Vertex vertex = new VertexImpl(node.position(), node.key());
            map.put(node, vertex);
        }
        return map;
    }

    private List<Edge> mapEdges(Collection<CommonEdge> edges, Map<CommonNode, Vertex> vertexMap) {
        List<Edge> result = new ArrayList<>();
        for (CommonEdge commonEdge : edges) {
            Vertex v1 = vertexMap.get(commonEdge.from());
            Vertex v2 = vertexMap.get(commonEdge.to());
            Edge edge = new EdgeImpl(v1, v2);
            result.add(edge);
        }
        return result;
    }

    private Box getBounds(Collection<CommonNode> nodes) {
        int yMin = Integer.MAX_VALUE;
        int yMax = 0;
        int xMin = Integer.MAX_VALUE;
        int xMax = 0;
        for (CommonNode n : nodes) {
            Position position = n.position();
            int x = position.x();
            int y = position.y();
            if (x < xMin) {
                xMin = x;
            }
            if (x > xMax) {
                xMax = x;
            }
            if (y < yMin) {
                yMin = y;
            }
            if (y > yMax) {
                yMax = y;
            }
        }
        return new Box(Position.of(xMin,yMin), Position.of(xMax,yMax));
    }
}
