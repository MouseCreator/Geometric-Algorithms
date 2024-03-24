package mouse.project.algorithm.impl.trapezoid;

import java.util.Objects;

public class EdgeImpl implements Edge {
    private final Vertex v1;
    private final Vertex v2;

    private final boolean limiting;
    public EdgeImpl(Vertex v1, Vertex v2) {
        this.v1 = v1;
        this.v2 = v2;
        limiting = false;
    }
    public EdgeImpl(Vertex v1, Vertex v2, boolean limiting) {
        this.v1 = v1;
        this.v2 = v2;
        this.limiting = limiting;
    }

    @Override
    public Vertex end1() {
        return v1;
    }

    @Override
    public Vertex end2() {
        return v2;
    }

    @Override
    public boolean isLimitingEdge() {
        return limiting;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        EdgeImpl edge = (EdgeImpl) object;
        return Objects.equals(v1, edge.v1) && Objects.equals(v2, edge.v2)
                || Objects.equals(v2, edge.v1) && Objects.equals(v1, edge.v2);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + (v1.hashCode() ^ v2.hashCode());
        return hash;
    }
}
