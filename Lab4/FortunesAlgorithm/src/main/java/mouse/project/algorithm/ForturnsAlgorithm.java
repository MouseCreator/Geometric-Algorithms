package mouse.project.algorithm;

import mouse.project.algorithm.mapper.Mapper;
import mouse.project.algorithm.sweep.SweepLine;
import mouse.project.graphics.GraphicsChangeListener;
import mouse.project.ui.components.point.PointSet;

public class ForturnsAlgorithm implements Algorithm {

    private final GraphicsChangeListener graphicsChangeListener;
    private final Mapper mapper;

    public ForturnsAlgorithm(GraphicsChangeListener graphicsChangeListener) {
        this.graphicsChangeListener = graphicsChangeListener;
        mapper = new Mapper();
    }

    @Override
    public void clear() {
        graphicsChangeListener.clear();
    }

    @Override
    public void build(PointSet pointSet) {
        SweepLine sweepLine = new SweepLine();
        mouse.project.algorithm.data.PointSet inputSet = mapper.mapPointSet(pointSet);
        sweepLine.scan(inputSet);
    }
}
