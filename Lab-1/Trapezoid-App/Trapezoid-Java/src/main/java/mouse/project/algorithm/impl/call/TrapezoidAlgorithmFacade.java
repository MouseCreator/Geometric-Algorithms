package mouse.project.algorithm.impl.call;

import mouse.project.algorithm.TrapezoidAlgorithm;
import mouse.project.algorithm.common.CommonGraph;
import mouse.project.algorithm.impl.desc.Descriptor;
import mouse.project.algorithm.impl.desc.TextDescriptor;
import mouse.project.algorithm.impl.tree.Tree;
import mouse.project.graphics.GraphicsChangeListener;
import mouse.project.utils.math.Position;

public class TrapezoidAlgorithmFacade implements TrapezoidAlgorithm {
    private Tree tree = null;
    private final TrapezoidCall trapezoidCall;

    public TrapezoidAlgorithmFacade() {
        trapezoidCall = new TrapezoidCall();
    }

    @Override
    public void build(CommonGraph commonGraph, GraphicsChangeListener graphicsChangeListener) {
        tree = trapezoidCall.prepareAndCall(commonGraph);
        Descriptor descriptor = new TextDescriptor();
        descriptor.inspect(commonGraph);
        descriptor.describe(tree);
    }

    @Override
    public void find(Position target, GraphicsChangeListener graphicsChangeListener) {

    }
}
