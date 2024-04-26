package mouse.project.algorithm.red;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RBTreeImplTest {

    private RBTree<Integer> rbTree;

    @BeforeEach
    void setUp() {
        rbTree = new RBTreeImpl<>(Integer::compareTo);
    }

    @Test
    void insertOrder() {
        List<Integer> list = range(1, 10);
        insertAll(rbTree, list);
        Collection<Integer> collect = new ArrayList<>(rbTree.collect());
        assertEquals(list, collect);
    }
    @Test
    void insertReversed() {
        List<Integer> list = range(10, 1, -1);
        insertAll(rbTree, list);
        List<Integer> collect = new ArrayList<>(rbTree.collect());
        Collections.reverse(collect);
        assertEquals(list, collect);
    }

    @Test
    void insertShuffled() {
        List<Integer> list = range(1, 100);
        for (int i = 1; i < 10; i++) {
            Collections.shuffle(list);
            insertAll(rbTree, list);
            List<Integer> collect = new ArrayList<> (rbTree.collect());
            assertEquals(list, collect, "Invalid result for: " + list);
            rbTree.clear();
        }
    }

    private void insertAll(RBTree<Integer> rbTree, List<Integer> list) {
        list.forEach(rbTree::insert);
    }
    private List<Integer> range(int startInclusive, int endInclusive) {
        return range(startInclusive, endInclusive, 1);
    }
    private List<Integer> range(int startInclusive, int endInclusive, int step) {
        List<Integer> integers = new ArrayList<>();
        for (int i = startInclusive; i <= endInclusive; i += step) {
            integers.add(i);
        }
        return integers;
    }

    @Test
    void contains() {
        List<Integer> list = range(1, 10);
        insertAll(rbTree, list);
        assertTrue(rbTree.contains(2));
        assertTrue(rbTree.contains(5));
        assertFalse(rbTree.contains(14));
        assertFalse(rbTree.contains(-1));
    }

    @Test
    void delete() {
        List<Integer> list = range(1, 10);
        insertAll(rbTree, list);
        list.removeAll(List.of(3,5));
        rbTree.delete(3);
        rbTree.delete(5);
        Collection<Integer> collect = new ArrayList<>(rbTree.collect());
        assertEquals(list, collect);
    }
}