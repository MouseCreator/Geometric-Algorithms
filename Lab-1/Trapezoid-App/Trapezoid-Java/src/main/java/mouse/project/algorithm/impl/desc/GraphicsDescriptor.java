package mouse.project.algorithm.impl.desc;

import mouse.project.algorithm.common.CommonGraph;
import mouse.project.algorithm.common.CommonNode;
import mouse.project.algorithm.impl.gfx.*;
import mouse.project.algorithm.impl.trapezoid.Edge;
import mouse.project.algorithm.impl.tree.Tree;
import mouse.project.algorithm.impl.tree.TreeEdgeElement;
import mouse.project.algorithm.impl.tree.TreeHorizontalElement;
import mouse.project.graphics.GraphicsChangeListener;
import mouse.project.utils.math.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

public class GraphicsDescriptor implements Descriptor<TrapezoidHighlights> {
    private BoundingTrapezoid initialBounds = null;
    private final GraphicsChangeListener gfxChange;

    private static final Logger logger = LogManager.getLogger(GraphicsDescriptor.class);

    public GraphicsDescriptor(GraphicsChangeListener gfxChange) {
        this.gfxChange = gfxChange;
    }

    @Override
    public TrapezoidHighlights describe(Tree tree) {
        if (initialBounds == null) {
            return new TrapezoidHighlights();
        }
        GraphicsBuilder graphicsBuilder = new GraphicsBuilder(gfxChange);
        describe(tree, initialBounds, graphicsBuilder);
        return graphicsBuilder.getHighLights();
    }

    @Override
    public void inspect(CommonGraph commonGraph) {
        try {
            Collection<CommonNode> nodes = commonGraph.nodes();
            int top = nodes.stream().mapToInt(v -> v.position().y()).max().orElseThrow();
            int bottom = nodes.stream().mapToInt(v -> v.position().y()).min().orElseThrow();
            int left = nodes.stream().mapToInt(v -> v.position().x()).min().orElseThrow();
            int right = nodes.stream().mapToInt(v -> v.position().x()).max().orElseThrow();
            initialBounds = createFromBounds(left, top, right, bottom);
        } catch (Exception e) {
            logger.debug("Inspecting empty graph.");
        }
    }

    private BoundingTrapezoid createFromBounds(int left, int top, int right, int bottom) {
        Position topLeft = Position.of(left, top);
        Position bottomRight = Position.of(right, bottom);
        Line leftL = new Line(topLeft, Vector2.of(0, 1));
        Line topL = new Line(topLeft, Vector2.of(1, 0));
        Line rightL = new Line(bottomRight, Vector2.of(0, 1));
        Line bottomL = new Line(bottomRight, Vector2.of(1, 0));
        return new BoundingTrapezoid(leftL, topL, rightL, bottomL);
    }



    private record BoundingTrapezoid(Line leftLine, Line topLine, Line rightLine, Line bottomLine) {

        public int getLeft(int lineY) {
            return leftLine.getX(lineY);
        }

        public int getRight(int lineY) {
            return rightLine.getX(lineY);
        }

        public BoundingTrapezoid[] splitHorizontal(Line split) {
            BoundingTrapezoid[] trapezoids = new BoundingTrapezoid[2];
            trapezoids[0] = new BoundingTrapezoid(leftLine, split, rightLine, bottomLine);
            trapezoids[1] = new BoundingTrapezoid(leftLine, topLine, rightLine, split);
            return trapezoids;
        }

        public BoundingTrapezoid[] splitVertical(Line split) {
            BoundingTrapezoid[] trapezoids = new BoundingTrapezoid[2];
            trapezoids[0] = new BoundingTrapezoid(leftLine, topLine, split, bottomLine);
            trapezoids[1] = new BoundingTrapezoid(split, topLine, rightLine, bottomLine);
            return trapezoids;
        }
    }

    private static class GraphicsBuilder {
        GraphicsChangeListener listener;
        TrapezoidHighlights highlights = new TrapezoidHighlights();
        public GraphicsBuilder(GraphicsChangeListener gfxChange) {
            this.listener = gfxChange;
        }
        void addGraphic(Tree tree, GFX gfx) {
            highlights.put(tree, gfx);
            listener.add(gfx);
        }

        public TrapezoidHighlights getHighLights() {
            return highlights;
        }
    }

    private void describe(Tree tree, BoundingTrapezoid bounds, GraphicsBuilder builder) {
        if (tree == null) {
            return;
        }
        BoundingTrapezoid[] children;
        if (tree.isHorizontal()) {
            TreeHorizontalElement horiz = (TreeHorizontalElement) tree;
            int lineY = horiz.getLineY();
            Position goesThrough = Position.of(0, lineY);
            children = bounds.splitHorizontal(new Line(goesThrough, Vector2.of(1.0, 0.0)));
            LineGFX line = createLine(bounds, lineY);
            builder.addGraphic(tree, line);
        }
        else if (tree.isEdge()) {
            TreeEdgeElement edgeElem = (TreeEdgeElement) tree;
            Edge edge = edgeElem.getEdge();
            Line line = toLine(edge);
            children = bounds.splitVertical(line);
            builder.addGraphic(tree, createLine(edgeElem.getEdge()));
        }
        else if (tree.isLeaf()) {
            GFX trapezoid = createTrapezoid(bounds);
            builder.addGraphic(tree, trapezoid);
            return;
        } else {
            return;
        }
        describe(tree.getLeft(), children[0], builder);
        describe(tree.getRight(), children[1], builder);
    }

    private Line toLine(Edge edge) {
        Position p1 = edge.end1().position();
        Position p2 = edge.end2().position();
        return Line.of(p1, p2);
    }



    private LineGFX createLine(Edge edge) {
        Position p1 = edge.end1().position();
        Position p2 = edge.end2().position();
        return new EdgeLineGFX(p1, p2);
    }



    private GFX createTrapezoid(BoundingTrapezoid bounds) {
        Position p1 = MathUtils.getIntersection(bounds.leftLine, bounds.topLine);
        Position p2 = MathUtils.getIntersection(bounds.topLine, bounds.rightLine);
        Position p3 = MathUtils.getIntersection(bounds.rightLine, bounds.bottomLine);
        Position p4 = MathUtils.getIntersection(bounds.bottomLine, bounds.leftLine);
        return new TrapezoidGFXImpl(p1, p2, p3, p4);
    }

    private LineGFX createLine(BoundingTrapezoid bounds, int lineY) {
        int leftX = bounds.getLeft(lineY);
        int rightX = bounds.getRight(lineY);
        Position p1 = Position.of(leftX, lineY);
        Position p2 = Position.of(rightX, lineY);
        return new HorizontalLineGFX(p1, p2);
    }
}
