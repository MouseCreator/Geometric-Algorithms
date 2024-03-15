package mouse.project.ui.components.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NodeIdGeneratorImplTest {
    private NodeIdGeneratorImpl nodeIdGenerator;
    private List<Pair> pairList;
    private static class Pair {
        int key;
        String str;

        private Pair(int key, String str) {
            this.key = key;
            this.str = str;
        }

        static Pair of(int key, String str) {
            return new Pair(key, str);
        }
    }
    @BeforeEach
    void setUp() {
       nodeIdGenerator = new NodeIdGeneratorImpl();
       pairList = List.of(
               Pair.of(0, "_"),
               Pair.of(1, "A"),
               Pair.of(2, "B"),
               Pair.of(3, "C"),
               Pair.of(26, "Z"),
               Pair.of(27, "A_"),
               Pair.of(28, "AA"),
               Pair.of(29, "AB"),
               Pair.of(53, "AZ"),
               Pair.of(54, "B_"),
               Pair.of(55, "BA")
           );
}

    @Test
    void testKeyValue() {
        for (Pair pair : pairList) {
            toKeyValue(pair.key, pair.str);
        }
    }
    void toKeyValue(int key, String v) {
        assertEquals(key, nodeIdGenerator.keyValue(v));
    }
    @Test
    void testFromKey() {
        for (Pair pair : pairList) {
            fromKeyValue(pair.key, pair.str);
        }
    }
    void fromKeyValue(int key, String v) {
        assertEquals(v, nodeIdGenerator.fromKey(key));
    }
    @Test
    void testPut() {
        nodeIdGenerator.put("A");
        nodeIdGenerator.put("C");
        nodeIdGenerator.put("B");
        List<String> keys = nodeIdGenerator.keys();
        assertEquals(3, keys.size());
        assertEquals("A", keys.get(0));
        assertEquals("B", keys.get(1));
        assertEquals("C", keys.get(2));
    }
    @Test
    void testPutDuplicate() {
        nodeIdGenerator.put("A");
        assertThrows(IllegalArgumentException.class, () -> nodeIdGenerator.put("A"));
    }
}