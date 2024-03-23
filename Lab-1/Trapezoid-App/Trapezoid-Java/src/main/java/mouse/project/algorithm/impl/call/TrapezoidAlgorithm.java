package mouse.project.algorithm.impl.call;

import mouse.project.algorithm.common.CommonGraph;
import mouse.project.algorithm.impl.tree.Tree;

public class TrapezoidAlgorithm {
    private Tree tree = null;
    private final TrapezoidCall trapezoidCall;

    public TrapezoidAlgorithm() {
        trapezoidCall = new TrapezoidCall();
    }

    public void build(CommonGraph commonGraph) {
        tree = trapezoidCall.prepareAndCall(commonGraph);
    }

    public void find() {

    }
}
