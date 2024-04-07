package mouse.project.saver;

import mouse.project.memory.MemoryHolder;
import mouse.project.memory.MemoryKeys;
import mouse.project.ui.components.point.PointSet;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SaveLoad {
    private static final GenericSaver GENERIC_SAVER = new GenericSaver();
    private static final String defaultName = "example.graph";
    public static void save(PointSet pointSet) {
        String content = GENERIC_SAVER.toSaveString(pointSet);

        JFileChooser fileChooser = initFileChooser();

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            onSave(fileChooser, content);
        }
    }

    private static JFileChooser initFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Point files (*.points)", "points"));
        File file = chooseInitialPosition();
        fileChooser.setSelectedFile(file);
        return fileChooser;
    }

    private static void onSave(JFileChooser fileChooser, String content) {
        File fileToSave = fileChooser.getSelectedFile();
        String oldPath = fileToSave.getPath();
        if (!oldPath.endsWith(".points")) {
            fileToSave = new File(oldPath + ".points");
        }
        String newPath = fileToSave.getPath();
        FileManager fileManager = new FileManager();
        fileManager.save(content, newPath);
        MemoryHolder.get().put(MemoryKeys.LAST_USED_DIRECTORY, fileToSave.getParent());
        MemoryHolder.save();
    }

    public static void load(PointSet pointSet) {

        JFileChooser fileChooser = initFileChooser();

        int userSelection = fileChooser.showOpenDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file= fileChooser.getSelectedFile();
            String filePath = file.getAbsolutePath();
            FileManager fileManager = new FileManager();
            String content = fileManager.load(filePath);
            MemoryHolder.get().put(MemoryKeys.LAST_USED_DIRECTORY, file.getParent());
            MemoryHolder.save();
            GENERIC_SAVER.fromSaveString(pointSet, content);
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
