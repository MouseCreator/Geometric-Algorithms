package mouse.project.saver;

import mouse.project.ui.components.graph.UIGraph;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.*;

public class SaveLoad {
    private static GraphSaver graphSaver = new GraphSaver();
    private static Logger logger = LogManager.getLogger(SaveLoad.class);
    public static void save(UIGraph uiGraph) {

        String content = graphSaver.toSaveString(uiGraph);

        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setSelectedFile(new File("example.graph"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getPath().endsWith(".format")) {
                fileToSave = new File(fileToSave.getPath() + ".format");
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                writer.write(content);
            } catch (IOException e) {
                logger.error("Error saving a file: " + e);
            }
        }
    }
}
