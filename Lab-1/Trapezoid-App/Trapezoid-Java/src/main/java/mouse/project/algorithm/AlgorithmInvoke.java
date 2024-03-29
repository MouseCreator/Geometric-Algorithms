package mouse.project.algorithm;

import mouse.project.algorithm.common.CommonGraph;
import mouse.project.algorithm.impl.call.TrapezoidAlgorithmFacade;
import mouse.project.graphics.GraphicsChangeListener;
import mouse.project.graphics.GraphicsChangeListenerImpl;
import mouse.project.mapper.GraphMapper;
import mouse.project.mapper.GraphMapperImpl;
import mouse.project.ui.components.draw.DrawManager;
import mouse.project.ui.components.graph.UIGraph;
import mouse.project.utils.math.Position;


public class AlgorithmInvoke implements Algorithm {

    private final GraphMapper graphMapper;
    private final TrapezoidAlgorithmFacade algorithmFacade;
    private final GraphicsChangeListener listener;

    public AlgorithmInvoke(DrawManager drawManager) {
        this.graphMapper = new GraphMapperImpl();
        this.algorithmFacade = new TrapezoidAlgorithmFacade();
        listener = new GraphicsChangeListenerImpl(drawManager);
    }

    @Override
    public void build(UIGraph graph) {
        Thread thread = new Thread(()->{
            CommonGraph commonGraph = graphMapper.fromUI(graph);
            algorithmFacade.build(commonGraph, listener);
        });
        thread.setName("Algorithm Thread");
        thread.start();
    }

    @Override
    public void find(Position position) {
        algorithmFacade.find(position, listener);
    }

    @Override
    public void clear() {
        algorithmFacade.clear();
        listener.clear();
    }
}
