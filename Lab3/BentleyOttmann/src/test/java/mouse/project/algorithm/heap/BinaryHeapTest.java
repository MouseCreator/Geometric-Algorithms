package mouse.project.algorithm.heap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BinaryHeapTest {
    private BinaryHeap<Integer> binaryHeap;
    @BeforeEach
    void setUp() {
        binaryHeap = new BinaryHeap<>(Integer::compareTo);
    }

    @Test
    void insert() {
        binaryHeap.insert(3);
        binaryHeap.insert(1);
        binaryHeap.insert(7);

        assertEquals(1, binaryHeap.minimum());
    }
    private void insertRandomOrder(Heap<Integer> heap, int fromInclusive, int toInclusive) {
        List<Integer> list = new ArrayList<>();
        for (int i = fromInclusive; i <= toInclusive; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        list.forEach(heap::insert);
    }
    @Test
    void minimum() {
        for (int i = 0; i < 5; i++) {
            insertRandomOrder(binaryHeap, 1, 10);
            assertEquals(1, binaryHeap.minimum());
            binaryHeap.clear();
        }
    }

    @Test
    void extractMin() {
        int from = 1;
        int to = 10;
        insertRandomOrder(binaryHeap, from, to);
        for (int i = from; i <= to; i++) {
            assertEquals(i, binaryHeap.extractMin());
        }
    }

    @Test
    void size() {
        int from = 1;
        int to = 10;
        insertRandomOrder(binaryHeap, from, to);
        assertEquals(10, binaryHeap.size());
    }

    @Test
    void isEmpty() {
        assertTrue(binaryHeap.isEmpty());
        binaryHeap.insert(12);
        assertFalse(binaryHeap.isEmpty());
        binaryHeap.extractMin();
        assertTrue(binaryHeap.isEmpty());
    }
}