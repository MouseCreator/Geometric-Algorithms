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
    public void addTree(Tree element) {
        if (!expectsTree) {
            throw new IllegalStateException("Expected edge, but got tree: " + element);
        }
        treeList.add(element);
        expectsTree = false;
    }

    @Override
    public void addEdge(Edge element) {
        if (expectsTree) {
            throw new IllegalStateException("Expected tree, but got edge: " + element);
        }
        edgeList.add(element);
        expectsTree = true;
    }

    public Tree balance() {
        int weight = weight();
        System.out.println("EDGE LIST:" + edgeList + "; TREE LIST: " + treeList);
        if (weight==0) {
            System.out.println("EDGE");
            return toEdgeTree();
        }
        if (treeList.size()==2) {
            System.out.println("SIMPLE");
            return simpleTree();
        }
        int mid = getMidIndex(weight);
        System.out.println("SPLIT " + mid);
        return split(mid);
    }

    private Tree simpleTree() {
        Edge edge = edgeList.get(0);
        Tree left = treeList.get(0);
        Tree right = treeList.get(1);
        TreeEdgeElement edgeElement = new TreeEdgeElementImpl(edge);
        edgeElement.setLeft(left);
        edgeElement.setRight(right);
        return edgeElement;
    }

    private Tree split(int index) {
        if (index == 0) {
            System.out.println("LEFT");
            return produceLeftmost();
        }
        if (index == treeList.size()-1) {
            System.out.println("RIGHT");
            return produceRightmost();
        }
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

    private Tree produceRightmost() {
        int last = treeList.size()-1;
        Tree right = treeList.get(last);
        Tree parent = new TreeEdgeElementImpl(edgeList.get(last-1));
        List<Tree> treesLeft = treeList.subList(0, last);
        List<Edge> edgesLeft = edgeList.subList(0, last-1);
        TreeSequenceImpl leftSeq = new TreeSequenceImpl(edgesLeft, treesLeft);
        Tree balanceLeft = leftSeq.balance();
        parent.setLeft(balanceLeft);
        parent.setRight(right);
        return parent;
    }

    private Tree produceLeftmost() {
        Tree left = treeList.get(0);
        Tree parent = new TreeEdgeElementImpl(edgeList.get(0));
        List<Tree> treesRight = treeList.subList(1, treeList.size());
        List<Edge> edgesRight = edgeList.subList(1, edgeList.size());
        TreeSequenceImpl rightSeq = new TreeSequenceImpl(edgesRight, treesRight);
        Tree balanceRight = rightSeq.balance();
        parent.setLeft(left);
        parent.setRight(balanceRight);
        return parent;
    }

    private int getMidIndex(int weight) {
        int halfWeight = weight >>> 1;
        int currentWeight = 0;
        for (int i = 0; i < treeList.size(); i++) {
            currentWeight += treeList.get(i).weight();
            if (currentWeight >= halfWeight) {
                return i;
            }
        }
        return treeList.size() - 1;
    }

    private Tree toEdgeTree() {
        if (edgeList.isEmpty()) {
            if (treeList.size()==1) {
                return treeList.get(0);
            }
            throw new IllegalStateException("Empty sequence cannot be turned into tree");
        }
        Tree edgeTree = buildBalancedTree(edgeList);
        assignLeaves(edgeTree, treeList, new IndexWrapper());
        return edgeTree;
    }
    private static class IndexWrapper {
        private int index;

        public IndexWrapper() {
            index = 0;
        }
        public int increment() {
            int prev = index;
            index = index + 1;
            return prev;
        }
    }
    private void assignLeaves(Tree edgeTree, List<Tree> leaves, IndexWrapper iw) {
        if (edgeTree == null) {
            return;
        }
        Tree left = edgeTree.getLeft();
        if (left == null) {
            edgeTree.setLeft(leaves.get(iw.increment()));
        } else {
            assignLeaves(left, leaves, iw);
        }
        Tree right = edgeTree.getRight();
        if (right == null) {
            edgeTree.setRight(leaves.get(iw.increment()));
        } else {
            assignLeaves(right, leaves, iw);
        }
    }

    private Tree buildBalancedTree(List<Edge> sortedEdges) {
        if (sortedEdges.isEmpty()) {
            return null;
        }

        int mid = sortedEdges.size() >>> 1;

        Edge midElement = sortedEdges.get(mid);
        Tree root = new TreeEdgeElementImpl(midElement);

        List<Edge> leftSublist = sortedEdges.subList(0, mid);
        Tree leftSubtree = buildBalancedTree(leftSublist);
        root.setLeft(leftSubtree);

        List<Edge> rightSublist = sortedEdges.subList(mid + 1, sortedEdges.size());
        Tree rightSubtree = buildBalancedTree(rightSublist);
        root.setRight(rightSubtree);

        return root;
    }

    private int weight() {
        return treeList.stream().mapToInt(Tree::weight).sum();
    }
}
