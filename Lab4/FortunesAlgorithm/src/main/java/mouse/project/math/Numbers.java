package mouse.project.math;

public class Numbers {
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
        return dEquals(d1, d2, 0.0001);
    }
    public static boolean dEquals(double d1, double d2, double th) {
        return Math.abs(d1 - d2) < th;
    }
}
