package mouse.project.math;

public class Matrix2 {
    private double a11;
    private double a12;
    private double a21;
    private double a22;

    public Matrix2(double a11, double a12, double a21, double a22) {
        this.a11 = a11;
        this.a12 = a12;
        this.a21 = a21;
        this.a22 = a22;
    }

    public Vector2 multiplyRight(Vector2 vector2) {
        return Vector2.of(
                a11 * vector2.x() + a12 * vector2.y(),
                a21 * vector2.x() + a22 * vector2.y()
        );
    }
}
