package mouse.project.algorithm.impl.trapezoid;

public interface SInterval {
    int top();
    int bottom();
    SInterval[] split(int med);
}
