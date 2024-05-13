package mouse.project.algorithm.sweep.diagram;

import mouse.project.algorithm.edge.SitePair;
import mouse.project.algorithm.sweep.struct.Site;
import mouse.project.math.FBox;
import mouse.project.math.FPosition;
import mouse.project.state.ConstUtils;

import java.util.*;

public class VDiagramBuilder implements DiagramBuilder {
    private final Map<SitePair, ConnectedEdge> edgeMap;
    private final VoronoiVertex imaginary;
    private final List<VoronoiVertex> vertices;

    public VDiagramBuilder() {
        edgeMap = new HashMap<>();
        vertices = new ArrayList<>();
        imaginary = new VoronoiVertex(
                        FPosition.of(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY),
                        true);
    }

    @Override
    public Diagram getResult() {
        DCELDiagram dcel = new DCELDiagram();
        DiagramData diagramData = new DiagramData(vertices, new ArrayList<>(edgeMap.values()));
        return dcel.buildBoundedDiagram(diagramData, new FBox(0, 0, ConstUtils.WORLD_WIDTH, ConstUtils.WINDOW_HEIGHT));
    }

    @Override
    public VoronoiVertex generateVoronoiVertex(FPosition fPosition) {
        VoronoiVertex generated = new VoronoiVertex(fPosition, false);
        vertices.add(generated);
        return generated;
    }

    public VoronoiEdge edgeOnBisector(Site s1, Site s2) {
        ConnectedEdge connectedEdge = withConnectedEdge(s1, s2);
        return toVoronoiEdge(connectedEdge);
    }

    @Override
    public ConnectedEdge withConnectedEdge(Site s1, Site s2) {
        SitePair sitePair = SitePair.of(s1, s2);
        ConnectedEdge connectedEdge = edgeMap.get(sitePair);
        if (connectedEdge == null) {
            connectedEdge = createConnectedEdge(s1, s2);
            edgeMap.put(SitePair.of(s1, s2), connectedEdge);
        }
        return connectedEdge;
    }

    @Override
    public void setStartVertex(ConnectedEdge connectedEdge, VoronoiVertex vertex) {
        connectedEdge.setStart(vertex);
    }

    @Override
    public void setEndVertex(ConnectedEdge connectedEdge, VoronoiVertex vertex) {
        connectedEdge.setEnd(vertex);
    }

    private ConnectedEdge createConnectedEdge(Site s1, Site s2) {
        ConnectedEdge connectedEdge = ConnectedEdge.fromSites(s1, s2);
        connectedEdge.setStart(imaginary);
        connectedEdge.setEnd(imaginary);
        return connectedEdge;
    }

    private VoronoiEdge toVoronoiEdge(ConnectedEdge connectedEdge) {
        return new VoronoiEdge(connectedEdge.getS1(), connectedEdge.getS2());
    }
}
