package mouse.project.algorithm.red;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RBTreeImplTest {

    private RBTree<Integer> rbTree;

    @BeforeEach
    void setUp() {
        rbTree = new RBTreeImpl<>(Integer::compareTo);
    }

    @Test
    void insert() {
        for (int i = 1; i <= 10; i++) {
            rbTree.insert(i);
        }
        rbTree.print();
    }

    @Test
    void contains() {
    }

    @Test
    void delete() {
    }
}