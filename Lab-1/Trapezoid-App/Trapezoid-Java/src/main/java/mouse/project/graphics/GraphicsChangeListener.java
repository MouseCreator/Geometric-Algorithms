package mouse.project.graphics;

import mouse.project.algorithm.common.CommonGraph;

public interface GraphicsChangeListener {
    void updateUI(CommonGraph commonGraph);
    void viewSolution(Trapezoid trapezoid);
}
