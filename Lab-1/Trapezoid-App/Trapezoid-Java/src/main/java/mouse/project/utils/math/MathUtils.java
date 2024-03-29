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

    public static GenLine toGenLine(Line line) {
        Vector2 vector = line.unit().unit();
        double a = -vector.y();
        double b = vector.x();
        double c = a * line.position().x() + b * line.position().y();
        return new GenLine(a,b,c);
    }
    public static Position getIntersection(Line line1, Line line2) {
        GenLine g1 = toGenLine(line1);
        GenLine g2 = toGenLine(line2);
        double d = - g1.a() * g2.b() + g2.a() * g1.b();
        assert d != 0;

        double xd = (g1.b() * g2.c() - g2.b() * g1.c()) / d;
        double yd = (g2.a() * g1.c() - g1.a() * g2.c()) / d;
        return Position.of((int) xd, (int) yd);
    }
}
