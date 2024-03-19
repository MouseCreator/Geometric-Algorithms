package mouse.project.graphics;

import mouse.project.algorithm.common.CommonGraph;
import mouse.project.algorithm.common.Trapezoid;

public interface GraphicsChangeListener {
    void updateUI(CommonGraph commonGraph);
    void viewSolution(Trapezoid trapezoid);
}
