package mouse.project.algorithm;

import mouse.project.algorithm.common.CommonGraph;
import mouse.project.algorithm.impl.call.TrapezoidAlgorithmFacade;
import mouse.project.graphics.GraphicsChangeListener;
import mouse.project.mapper.GraphMapper;
import mouse.project.mapper.GraphMapperImpl;
import mouse.project.ui.components.graph.UIGraph;
import mouse.project.utils.math.Position;

public class AlgorithmInvoke implements Algorithm {

    private final GraphMapper graphMapper;
    private final TrapezoidAlgorithmFacade algorithmFacade;
    private final GraphicsChangeListener listener;

    public AlgorithmInvoke() {
        this.graphMapper = new GraphMapperImpl();
        this.algorithmFacade = new TrapezoidAlgorithmFacade();
        listener = new GraphicsChangeListener() {};
    }

    @Override
    public void build(UIGraph graph) {
        Thread thread = new Thread(()->{
            CommonGraph commonGraph = graphMapper.fromUI(graph);
            algorithmFacade.build(commonGraph, listener);
        });
        thread.start();
    }

    @Override
    public void find(Position position) {
        algorithmFacade.find(position, listener);
    }
}
