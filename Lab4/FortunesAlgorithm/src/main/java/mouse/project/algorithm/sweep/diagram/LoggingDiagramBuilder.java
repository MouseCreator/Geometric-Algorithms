package mouse.project.algorithm.sweep.diagram;

import mouse.project.math.FPosition;

public class LoggingDiagramBuilder implements DiagramBuilder {
    @Override
    public void appendDanglingEdge(VoronoiEdge voronoiEdge) {
        System.out.println("Adding dangling edge: " + voronoiEdge);
    }

    @Override
    public Diagram getResult() {
        return new Diagram() {
            @Override
            public void addEdge(FPosition pi, FPosition pj) {

            }
        };
    }

    @Override
    public void createAndJoin(FPosition center, VoronoiEdge voronoiEdge, VoronoiEdge voronoiEdge1) {
        String message = "Creating vertex at " + center + " and edges " + voronoiEdge + ", " + voronoiEdge1;
        System.out.println(message);
    }
}
