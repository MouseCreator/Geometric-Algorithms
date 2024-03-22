package mouse.project.algorithm.impl.trapezoid;

import mouse.project.algorithm.impl.tree.Tree;
import mouse.project.algorithm.impl.tree.TreeElementSequence;

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
        TreeElementSequence[] u = new TreeElementSequence[outputTrapezoids];

        int yMed = vertices.medianPosition().y();
        SInterval[] intervals = interval.split(yMed);
        for (Edge edge : edges.getAll()) {
            repeat(2, i -> {
                Optional<Vertex> pOpt = getEndIn(edge, r[i]);
                pOpt.ifPresent(p -> v[i].add(p));
                if (covers(edge, r[i])) {
                    u[i].add(trapezoid(e[i], v[i], intervals[i]));
                }
                u[i].add(edge);
            });
        }
        Tree w = createTreeElement();
        w.setLeft(balance(u[0]));
        w.setRight(balance(u[1]));
        return w;
    }

    private Tree balance(TreeElementSequence treeElement) {
        return null;
    }

    private Tree createTreeElement() {
        return null;
    }

    private boolean covers(Edge edge, STrapezoid sTrapezoid) {
        return false;
    }

    private Optional<Vertex> getEndIn(Edge edge, STrapezoid r1) {
        return Optional.empty();
    }
}
