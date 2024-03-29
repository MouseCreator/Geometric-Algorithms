package mouse.project.algorithm.impl.sweep;

import mouse.project.algorithm.impl.trapezoid.Edge;
import mouse.project.algorithm.impl.trapezoid.EdgeImpl;
import mouse.project.algorithm.impl.trapezoid.Vertex;
import mouse.project.algorithm.impl.trapezoid.VertexImpl;
import mouse.project.utils.math.Position;

public class EdgeHelper {
    public static int getX(Edge e, int y) {
        return getX(e.end1().position(), e.end2().position(), y);
    }

    public static int getX(Position p1, Position p2, double y) {
        if (p1.y() == p2.y()) {
            if (y == p1.y()) {
                return p1.x();
            } else {
                throw new IllegalArgumentException("The given y-coordinate is not on the segment.");
            }
        }
        double slope = (double) (p2.y() - p1.y()) / (p2.x() - p1.x());
        return (int) ((y - p1.y()) / slope + p1.x());
    }

    public static Edge createLimiting() {
        Vertex limitVertex = new VertexImpl(Position.of(25000, -25000), "LIMIT");
        Vertex limitVertex2 = new VertexImpl(Position.of(25000, 25000), "LIMIT-2");
        return new EdgeImpl(limitVertex, limitVertex2, true);
    }
}
