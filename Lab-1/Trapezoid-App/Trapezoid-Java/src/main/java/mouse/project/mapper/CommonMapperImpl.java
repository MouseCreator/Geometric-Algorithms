package mouse.project.mapper;

import mouse.project.algorithm.InputGraph;
import mouse.project.algorithm.InputGraphImpl;
import mouse.project.algorithm.common.CommonGraph;

public class CommonMapperImpl implements CommonMapper {
    @Override
    public InputGraph fromCommonGraph(CommonGraph fromCommon) {
        InputGraphImpl inputGraph = new InputGraphImpl();
        fromCommon.nodes().forEach(inputGraph::addNode);
        fromCommon.edges().forEach(inputGraph::addEdge);
        return inputGraph;
    }
}
