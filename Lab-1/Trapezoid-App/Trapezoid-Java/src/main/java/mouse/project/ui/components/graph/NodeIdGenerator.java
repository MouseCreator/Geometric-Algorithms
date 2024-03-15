package mouse.project.ui.components.graph;

public interface NodeIdGenerator {
    String generateAndPut();
    void put(String id);
    void free(String id);
}
