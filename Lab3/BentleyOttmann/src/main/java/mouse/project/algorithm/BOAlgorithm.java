package mouse.project.algorithm;

import mouse.project.algorithm.mapper.Mapper;
import mouse.project.algorithm.sweep.SweepLineFacade;
import mouse.project.algorithm.sweep.SweepLineFacadeImpl;
import mouse.project.algorithm.sweep.TIntersection;
import mouse.project.algorithm.sweep.TSegmentSet;
import mouse.project.graphics.GraphicsChangeListener;
import mouse.project.graphics.IntersectionsGFX;
import mouse.project.ui.components.point.Segments;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class BOAlgorithm implements Algorithm {

    private final GraphicsChangeListener graphicsChangeListener;
    private final SweepLineFacade facade;
    private final Mapper mapper;
    private final Logger logger = LogManager.getLogger(BOAlgorithm.class);
    public BOAlgorithm(GraphicsChangeListener graphicsChangeListener) {
        this.graphicsChangeListener = graphicsChangeListener;
        facade = new SweepLineFacadeImpl();
        mapper = new Mapper();
    }

    @Override
    public void apply(Segments segments) {
        TSegmentSet set = mapper.mapSegments(segments);
        Set<TIntersection> allIntersections = facade.findAllIntersections(set);
        allIntersections.forEach(i -> {
            graphicsChangeListener.add(new IntersectionsGFX(i.intersection()));
        });
        logAll(allIntersections);

    }

    private void logAll(Set<TIntersection> allIntersections) {
        allIntersections.forEach(i -> {
            logger.debug("INTERSECTION: " + i.print());
        });
    }

    @Override
    public void clear() {
        graphicsChangeListener.clear();
    }
}
