package mouse.project.ui.components.point;

public interface PointIdGenerator {
    String generateAndPut();
    void put(String id);
    void free(String id);
}
