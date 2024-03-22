package mouse.project.algorithm.impl.trapezoid;

import mouse.project.algorithm.impl.tree.Tree;
import mouse.project.algorithm.impl.tree.TreeHorizontalElementImpl;
import mouse.project.algorithm.impl.tree.TreeSequence;

import java.util.Optional;

import static mouse.project.func.StaticFunctions.repeat;
public class TrapezoidBuilder {
    public Tree trapezoid(EdgesSet edges, VerticesSet vertices, SInterval interval) {
        if (vertices.isEmpty()) {
            return null;
        }
        int outputTrapezoids = 2;
        EdgesSet[] e = new EdgesSet[outputTrapezoids];
        VerticesSet[] v = new VerticesSet[outputTrapezoids];
        STrapezoid[] r = new STrapezoid[outputTrapezoids];
        TreeSequence[] u = new TreeSequence[outputTrapezoids];

        int yMed = vertices.medianPosition().y();
        SInterval[] intervals = interval.split(yMed);
        for (Edge edge : edges.getAll()) {
            repeat(2, i -> {
                Optional<Vertex> pOpt = getEndIn(edge, r[i]);
                pOpt.ifPresent(p -> v[i].add(p));
                if (covers(edge, r[i])) {
                    Tree tree = trapezoid(e[i], v[i], intervals[i]);
                    u[i].add(tree);
                }
                u[i].add(edge);
            });
        }
        Tree w = createTreeElement(yMed);
        w.setLeft(balance(u[0]));
        w.setRight(balance(u[1]));
        return w;
    }

    private Tree balance(TreeSequence treeElement) {
        return treeElement.balance();
    }

    private Tree createTreeElement(int yMed) {
        return new TreeHorizontalElementImpl(yMed);
    }

    private boolean covers(Edge edge, STrapezoid sTrapezoid) {
        int p1 = edge.end1().position().y();
        int p2 = edge.end2().position().y();
        int top = Math.max(p1, p2);
        int bottom = Math.min(p1, p2);
        return top > sTrapezoid.top() && bottom < sTrapezoid.bottom();
    }

    private Optional<Vertex> getEndIn(Edge edge, STrapezoid r1) {
        Vertex v1 = edge.end1();
        Vertex v2 = edge.end2();
        int y1 = v1.position().y();
        if (y1 >= r1.top() && y1 <= r1.bottom()) {
            return Optional.of(v1);
        }
        int y2 = v2.position().y();
        if (y2 >= r1.top() && y2 <= r1.bottom()) {
            return Optional.of(v2);
        }
        return Optional.empty();
    }
}
