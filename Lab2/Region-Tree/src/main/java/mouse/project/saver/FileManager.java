package mouse.project.saver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {
    public String load(String filename) {
        try {
            return new String(Files.readAllBytes(Paths.get(filename)));
        } catch (IOException e) {
           throw new FileException("Cannot load file: " + filename, e);
        }
    }

    public void save(String content, String filename) {
        try {
            Files.write(Paths.get(filename), content.getBytes());
        } catch (IOException e) {
            throw new FileException("Cannot save file: " + filename, e);
        }
    }
    public void append(String content, String filename) {
        try {
            Files.write(Paths.get(filename), content.getBytes(), java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new FileException("Cannot append to file: " + filename, e);
        }
    }
}
