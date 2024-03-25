package mouse.project.algorithm.impl.trapezoid;

public record SIntervalImpl(int top, int bottom) implements SInterval {
    @Override
    public SInterval[] split(int med) {
        SInterval[] result = new SInterval[2];
        if (med < bottom) {
            String exceptionStr = String.format("Median is lower than interval bottom: %d < %d", med, bottom);
            throw new IllegalArgumentException(exceptionStr);
        }
        if (med > top) {
            String exceptionStr = String.format("Median is higher than interval top: %d > %d", med, top);
            throw new IllegalArgumentException(exceptionStr);
        }
        result[0] = new SIntervalImpl(med, bottom);
        result[1] = new SIntervalImpl(top, med);
        return result;
    }
}
