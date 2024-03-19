package mouse.project.mapper;

import mouse.project.algorithm.InputGraph;

import mouse.project.ui.components.graph.UIGraph;

import javax.naming.OperationNotSupportedException;

public class GraphMapperImpl implements GraphMapper {
    @Override
    public void toUI(UIGraph uiGraph, InputGraph inputGraph) {
       throw new UnsupportedOperationException();
    }

    @Override
    public InputGraph toAlgorithm(UIGraph uiGraph) {
        throw new UnsupportedOperationException();
    }
}
