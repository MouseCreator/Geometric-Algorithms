package mouse.project.utils.math;

import mouse.project.ui.components.graph.Edge;

public class MathUtils {
    public static Position movePositionToEdge(Edge edge, Position c) {
        Position a = edge.node1().getPosition();
        Position b = edge.node2().getPosition();
        Vector2 result = getVectorProjection(a, b, c);
        return result.moveOn(a);
    }

    public static Vector2 getVectorProjection(Position a, Position b, Position c) {
        Vector2 ab = Vector2.from(a, b);
        Vector2 ac = Vector2.from(a, c);
        return getVectorProjection(ab, ac);
    }

    public static Vector2 getVectorProjection(Vector2 ab, Vector2 ac) {
        double cos = ac.cos(ab);
        double magnitude = ac.magnitude();
        return ab.unit().multiply(magnitude * cos);
    }
}
