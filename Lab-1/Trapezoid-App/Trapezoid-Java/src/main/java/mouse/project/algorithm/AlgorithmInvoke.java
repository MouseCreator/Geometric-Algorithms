package mouse.project.algorithm;

import mouse.project.algorithm.common.CommonGraph;
import mouse.project.algorithm.impl.call.TrapezoidAlgorithmFacade;
import mouse.project.algorithm.impl.gfx.GFX;
import mouse.project.graphics.GraphicsChangeListener;
import mouse.project.mapper.GraphMapper;
import mouse.project.mapper.GraphMapperImpl;
import mouse.project.ui.components.graph.UIGraph;
import mouse.project.utils.math.Position;

import java.util.Collection;
import java.util.List;

public class AlgorithmInvoke implements Algorithm {

    private final GraphMapper graphMapper;
    private final TrapezoidAlgorithmFacade algorithmFacade;
    private final GraphicsChangeListener listener;

    public AlgorithmInvoke() {
        this.graphMapper = new GraphMapperImpl();
        this.algorithmFacade = new TrapezoidAlgorithmFacade();
        listener = new GraphicsChangeListener() {
            @Override
            public void add(GFX gfx) {
            }

            @Override
            public void clear() {
            }

            @Override
            public Collection<GFX> getAll() {
                return List.of();
            }

            @Override
            public void highlight(GFX gfx) {

            }
        };
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
