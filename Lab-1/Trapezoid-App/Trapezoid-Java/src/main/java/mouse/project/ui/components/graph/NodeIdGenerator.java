package mouse.project.ui.components.graph;

import java.util.*;

public class NodeIdGenerator {
    private final Set<String> foreignKeys;
    private final List<String> localKeys;
    public NodeIdGenerator() {
        foreignKeys = new HashSet<>();
        localKeys = new ArrayList<>();
    }
    public String generate() {
        String current = "_";
        for (String key : localKeys) {
            if (keyDistance(current, key) < 2) {
                current = key;
            } else {
                break;
            }
        }
        String newKey = nextKey(current);
        put(newKey);
        return newKey;
    }

    private String nextKey(String current) {
        int i = keyValue(current);
        return fromKey(i+1);
    }

    private int keyDistance(String key1, String key2) {
        int v1 = keyValue(key1);
        int v2 = keyValue(key2);
        return Math.abs(v1 - v2);
    }

    protected int keyValue(String key1) {
        if (key1.equals("_")) {
            return 0;
        }
        char[] charArray = key1.toCharArray();
        int sum = 0;
        int multiply = 27;
        int power = 1;
        for(int i = charArray.length - 1; i > -1; i--) {
            char ch = charArray[i];
            int charId = ch - 'A' + 1;
            sum += power * charId;
            power *= multiply;
        }
        return sum + 1;
    }

    protected String fromKey(int key) {
        if (key == 0) {
            return "_";
        }
        int modulo = 26;
        int current = key - 1;
        StringBuilder result = new StringBuilder();
        do {
            int dif = current / modulo;
            int charId = current - dif * modulo;
            current = dif;
            char ch = (char) (charId + 'A');
            result.insert(0, ch);
        } while (current > 0);
        return result.toString();
    }

    public void put(String id) {

    }
    public void free(String id) {

    }
}
