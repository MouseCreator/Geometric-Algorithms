package mouse.project.memory;

import mouse.project.saver.FileManager;

public class MemoryHolder {
    private static Memory memory = null;
    private static MemorySaveLoad saveLoad = null;

    public static Memory get() {
        if (memory == null) {
            loadMemory();
        }
        return memory;
    }

    public static void save() {
        if (memory == null) {
            return;
        }
        saveMemory();
    }

    private static void saveMemory() {
        createSaveLoad();
        saveLoad.save(memory);
    }

    private static void createSaveLoad() {
        if (saveLoad == null) {
            saveLoad = new MemorySaveLoad(new FileManager());
        }
    }

    private static void loadMemory() {
        createSaveLoad();
        memory = saveLoad.load();
    }
}
