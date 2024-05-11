package mouse.project.math;

public record Vector2(double x, double y) {
    public static Vector2 zeros() {
        return new Vector2(0, 0);
    }
    public static Vector2 of(double x, double y) {
        return new Vector2(x, y);
    }
    public static Vector2 ones() {
        return new Vector2(1, 1);
    }

    public static Vector2 from(Position origin, Position destination) {
        return Vector2.of(destination.x()-origin.x(), destination.y()-origin.y());
    }
    public static Vector2 from(FPosition origin, FPosition destination) {
        return Vector2.of(destination.x()-origin.x(), destination.y()-origin.y());
    }

    public double dot(Vector2 other) {
        return x * other.x + y * other.y;
    }
    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }
    public double cos(Vector2 other) {
        return dot(other) / (magnitude() * other.magnitude());
    }
    public Vector2 multiply(double c) {
        return Vector2.of(x * c, y * c);
    }

    public Position moveOn(Position a) {
        return Position.of( a.x() + (int) Math.round(x), a.y() + (int) Math.round(y));
    }

    public Vector2 subtract(Vector2 o) {
        return Vector2.of(x - o.x, y - o.y);
    }
    public Vector2 add(Vector2 o) {
        return Vector2.of(x + o.x, y + o.y);
    }

    public Vector2 unit() {
        double m = magnitude();
        return Vector2.of(x / m, y / m);
    }

    public Vector2 orthogonal() {
        if (x == 0 && y == 0) {
            return Vector2.zeros();
        }
        return Vector2.of(y, -x).unit();
    }

    public double angle(Vector2 other) {
        double cos = cos(other);
        double angleOrigin = Math.acos(cos);
        if (Numbers.dEquals(y, 0)) {
            if (Numbers.dEquals(other.y, 0)) {
                if (Math.signum(x) == Math.signum(other.x)) {
                    return 0;
                }
                else {
                    return Math.PI;
                }
            }
            if (other.y < 0) {
                return angleOrigin + Math.PI;
            } else {
                return angleOrigin;
            }
        }
        Matrix2 transform = new Matrix2(1, (1-x)/y, 1, -x/y);
        Vector2 otherAfterTransform = transform.multiplyRight(other);

        if (otherAfterTransform.y < 0) {
            return angleOrigin + Math.PI;
        } else {
            return angleOrigin;
        }
    }
}

