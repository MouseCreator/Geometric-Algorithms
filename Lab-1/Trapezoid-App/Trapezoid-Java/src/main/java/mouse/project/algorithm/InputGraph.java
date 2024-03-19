package mouse.project.algorithm;

import mouse.project.algorithm.common.CommonEdge;
import mouse.project.algorithm.common.CommonNode;

import java.util.List;

public interface InputGraph {
    List<CommonNode> getNodes();
    List<CommonEdge> getEdges();
}
