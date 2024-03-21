package mouse.project.algorithm.impl.trapezoid;

import mouse.project.algorithm.impl.SEdge;
import mouse.project.algorithm.impl.tree.TreeElement;
import mouse.project.algorithm.impl.tree.TreeLeafImpl;

import java.util.Optional;

import static mouse.project.func.StaticFunctions.repeat;
public class TrapezoidBuilder {
    public TreeElement trapezoid(EdgesSet edges, VerticesSet vertices, SInterval interval) {
        if (vertices.isEmpty()) {
            return new TreeLeafImpl();
        }
        int outputTrapezoids = 2;
        EdgesSet[] e = new EdgesSet[outputTrapezoids];
        VerticesSet[] v = new VerticesSet[outputTrapezoids];
        STrapezoid[] r = new STrapezoid[outputTrapezoids];
        TreeElement[] u = new TreeElement[outputTrapezoids];

        int yMed = vertices.medianPosition().y();
        SInterval[] intervals = interval.split(yMed);
        for (SEdge edge : edges.getAll()) {
            repeat(2, i -> {
                Optional<Vertex> pOpt = getEndIn(edge, r[i]);
                pOpt.ifPresent(p -> v[i].add(p));
                if (covers(edge, r[i])) {
                    u[i] = trapezoid(e[i], v[i], intervals[i]);
                }
            });
        }
        TreeElement w = createTreeElement();
        w.setLeft(balance(u[0]));
        w.setRight(balance(u[1]));
        return w;
    }

    private TreeElement balance(TreeElement treeElement) {
        return null;
    }

    private TreeElement createTreeElement() {
        return null;
    }

    private boolean covers(SEdge edge, STrapezoid sTrapezoid) {
        return false;
    }

    private Optional<Vertex> getEndIn(SEdge edge, STrapezoid r1) {
        return Optional.empty();
    }
}
