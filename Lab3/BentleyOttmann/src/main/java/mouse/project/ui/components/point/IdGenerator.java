package mouse.project.ui.components.point;

public interface IdGenerator {
    String generateAndPut();
    void put(String id);
    void free(String id);
}
