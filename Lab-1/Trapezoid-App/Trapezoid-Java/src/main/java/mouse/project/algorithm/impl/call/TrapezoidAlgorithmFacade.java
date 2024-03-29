package mouse.project.algorithm.impl.call;

import mouse.project.algorithm.TrapezoidAlgorithm;
import mouse.project.algorithm.common.CommonGraph;
import mouse.project.algorithm.common.CommonNode;
import mouse.project.algorithm.impl.desc.Descriptor;
import mouse.project.algorithm.impl.desc.GraphicsDescriptor;
import mouse.project.algorithm.impl.desc.TrapezoidHighlights;
import mouse.project.algorithm.impl.gfx.GFX;
import mouse.project.algorithm.impl.search.TreeSearch;
import mouse.project.algorithm.impl.search.TreeSearchImpl;
import mouse.project.algorithm.impl.tree.Tree;
import mouse.project.graphics.GraphicsChangeListener;
import mouse.project.utils.math.Box;
import mouse.project.utils.math.Position;

import java.util.Collection;

public class TrapezoidAlgorithmFacade implements TrapezoidAlgorithm {
    private Tree tree = null;
    private final TrapezoidCall trapezoidCall;
    private TrapezoidHighlights trapezoidHighlights;
    private Box graphBox;
    public TrapezoidAlgorithmFacade() {
        trapezoidCall = new TrapezoidCall();
    }
    @Override
    public void build(CommonGraph commonGraph, GraphicsChangeListener graphicsChangeListener) {
        getGraphBox(commonGraph);
        graphicsChangeListener.clear();
        tree = trapezoidCall.prepareAndCall(commonGraph);
        Descriptor<TrapezoidHighlights> descriptor = new GraphicsDescriptor(graphicsChangeListener);
        descriptor.inspect(commonGraph);
        trapezoidHighlights = descriptor.describe(tree);
        graphicsChangeListener.show();
    }

    private void getGraphBox(CommonGraph commonGraph) {
        try {
            Collection<CommonNode> nodes = commonGraph.nodes();
            int top = nodes.stream().mapToInt(v -> v.position().y()).max().orElseThrow();
            int bottom = nodes.stream().mapToInt(v -> v.position().y()).min().orElseThrow();
            int left = nodes.stream().mapToInt(v -> v.position().x()).min().orElseThrow();
            int right = nodes.stream().mapToInt(v -> v.position().x()).max().orElseThrow();
            graphBox = new Box(Position.of(left, top), Position.of(right, bottom));
        } catch (Exception ignored) {
        }
    }

    @Override
    public void find(Position target, GraphicsChangeListener graphicsChangeListener) {
        if (!graphBox.contains(target)) {
            graphicsChangeListener.dehighlight();
            return;
        }
        TreeSearch search = new TreeSearchImpl();
        Tree found = search.find(tree, target);

        GFX gfx = trapezoidHighlights.get(found);
        if (gfx == null) { return; }
        graphicsChangeListener.highlight(gfx);
    }

    @Override
    public void clear() {
        tree = null;
        trapezoidHighlights = null;
        graphBox = null;
    }
}
