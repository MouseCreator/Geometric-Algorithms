package mouse.project.ui.components.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NodeIdGeneratorTest {
    private NodeIdGenerator nodeIdGenerator;
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
       nodeIdGenerator = new NodeIdGenerator();
       pairList = List.of(
               Pair.of(0, "_"),
               Pair.of(1, "A"),
               Pair.of(2, "B"),
               Pair.of(3, "C"),
               Pair.of(26, "Z"),
               Pair.of(27, "AA"),
               Pair.of(28, "AB"),
               Pair.of(52, "AZ"),
               Pair.of(53, "BA")
           );
}

    @Test
    void testKeyValue() {
        toKeyValue(27, "AA");
        for (Pair pair : pairList) {
            toKeyValue(pair.key, pair.str);
        }
    }
    void toKeyValue(int key, String v) {
        assertEquals(key, nodeIdGenerator.keyValue(v));
    }
    @Test
    void testFromKey() {
        fromKeyValue(27, "AA");
        for (Pair pair : pairList) {
            fromKeyValue(pair.key, pair.str);
        }
    }
    void fromKeyValue(int key, String v) {
        assertEquals(v, nodeIdGenerator.fromKey(key));
    }
}