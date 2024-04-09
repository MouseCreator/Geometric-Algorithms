package mouse.project.algorithm.builder;

import mouse.project.algorithm.descriptor.Descriptor;
import mouse.project.algorithm.descriptor.SegmentTreeDescriptor;
import mouse.project.algorithm.tree.SegmentTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SegmentTreeBuilderTest {
    private SegmentTreeBuilder builder;
    @BeforeEach
    void setUp() {
        builder = new SegmentTreeBuilder();
    }

    @Test
    void createSegmentTree() {
        List<Integer> sortedX = List.of(10, 20, 30);
        SegmentTree segmentTree = builder.createSegmentTree(sortedX);
        Descriptor<String> stringDescriptor = new SegmentTreeDescriptor();
        String describe = stringDescriptor.describe(segmentTree);
        System.out.println(describe);
        assertEquals(5, segmentTree.allNodes().size());
    }
}