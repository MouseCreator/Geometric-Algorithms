package mouse.project.algorithm.sweep.diagram;

import mouse.project.algorithm.sweep.struct.Site;
import mouse.project.math.FPosition;
import mouse.project.math.FSegment;
import mouse.project.math.GenLine;

public class LoggingDiagramBuilder implements DiagramBuilder {
    @Override
    public Diagram getResult() {
        return new Diagram() {
            @Override
            public void addEdge(FPosition pi, FPosition pj) {

            }
        };
    }

    @Override
    public VoronoiVertex createVertex(FPosition position) {
        System.out.println("Creating vertex: " + position);
        return new VoronoiVertex(position);
    }

    @Override
    public void joinEdge(VoronoiVertex vertex, VoronoiEdge edge) {
        System.out.println("Joining edge " + edge + " to vertex " + vertex);
    }

    @Override
    public VoronoiEdge appendEdgeOnBisector(Site pI, Site pJ) {
        FSegment segment = new FSegment(pI.getPosition(), pJ.getPosition());
        GenLine bisector = segment.bisector();
        VoronoiEdge edge = new VoronoiEdge(bisector);
        System.out.println("Adding edge " + edge);
        return edge;
    }
}
