package mouse.project.algorithm.impl.call;

import mouse.project.algorithm.TrapezoidAlgorithm;
import mouse.project.algorithm.common.CommonGraph;
import mouse.project.algorithm.impl.desc.Descriptor;
import mouse.project.algorithm.impl.desc.GraphicsDescriptor;
import mouse.project.algorithm.impl.desc.TrapezoidHighlights;
import mouse.project.algorithm.impl.gfx.GFX;
import mouse.project.algorithm.impl.search.TreeSearch;
import mouse.project.algorithm.impl.search.TreeSearchImpl;
import mouse.project.algorithm.impl.tree.Tree;
import mouse.project.graphics.GraphicsChangeListener;
import mouse.project.utils.math.Position;

public class TrapezoidAlgorithmFacade implements TrapezoidAlgorithm {
    private Tree tree = null;
    private final TrapezoidCall trapezoidCall;
    private TrapezoidHighlights trapezoidHighlights;

    public TrapezoidAlgorithmFacade() {
        trapezoidCall = new TrapezoidCall();
    }

    @Override
    public void build(CommonGraph commonGraph, GraphicsChangeListener graphicsChangeListener) {
        tree = trapezoidCall.prepareAndCall(commonGraph);
        Descriptor<TrapezoidHighlights> descriptor = new GraphicsDescriptor(graphicsChangeListener);
        descriptor.inspect(commonGraph);
        trapezoidHighlights = descriptor.describe(tree);
    }

    @Override
    public void find(Position target, GraphicsChangeListener graphicsChangeListener) {
        TreeSearch search = new TreeSearchImpl();
        Tree found = search.find(tree, target);
        GFX gfx = trapezoidHighlights.get(tree);
        graphicsChangeListener.highlight(gfx);
    }
}
