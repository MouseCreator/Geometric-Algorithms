package mouse.project.saver;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

public interface SavableHolder {
    void refresh();
    Collection<Savable> getSavables();
    Map<String, Supplier<Savable>> getKeyMap();
    void addAll(Collection<Savable> savables);
    void add(Savable savable);
}
