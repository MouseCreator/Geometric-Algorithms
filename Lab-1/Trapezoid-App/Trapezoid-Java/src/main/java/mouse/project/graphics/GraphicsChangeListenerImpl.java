package mouse.project.graphics;

import mouse.project.algorithm.InputGraph;
import mouse.project.algorithm.common.CommonGraph;
import mouse.project.algorithm.common.Trapezoid;
import mouse.project.event.service.Events;
import mouse.project.event.type.GraphicsUpdateEvent;
import mouse.project.event.type.TrapezoidEvent;
import mouse.project.mapper.CommonMapper;
import mouse.project.mapper.CommonMapperImpl;

public class GraphicsChangeListenerImpl implements GraphicsChangeListener {

    private final CommonMapper commonMapper;

    public GraphicsChangeListenerImpl() {
        commonMapper = new CommonMapperImpl();
    }

    @Override
    public void updateUI(CommonGraph commonGraph) {
        InputGraph inputGraph = commonMapper.fromCommonGraph(commonGraph);
        Events.generate(new GraphicsUpdateEvent(inputGraph));
    }

    @Override
    public void viewSolution(Trapezoid trapezoid) {
        Events.generate(new TrapezoidEvent(trapezoid));
    }
}
