package mouse.project.algorithm.sweep.diagram;

import mouse.project.algorithm.sweep.struct.Site;
import mouse.project.math.FPosition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingDiagramBuilder implements DiagramBuilder {

    private DiagramBuilder decorated;

    private static Logger logger = LogManager.getLogger(LoggingDiagramBuilder.class);

    public LoggingDiagramBuilder(DiagramBuilder decorated) {
        this.decorated = decorated;
    }

    @Override
    public Diagram getResult() {
        return decorated.getResult();
    }

    @Override
    public VoronoiVertex generateVoronoiVertex(FPosition fPosition) {
        VoronoiVertex vertex = decorated.generateVoronoiVertex(fPosition);
        logger.debug("Generating vertex: " + vertex);
        return vertex;
    }

    @Override
    public VoronoiEdge edgeOnBisector(Site s1, Site s2) {
        VoronoiEdge edge = decorated.edgeOnBisector(s1, s2);
        logger.debug("Got on bisector:" + edge);
        return edge;
    }

    @Override
    public void bindEdgeOnBisectorToVertex(VoronoiEdge edge, VoronoiVertex startingVertex) {
        logger.debug("Connecting edge" + edge + " and vertex " + startingVertex);
        decorated.bindEdgeOnBisectorToVertex(edge, startingVertex);
    }
}
