package mouse.project.ui.components.graph;

import java.util.*;
import java.util.regex.Pattern;

public class NodeIdGenerator {
    private final Set<String> foreignKeys;
    private final List<String> localKeys;
    public NodeIdGenerator() {
        foreignKeys = new HashSet<>();
        localKeys = new ArrayList<>();
    }
    public String generateAndPut() {
        String newKey = generateNext();
        put(newKey);
        return newKey;
    }

    private String generateNext() {
        String current = "_";
        for (String key : localKeys) {
            if (keyDistance(current, key) < 2) {
                current = key;
            } else {
                break;
            }
        }
        return nextKey(current);
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
        char[] charArray = key1.toCharArray();
        int sum = 0;
        int multiply = 27;
        int power = 1;
        for(int i = charArray.length - 1; i > -1; i--) {
            char ch = charArray[i];
            int charId = mapChar(ch);
            sum += power * charId;
            power *= multiply;
        }
        return sum;
    }

    private int mapChar(char ch) {
        return ch == '_' ? 0 : ch - 'A' + 1;
    }
    private char fromChar(int k) {
        return k == 0 ? '_' : (char) (k + 'A' - 1);
    }

    protected String fromKey(int key) {
        int modulo = 27;
        int current = key;
        StringBuilder result = new StringBuilder();
        do {
            int dif = current / modulo;
            int charId = current - dif * modulo;
            current = dif;
            char ch = fromChar(charId);
            result.insert(0, ch);
        } while (current > 0);
        return result.toString();
    }
    private boolean isLocalKey(String s) {
        String regex = "^[A-Z_]{3}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(s).matches();
    }
    public void put(String id) {
        if (isLocalKey(id)) {
            localKeys.add(id);
        } else {
            foreignKeys.add(id);
        }
    }
    public void free(String id) {
        foreignKeys.remove(id);
        localKeys.remove(id);
    }

    public boolean has(String key) {
        if (isLocalKey(key)) {
            return localKeys.contains(key);
        } else {
            return foreignKeys.contains(key);
        }
    }
}
