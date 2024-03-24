package mouse.project.mapper;

import mouse.project.algorithm.common.CommonGraph;
import mouse.project.ui.components.graph.UIGraph;

public interface GraphMapper {
    CommonGraph fromUI (UIGraph uiGraph);
}
