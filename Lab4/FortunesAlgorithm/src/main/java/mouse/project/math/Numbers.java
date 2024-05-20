package mouse.project.math;

public class Numbers {

    private final static double TOLERANCE = 0.0001;
    public static int min(int... numbers) {
        int min = Integer.MAX_VALUE;
        for (int n : numbers) {
            min = Math.min(n, min);
        }
        return min;
    }

    public static int max(int... numbers) {
        int max = Integer.MIN_VALUE;
        for (int n : numbers) {
            max = Math.max(n, max);
        }
        return max;
    }

    public static boolean dEquals(double d1, double d2) {
        return dEquals(d1, d2, TOLERANCE);
    }
    public static boolean dEquals(double d1, double d2, double th) {
        return Math.abs(d1 - d2) < th;
    }

    public static int dCompare(double d1, double d2, double th) {
        if (dEquals(d1, d2, th)) {
            return 0;
        }
        return d1 > d2 ? 1 : -1;
    }
    public static int dCompare(double d1, double d2) {
        return dCompare(d1, d2, TOLERANCE);
    }

    public static boolean dLess(double x, double x1) {
        return x1 - x > TOLERANCE;
    }
    public static boolean dLess(double x, double x1, double th) {
        return x1 - x > th;
    }
    public static boolean dGreater(double x, double x1) {
        return x - x1 > TOLERANCE;
    }

    public static boolean dLessOrEquals(double x1, double x2) {
        return dLessOrEquals(x1, x2, TOLERANCE);
    }
    public static boolean dLessOrEquals(double x1, double x2, double th) {
        return x1 - x2 > -th;
    }
}
