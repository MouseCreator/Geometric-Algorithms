package mouse.project.algorithm.impl.search;

import mouse.project.algorithm.impl.tree.Tree;

import java.util.ArrayList;
import java.util.List;

public class TraceImpl implements Trace {
    private final List<Movement> movementList = new ArrayList<>();
    @Override
    public void append(Movement movement) {
        movementList.add(movement);
    }

    @Override
    public void append(Tree tree, Dir dir) {
        append(new Movement(tree, dir));
    }

    @Override
    public List<Movement> getMovements() {
        return new ArrayList<>(movementList);
    }
}
