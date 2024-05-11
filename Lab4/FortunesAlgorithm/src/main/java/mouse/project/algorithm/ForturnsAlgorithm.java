package mouse.project.algorithm;

import mouse.project.algorithm.mapper.Mapper;
import mouse.project.algorithm.sweep.SweepLine;
import mouse.project.algorithm.sweep.diagram.Diagram;
import mouse.project.algorithm.sweep.diagram.Face;
import mouse.project.graphics.GraphicsChangeListener;
import mouse.project.graphics.PolygonGFX;
import mouse.project.math.FPosition;
import mouse.project.math.Position;
import mouse.project.ui.components.point.PointSet;

import java.awt.Color;
import java.util.Collection;
import java.util.List;

public class ForturnsAlgorithm implements Algorithm {

    private final GraphicsChangeListener graphicsChangeListener;
    private final Mapper mapper;
    private final ColorGenerator colorGenerator;

    public ForturnsAlgorithm(GraphicsChangeListener graphicsChangeListener) {
        this.graphicsChangeListener = graphicsChangeListener;
        mapper = new Mapper();
        colorGenerator = new ColorGenerator();
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
        Diagram diagram = sweepLine.diagram();
        visualize(diagram);
    }

    private void visualize(Diagram diagram) {
        Collection<Face> faces = diagram.getFaces();
        for (Face face : faces) {
            List<Position> positions = face.getNodes().stream().map(FPosition::ints).toList();
            PolygonGFX polygonGFX = new PolygonGFX(positions, generateRandomColor());
            graphicsChangeListener.add(polygonGFX);
        }
        graphicsChangeListener.show();
    }

    private Color generateRandomColor() {
        return colorGenerator.generateBright();
    }
}
