package mouse.project.algorithm.sweep;

import mouse.project.algorithm.red.RBTree;
import mouse.project.algorithm.red.RBTreeImpl;
import mouse.project.algorithm.red.node.RBNode;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

public class SegmentStatus implements Status<TSegment> {

    private final RBTree<TSegment> segmentRBTree;

    public SegmentStatus(Comparator<TSegment> c) {
        segmentRBTree = new RBTreeImpl<>(c);
    }

    @Override
    public Neighbors<TSegment> insert(TSegment segment) {
        RBNode<TSegment> inserted = segmentRBTree.insert(segment);
        Neighbors<TSegment> neighbors = new NeighborsImpl<>();

        Optional<RBNode<TSegment>> left = segmentRBTree.predecessor(inserted);
        left.ifPresent(i -> neighbors.setLeft(i.key()));

        Optional<RBNode<TSegment>> right = segmentRBTree.successor(inserted);
        right.ifPresent(i -> neighbors.setRight(i.key()));
        return neighbors;
    }

    @Override
    public Neighbors<TSegment> delete(TSegment segment) {
        Optional<RBNode<TSegment>> rbNode = segmentRBTree.find(segment);
        if (rbNode.isEmpty()) {
            throw new NoSuchElementException("Cannot delete segment: " + segment);
        }
        Neighbors<TSegment> neighbors = new NeighborsImpl<>();
        Optional<RBNode<TSegment>> left = segmentRBTree.predecessor(rbNode.get());
        left.ifPresent(i -> neighbors.setLeft(i.key()));

        Optional<RBNode<TSegment>> right = segmentRBTree.successor(rbNode.get());
        right.ifPresent(i -> neighbors.setRight(i.key()));

        segmentRBTree.delete(segment);
        return neighbors;
    }



    @Override
    public String toString() {
        return "Tree={" + segmentRBTree + '}';
    }
}
