package mouse.project.saver;

import mouse.project.memory.MemoryHolder;
import mouse.project.memory.MemoryKeys;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SaveLoad {
    private static final GenericSaver GENERIC_SAVER = new GenericSaver();

    private static final String extension = "segments";
    private static final String defaultName = "example." + extension;
    public static void save(SavableHolder savableHolder) {
        String content = GENERIC_SAVER.toSaveString(savableHolder);

        JFileChooser fileChooser = initFileChooser();

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            onSave(fileChooser, content);
        }
    }

    private static JFileChooser initFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Files (*." + extension+")", extension));
        File file = chooseInitialPosition();
        fileChooser.setSelectedFile(file);
        return fileChooser;
    }

    private static void onSave(JFileChooser fileChooser, String content) {
        File fileToSave = fileChooser.getSelectedFile();
        String oldPath = fileToSave.getPath();
        if (!oldPath.endsWith("."+extension)) {
            fileToSave = new File(oldPath + "." + extension);
        }
        String newPath = fileToSave.getPath();
        FileManager fileManager = new FileManager();
        fileManager.save(content, newPath);
        MemoryHolder.get().put(MemoryKeys.LAST_USED_DIRECTORY, fileToSave.getParent());
        MemoryHolder.save();
    }

    public static void load(SavableHolder savableHolder) {

        JFileChooser fileChooser = initFileChooser();

        int userSelection = fileChooser.showOpenDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file= fileChooser.getSelectedFile();
            String filePath = file.getAbsolutePath();
            FileManager fileManager = new FileManager();
            String content = fileManager.load(filePath);
            MemoryHolder.get().put(MemoryKeys.LAST_USED_DIRECTORY, file.getParent());
            MemoryHolder.save();
            GENERIC_SAVER.fromSaveString(savableHolder, content);
        }

    }

    private static File chooseInitialPosition() {
        String s = MemoryHolder.get().get(MemoryKeys.LAST_USED_DIRECTORY);
        if (isValidPath(s)) {
            return new File(s + "/" + defaultName);
        }
        return new File(MemoryKeys.DEFAULT_DIRECTORY + "/" + defaultName);
    }

    private static boolean isValidPath(String s) {
        return Files.exists(Paths.get(s));
    }
}
