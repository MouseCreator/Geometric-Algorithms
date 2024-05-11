package mouse.project.algorithm.sweep.diagram;

import mouse.project.algorithm.edge.SitePair;
import mouse.project.algorithm.sweep.struct.Site;
import mouse.project.math.FBox;
import mouse.project.math.FPosition;
import mouse.project.math.Numbers;
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

    @Override
    public VoronoiEdge edgeOnBisector(Site s1, Site s2) {
        SitePair sitePair = SitePair.of(s1, s2);
        ConnectedEdge connectedEdge = edgeMap.get(sitePair);
        if (connectedEdge == null) {
            connectedEdge = createConnectedEdge(s1, s2);
            edgeMap.put(SitePair.of(s1, s2), connectedEdge);
        }
        return toVoronoiEdge(connectedEdge);
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
    private final static Comparator<FPosition> positionComparator = (p1, p2) -> {
        if (Numbers.dEquals(p1.y(), p2.y())) {
            if (Numbers.dEquals(p1.x(), p2.x())) {
                return 0;
            }
            return p1.x() > p2.x() ? 1 : -1;
        }
        return p1.y() > p2.y() ? 1 : -1;
    };
    @Override
    public void bindEdgeOnBisectorToVertex(VoronoiEdge edge, VoronoiVertex vertex) {
        Site s1 = edge.getS1();
        Site s2 = edge.getS2();
        SitePair pair = SitePair.of(s1, s2);
        ConnectedEdge connectedEdge = edgeMap.get(pair);
        if (connectedEdge == null) {
            throw new NoSuchElementException("No edge on bisector between " + s1 + " and " + s2);
        }
        FPosition originPosition = connectedEdge.getOrigin();
        FPosition vertexPosition = vertex.getPosition();

        int compared = positionComparator.compare(originPosition, vertexPosition);

        if (compared == 0) {
            throw new IllegalStateException("Edge goes through a voronoi vertex: " + edge + ", " + vertex);
        }
        if (compared > 0) {
            connectedEdge.setStart(vertex);
        } else {
            connectedEdge.setEnd(vertex);
        }

    }
}
