package mouse.project.memory;

import mouse.project.saver.FileManager;

public class MemorySaveLoad {
    private final static String MEMORY_FILE = "all.memory";

    private final FileManager fileManager;

    public MemorySaveLoad(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void save(Memory memory) {
        String content = toContentString(memory);
        fileManager.save(content, MEMORY_FILE);
    }

    private String toContentString(Memory memory) {
        StringBuilder builder = new StringBuilder();
        for (KeyValue kv : memory) {
            builder.append(kv.key()).append("=").append(kv.value()).append("\n");
        }
        return builder.toString();
    }

    public Memory load() {
        String loaded = fileManager.load(MEMORY_FILE);
        return readMemory(loaded);
    }

    private Memory readMemory(String loaded) {
        String[] lines = loaded.split("\n");
        Memory memory = new Memory();
        for (String line : lines) {
            if (line.isEmpty()) {
                continue;
            }
            String[] kv = line.split("=", 2);
            if (kv.length != 2) {
                throw new MemoryException("Unexpected line: " + line);
            }
            String key = kv[0];
            String value = kv[1];
            memory.put(key, value);
        }
        return memory;
    }
}
