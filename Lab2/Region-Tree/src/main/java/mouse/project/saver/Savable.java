package mouse.project.saver;

import java.util.Collection;
import java.util.List;

public interface Savable {
    String key();
    Collection<Object> supply();
    void consume(List<String> strings);
    int canConsume();
    default boolean dontSaveMe() {return false;}
}
