package mouse.project.algorithm.impl.tree;

import mouse.project.algorithm.impl.trapezoid.Edge;

import java.util.ArrayList;
import java.util.List;

public class TreeSequenceImpl implements TreeSequence {

    private final List<Edge> edgeList;
    private final List<Tree> treeList;
    private boolean expectsTree;

    public TreeSequenceImpl() {
        edgeList = new ArrayList<>();
        treeList = new ArrayList<>();
        expectsTree = true;
    }

    public TreeSequenceImpl(List<Edge> edgesLeft, List<Tree> treesLeft) {
        this.treeList = treesLeft;
        this.edgeList = edgesLeft;
    }

    @Override
    public void add(Tree element) {
        if (!expectsTree) {
            throw new IllegalStateException("Expected edge, but got tree: " + element);
        }
        treeList.add(element);
        expectsTree = false;
    }

    @Override
    public void add(Edge element) {
        if (expectsTree) {
            throw new IllegalStateException("Expected tree, but got edge: " + element);
        }
        edgeList.add(element);
        expectsTree = true;
    }

    public Tree balance() {
        int weight = weight();
        if (weight==0) {
            return toEdgeTree();
        }
        int mid = getMidIndex(weight);
        return split(mid);
    }

    private Tree split(int index) {
        List<Edge> edgesLeft = edgeList.subList(0, index-1);
        List<Edge> edgesRight = edgeList.subList(index, edgeList.size());

        List<Tree> treesLeft = treeList.subList(0, index-1);
        List<Tree> treesRight = treeList.subList(index, edgeList.size());

        Edge parent = edgeList.get(index-1);
        Edge child = edgeList.get(index);
        Tree midTree = treeList.get(index);
        TreeSequenceImpl u1 = new TreeSequenceImpl(edgesLeft, treesLeft);
        Tree balanceLeft = u1.balance();
        TreeSequenceImpl u2 = new TreeSequenceImpl(edgesRight, treesRight);
        Tree balanceRight = u2.balance();

        TreeEdgeElement parentElement = new TreeEdgeElementImpl(parent);
        TreeEdgeElement childElement = new TreeEdgeElementImpl(child);
        parentElement.setLeft(balanceLeft);
        parentElement.setRight(childElement);
        childElement.setLeft(midTree);
        childElement.setRight(balanceRight);
        return parentElement;
    }

    private int getMidIndex(int weight) {
        int halfWeight = weight >>> 1;
        int currentWeight = 0;
        for (int i = 0; i < treeList.size(); i++) {
            if (currentWeight >= halfWeight) {
                return i;
            }
            currentWeight += treeList.get(i).weight();
        }
        return treeList.size();
    }

    private Tree toEdgeTree() {
        if (edgeList.isEmpty()) {
            throw new IllegalStateException("Empty sequence cannot be turned into tree");
        }
        return buildBalancedTree(edgeList);
    }

    private Tree buildBalancedTree(List<Edge> sortedList) {
        if (sortedList.isEmpty()) {
            return null;
        }

        int mid = sortedList.size() >>> 1;
        Edge midElement = sortedList.get(mid);
        Tree root = new TreeEdgeElementImpl(midElement);

        List<Edge> leftSublist = sortedList.subList(0, mid);
        Tree leftSubtree = buildBalancedTree(leftSublist);
        root.setLeft(leftSubtree);

        List<Edge> rightSublist = sortedList.subList(mid + 1, sortedList.size());
        Tree rightSubtree = buildBalancedTree(rightSublist);
        root.setRight(rightSubtree);

        return root;
    }

    private int weight() {
        return treeList.stream().mapToInt(Tree::weight).sum();
    }
}
