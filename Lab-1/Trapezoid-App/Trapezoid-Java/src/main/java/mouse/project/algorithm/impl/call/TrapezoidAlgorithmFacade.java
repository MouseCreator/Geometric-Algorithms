package mouse.project.algorithm.impl.call;

import mouse.project.algorithm.common.CommonGraph;
import mouse.project.algorithm.impl.tree.Tree;

public class TrapezoidAlgorithmFacade implements TrapezoidAlgorithm {
    private Tree tree = null;
    private final TrapezoidCall trapezoidCall;

    public TrapezoidAlgorithmFacade() {
        trapezoidCall = new TrapezoidCall();
    }

    public void build(CommonGraph commonGraph) {
        tree = trapezoidCall.prepareAndCall(commonGraph);
        Descriptor descriptor = new Descriptor();
        descriptor.describe(tree);
    }

    public void find() {

    }
}
