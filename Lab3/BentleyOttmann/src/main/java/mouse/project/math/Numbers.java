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
}
