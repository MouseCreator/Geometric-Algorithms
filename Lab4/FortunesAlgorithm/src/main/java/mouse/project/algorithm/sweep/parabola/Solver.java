package mouse.project.algorithm.sweep.parabola;

import mouse.project.math.Numbers;

public class Solver {
    public static double[] solveQuadratic(double a, double b, double c) {
        double disc = b * b - 4 * a * c;
        if (disc < 0) {
            return new double[0];
        }
        if (Numbers.dEquals(disc, 0)) {
            return new double[]{-b / (2 * a)};
        }
        double sqDisc = Math.sqrt(disc);
        double s1 = (-b - sqDisc) / (2 * a);
        double s2 = (-b + sqDisc) / (2 * a);
        if (s1 < s2)
            return new double[]{s1, s2};
        else
            return new double[]{s2, s1};
    }

}
