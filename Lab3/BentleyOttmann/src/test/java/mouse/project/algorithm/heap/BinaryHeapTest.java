package mouse.project.algorithm.heap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @Test
    void minimum() {
    }

    @Test
    void extractMin() {
    }

    @Test
    void size() {
    }

    @Test
    void isEmpty() {
    }
}