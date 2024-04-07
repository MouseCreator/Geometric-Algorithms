package mouse.project.memory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Memory implements Iterable<KeyValue> {
    private final Map<String, String> memoryStrings;

    public Memory() {
        memoryStrings = new HashMap<>();
    }

    public String get(String key) {
        String s = memoryStrings.get(key);
        if (s == null) {
            throw new MemoryException("No such key in memory " + key);
        }
        return s;
    }
    public void put(String key, String value) {
        memoryStrings.put(key, value);
    }
    @Override
    public Iterator<KeyValue> iterator() {
        return new Iterator<>() {
            private final Iterator<String> keys = memoryStrings.keySet().iterator();
            @Override
            public boolean hasNext() {
                return keys.hasNext();
            }

            @Override
            public KeyValue next() {
                String next = keys.next();
                return new KeyValue(next, memoryStrings.get(next));
            }
        };
    }
}
