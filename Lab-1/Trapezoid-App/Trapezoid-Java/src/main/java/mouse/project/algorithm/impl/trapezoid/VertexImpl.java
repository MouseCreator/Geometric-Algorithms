package mouse.project.algorithm.impl.trapezoid;

import mouse.project.utils.math.Position;

import java.util.Objects;

public record VertexImpl(Position position, String key) implements Vertex {

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (VertexImpl) obj;
        return Objects.equals(this.position, that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    @Override
    public String toString() {
        return key;
    }

}
