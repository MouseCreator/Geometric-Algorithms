package mouse.project.algorithm.impl.desc;

import mouse.project.algorithm.impl.gfx.GFX;
import mouse.project.algorithm.impl.tree.Tree;

import java.util.HashMap;
import java.util.Map;

public class TrapezoidHighlights {

    private final Map<Tree, GFX> treeGFXMap;

    public TrapezoidHighlights() {
        this.treeGFXMap = new HashMap<>();
    }

    public void put(Tree tree, GFX gfx) {
        treeGFXMap.put(tree, gfx);
    }

    public GFX get(Tree tree) {
        return treeGFXMap.get(tree);
    }
}
