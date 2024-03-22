package mouse.project.algorithm.impl.tree;

public interface Tree {
    default Tree of() {
        return null;
    }
    int weight();
    void setLeft(Tree element);
    void setRight(Tree element);
    Tree getLeft();
    Tree getRight();
    boolean isEdge();
    boolean isHorizontal();
}
