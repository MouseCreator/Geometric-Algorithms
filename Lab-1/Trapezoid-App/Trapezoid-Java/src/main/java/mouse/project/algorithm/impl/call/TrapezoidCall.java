package mouse.project.algorithm.impl.call;

import mouse.project.algorithm.common.CommonEdge;
import mouse.project.algorithm.common.CommonGraph;
import mouse.project.algorithm.common.CommonNode;
import mouse.project.algorithm.impl.trapezoid.Edge;
import mouse.project.algorithm.impl.trapezoid.EdgeImpl;
import mouse.project.algorithm.impl.trapezoid.TrapezoidBuilder;
import mouse.project.algorithm.impl.tree.Tree;
import mouse.project.utils.math.Box;
import mouse.project.utils.math.Position;
import mouse.project.utils.math.Positions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TrapezoidCall {
    private final TrapezoidBuilder builder;

    public TrapezoidCall() {
        builder = new TrapezoidBuilder();
    }

    public Tree prepareAndCall(CommonGraph graph) {
        Collection<CommonEdge> edges = graph.edges();
        Collection<CommonNode> nodes = graph.nodes();
        Box bounds = getBounds(nodes);
        List<Edge> orderedEdges = mapAndOrder(edges);
        return null;
    }

    private List<Edge> mapAndOrder(Collection<CommonEdge> edges) {
        ArrayList<CommonEdge> commonEdges = new ArrayList<>(edges);
        commonEdges.sort((e1, e2) -> middleX(e1) - middleX(e2));
        for (CommonEdge commonEdge : commonEdges) {
            Edge edge;
        }
        return List.of();
    }

    private int middleX(CommonEdge edge) {
        Position p1 = edge.from().getPosition();
        Position p2 = edge.to().getPosition();
        return Positions.average(p1, p2).x();
    }

    private Box getBounds(Collection<CommonNode> nodes) {
        int yMin = Integer.MAX_VALUE;
        int yMax = 0;
        int xMin = Integer.MAX_VALUE;
        int xMax = 0;
        for (CommonNode n : nodes) {
            Position position = n.getPosition();
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
