package mouse.project.algorithm.impl.search;

import mouse.project.algorithm.impl.gfx.GraphicSet;
import mouse.project.algorithm.impl.tree.Tree;
import mouse.project.utils.math.Position;

public class TraceEventGenerator {

    public GraphicSet generateTreeGraphics(Tree tree, Position position) {
        TreeSearch treeSearch = new TreeSearchImpl();
        Trace trace = treeSearch.find(tree, position);
        for (Movement movement : trace.getMovements()) {
            Tree from = movement.from();
            Dir dir = movement.dir();
            highlight(from, dir);
            if (from.isLeaf()) {
                highlightOpposite(trace, from);
            }
        }
        /*
        TODO: complete this method
         */
        return null;
    }

    private void highlightOpposite(Trace trace, Tree from) {
        Movement oppositeHorizontal = findOppositeHorizontal(trace, from);
        Movement oppositeEdge = findOppositeEdge(trace, from);
        if (oppositeHorizontal != null) {
            highlight(oppositeHorizontal.from(), oppositeHorizontal.dir());
        }
        if (oppositeEdge != null) {
            highlight(oppositeEdge.from(), oppositeEdge.dir());
        }
    }

    private Movement findOppositeEdge(Trace trace, Tree from) {
        return null;
    }

    private Movement findOppositeHorizontal(Trace trace, Tree from) {
        return null;
    }

    private void highlight(Tree from, Dir dir) {
    }
}
