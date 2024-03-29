package mouse.project.algorithm.impl.sweep;

import mouse.project.algorithm.impl.trapezoid.Edge;
import mouse.project.algorithm.impl.trapezoid.EdgeImpl;
import mouse.project.algorithm.impl.trapezoid.Vertex;
import mouse.project.algorithm.impl.trapezoid.VertexImpl;
import mouse.project.utils.math.*;

public class EdgeHelper {
    public static int getX(Edge e, int y) {
        Line line;
        if (e.end1().position()== e.end2().position()) {
            return e.end1().position().x();
        }
        line = Line.of(e.end1().position(), e.end2().position());
        Line oY = Line.from(Position.of(0, y), Vector2.of(1, 0));
        return MathUtils.getIntersection(line, oY).x();
    }

    public static Edge createLimiting() {
        Vertex limitVertex = new VertexImpl(Position.of(25000, -25000), "LIMIT");
        Vertex limitVertex2 = new VertexImpl(Position.of(25000, 25000), "LIMIT-2");
        return new EdgeImpl(limitVertex, limitVertex2, true);
    }
}
