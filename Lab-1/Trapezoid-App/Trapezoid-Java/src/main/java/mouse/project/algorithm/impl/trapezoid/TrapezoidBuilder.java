package mouse.project.algorithm.impl.trapezoid;

import mouse.project.algorithm.impl.tree.*;

import java.util.Optional;
import java.util.function.Supplier;
public class TrapezoidBuilder {
    private static final int outputTrapezoids = 2;
    public Tree trapezoid(EdgesSet edges, VerticesSet vertices, SInterval interval, int weight) {
        if (vertices.isEmpty()) {
            return new TreeLeafElementImpl();
        }

        int yMed = vertices.medianPosition().y();

        EdgesSet[] e = createEdgeSets();
        VerticesSet[] v = createVerticesSet();
        STrapezoid[] r = createTrapezoids(interval, yMed);
        TreeSequence[] u = createTreeSequences();
        SInterval[] intervals = interval.split(yMed);

        for (Edge edge : edges.getAll()) {
            for(int i = 0; i < 2; i++){
                Optional<Vertex> pOpt = getEndIn(edge, r[i]);
                if (pOpt.isPresent()) {
                    v[i].add(pOpt.get());
                    e[i].add(edge);
                }
                if (covers(edge, r[i]) || edge.isLimitingEdge()) {
                    Tree tree = trapezoid(e[i], v[i], intervals[i], weight>>>1);
                    u[i].add(tree);
                    if (!edge.isLimitingEdge()) {
                        u[i].add(edge);
                    }
                    e[i] = new EdgesSetImpl();
                    v[i] = new VerticesSetImpl();
                }
            }
        }
        Tree w = createTreeElement(yMed, weight);
        w.setLeft(balance(u[0]));
        w.setRight(balance(u[1]));
        return w;
    }

    private TreeSequence[] createTreeSequences() {
        TreeSequence[] treeSequences = new TreeSequence[outputTrapezoids];
        initArr(treeSequences, TreeSequenceImpl::new);
        return treeSequences;
    }

    private STrapezoid[] createTrapezoids(SInterval interval, int yMed) {
        STrapezoid[] trapezoids = new STrapezoid[2];
        trapezoids[0] = new STrapezoidImpl(interval.bottom(), yMed);
        trapezoids[1] = new STrapezoidImpl(yMed, interval.top());
        return trapezoids;
    }

    private VerticesSet[] createVerticesSet() {
        VerticesSet[] verticesSets = new VerticesSet[outputTrapezoids];
        initArr(verticesSets, VerticesSetImpl::new);
        return verticesSets;
    }

    private EdgesSet[] createEdgeSets() {
        EdgesSet[] edgesSets = new EdgesSet[outputTrapezoids];
        initArr(edgesSets, EdgesSetImpl::new);
        return edgesSets;
    }

    private <T> void initArr(T[] arr, Supplier<T> supplier) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = supplier.get();
        }
    }

    private Tree balance(TreeSequence sequence) {
        return sequence.balance();
    }

    private Tree createTreeElement(int yMed, int weight) {
        return new TreeHorizontalElementImpl(yMed, weight);
    }

    private boolean covers(Edge edge, STrapezoid sTrapezoid) {
        int p1 = edge.end1().position().y();
        int p2 = edge.end2().position().y();
        int top = Math.max(p1, p2);
        int bottom = Math.min(p1, p2);
        return top >= sTrapezoid.top() && bottom <= sTrapezoid.bottom();
    }

    private Optional<Vertex> getEndIn(Edge edge, STrapezoid tr) {
        Vertex v1 = edge.end1();
        Vertex v2 = edge.end2();
        int y1 = v1.position().y();
        if (y1 < tr.top() && y1 > tr.bottom()) {
            return Optional.of(v1);
        }
        int y2 = v2.position().y();
        if (y2 < tr.top() && y2 > tr.bottom()) {
            return Optional.of(v2);
        }
        return Optional.empty();
    }
}
