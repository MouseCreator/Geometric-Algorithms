package mouse.project.algorithm.impl.desc;

import mouse.project.algorithm.common.CommonGraph;
import mouse.project.algorithm.common.CommonNode;
import mouse.project.algorithm.impl.gfx.*;
import mouse.project.algorithm.impl.trapezoid.Edge;
import mouse.project.algorithm.impl.tree.Tree;
import mouse.project.algorithm.impl.tree.TreeEdgeElement;
import mouse.project.algorithm.impl.tree.TreeHorizontalElement;
import mouse.project.graphics.GraphicsChangeListener;
import mouse.project.utils.math.Position;
import mouse.project.utils.math.Vector2;

import java.util.Collection;

public class GraphicsDescriptor implements Descriptor<TrapezoidHighlights> {
    private BoundingTrapezoid initialBounds = null;
    private final GraphicsChangeListener gfxChange;

    public GraphicsDescriptor(GraphicsChangeListener gfxChange) {
        this.gfxChange = gfxChange;
    }

    @Override
    public TrapezoidHighlights describe(Tree tree) {
        if (initialBounds == null) {
            throw new IllegalStateException("Graph is not inspected before description!");
        }
        GraphicsBuilder graphicsBuilder = new GraphicsBuilder(gfxChange);
        describe(tree, initialBounds, graphicsBuilder);
        return graphicsBuilder.getHighLights();
    }

    @Override
    public void inspect(CommonGraph commonGraph) {
        Collection<CommonNode> nodes = commonGraph.nodes();
        int top = nodes.stream().mapToInt(v -> v.position().y()).max().orElseThrow();
        int bottom = nodes.stream().mapToInt(v -> v.position().y()).min().orElseThrow();
        int left = nodes.stream().mapToInt(v -> v.position().x()).max().orElseThrow();
        int right = nodes.stream().mapToInt(v -> v.position().x()).max().orElseThrow();
        initialBounds = createFromBounds(left, top, right, bottom);
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

    private static class Line {
        Vector2 unit;
        Position position;

        public Line(Vector2 unit, Position position) {
            this.unit = unit;
            this.position = position;
        }

        public static Line of(Position p1, Position p2) {
            Vector2 direction = Vector2.from(p1, p2);
            Vector2 unitDirection = direction.unit();
            return new Line(unitDirection, p1);
        }

        public Line(Position goesThrough, Vector2 vector) {
            this.position = goesThrough;
            this.unit = vector.unit();
        }

        public int getX(int y) {
            if (unit.y() == 0) {
                throw new IllegalArgumentException("The line is horizontal. Cannot calculate x for a given y.");
            }
            double slope = unit.y() / unit.x();
            double yIntercept = position.y() - slope * position.x();
            return (int) ((y - yIntercept) / slope);
        }
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
            trapezoids[0] = new BoundingTrapezoid(leftLine, topLine, rightLine, split);
            trapezoids[1] = new BoundingTrapezoid(leftLine, split, rightLine, bottomLine);
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
        describeCurrentElement(tree, bounds, builder);
        BoundingTrapezoid[] children = new BoundingTrapezoid[2];
        if (tree.isHorizontal()) {
            TreeHorizontalElement horiz = (TreeHorizontalElement) tree;
            int lineY = horiz.getLineY();
            Position goesThrough = Position.of(0, lineY);
            children = bounds.splitHorizontal(new Line(goesThrough, Vector2.of(1.0, 0.0)));
        }
        else if (tree.isEdge()) {
            TreeEdgeElement edgeElem = (TreeEdgeElement) tree;
            Edge edge = edgeElem.getEdge();
            Line line = toLine(edge);
            children = bounds.splitVertical(line);
        }
        else if (tree.isLeaf()) {
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

    private void describeCurrentElement(Tree tree, BoundingTrapezoid bounds, GraphicsBuilder builder) {
        if (tree.isHorizontal()) {
            TreeHorizontalElement horiz = (TreeHorizontalElement) tree;
            int lineY = horiz.getLineY();
            builder.addGraphic(tree, createLine(bounds, lineY));
        } else if (tree.isLeaf()) {
            builder.addGraphic(tree, createTrapezoid(bounds));
        } else if (tree.isEdge()) {
            TreeEdgeElement edgeElement = (TreeEdgeElement) tree;
            builder.addGraphic(tree, createLine(edgeElement.getEdge()));
        }
    }

    private LineGFX createLine(Edge edge) {
        Position p1 = edge.end1().position();
        Position p2 = edge.end2().position();
        return new EdgeLineGFX(p1, p2);
    }

    private Position getIntersection(Line line1, Line line2) {
        Vector2 v1 = Vector2.from(line1.position,
                Position.of(line1.position.x() + (int) line1.unit.x(),
                line1.position.y() + (int) line1.unit.y()));
        Vector2 v2 = Vector2.from(line2.position,
                Position.of(line2.position.x() + (int) line2.unit.x(),
                line2.position.y() + (int) line2.unit.y()));

        if (Math.abs(v1.cos(v2)) == 1) {
            throw new IllegalArgumentException("The lines are parallel. No intersection found.");
        }

        double t = v2.subtract(Vector2.from(line1.position, line2.position)).dot(v1.unit().multiply(-1)) / v1.dot(v2.unit().multiply(-1));
        return v1.unit().multiply(t).moveOn(line1.position);
    }
    private GFX createTrapezoid(BoundingTrapezoid bounds) {
        Position p1 = getIntersection(bounds.leftLine, bounds.topLine);
        Position p2 = getIntersection(bounds.topLine, bounds.rightLine);
        Position p3 = getIntersection(bounds.rightLine, bounds.bottomLine);
        Position p4 = getIntersection(bounds.bottomLine, bounds.leftLine);
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
