package mouse.project.algorithm;

import mouse.project.ui.components.graph.UIGraph;
import mouse.project.utils.math.Position;

public interface Algorithm {
    void build(UIGraph graph);
    void find(Position position);
    void clear();
}
