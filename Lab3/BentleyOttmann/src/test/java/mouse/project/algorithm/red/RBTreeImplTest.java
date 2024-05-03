package mouse.project.algorithm.red;

import mouse.project.algorithm.red.node.RBNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

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
            List<Integer> s = new ArrayList<>(list);
            Collections.shuffle(s);
            insertAll(rbTree, s);
            List<Integer> collect = new ArrayList<> (rbTree.collect());
            assertEquals(list, collect, "Invalid result for: " + s);
            rbTree.clear();
        }
    }

    @Test
    void insertDebug() {
        List<Integer> list = range(1, 100);
        List<Integer> minExample = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            List<Integer> s = new ArrayList<>(list);
            Collections.shuffle(s);
            List<Integer> integers = insertAndFind(s);
            if (integers.size() < minExample.size() || minExample.isEmpty()) {
                minExample = integers;
            }
            rbTree.clear();
        }
        System.out.println(minExample);

    }

    @Test
    void insertExample() {
        List<Integer> integers = List.of(11, 3, 2, 9, 4, 1, 6, 5, 10, 12, 8, 7);
        for (int i : integers) {
            rbTree.insert(i);
            System.out.println(rbTree.collect());
        }
    }

    private List<Integer> normalize(List<Integer> integers) {
        List<Integer> temp = new ArrayList<>(integers);
        temp.sort(Integer::compareTo);
        List<Integer> result = new ArrayList<>();
        HashMap<Integer, Integer> mapped = new HashMap<>();
        for (int i = 0; i < temp.size(); i++) {
            Integer val = temp.get(i);
            mapped.put(val, i+1);
        }
        for (int i : integers) {
            result.add(mapped.get(i));
        }
        return result;

    }

    private  List<Integer> insertAndFind(List<Integer> s) {
        List<Integer> example = new ArrayList<>();
        int count = 0;
        for (Integer t : s) {
            rbTree.insert(t);
            example.add(t);
            count++;
            if (count != rbTree.collect().size()) {
                break;
            }
        }
        return example;
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

    @Test
    void testSuccessorPredecessor() {
        int least = 1;
        int max = 10;
        List<Integer> list = range(least, max);
        insertAll(rbTree, list);
        list.forEach(i -> {
            Optional<RBNode<Integer>> optNode = rbTree.find(i);
            assertTrue(optNode.isPresent(), "Value not found: " + i);
            RBNode<Integer> node = optNode.get();
            assertEquals(i, node.key());
            Optional<RBNode<Integer>> predecessor = rbTree.predecessor(node);
            if (i == least) {
                assertTrue(predecessor.isEmpty(), "Unexpected predecessor for " + i + ":" + predecessor);
            } else {
                assertTrue(predecessor.isPresent(), "Expected " + i + " to have predecessor");
                assertEquals(i-1, predecessor.get().key(), "Unexpected predecessor");
            }

            Optional<RBNode<Integer>> successor = rbTree.successor(node);
            if (i == max) {
                assertTrue(successor.isEmpty(), "Unexpected successor for " + i + ": " + successor);
            } else {
                assertTrue(successor.isPresent(), "Expected " + i + " to have successor");
                assertEquals(i+1, successor.get().key(), "Unexpected successor");
            }
        });

    }

    @Test
    void testCertainOrder() {
        rbTree.insert(3);
        rbTree.insert(1);
        rbTree.insert(2);
        range(1, 3).forEach(s -> assertTrue(rbTree.contains(s)));
    }
}