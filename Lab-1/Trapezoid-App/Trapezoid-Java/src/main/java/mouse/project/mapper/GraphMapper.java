package mouse.project.mapper;

import mouse.project.algorithm.InputGraph;
import mouse.project.ui.components.graph.UIGraph;

public interface GraphMapper {
    void toUI(UIGraph uiGraph, InputGraph inputGraph);
    InputGraph toAlgorithm(UIGraph uiGraph);

}
