package mouse.project.algorithm.sweep;

import java.util.Comparator;
import java.util.Set;

public interface Status<T> {
    Neighbors<T> insert(T t);
    Neighbors<T> delete(T segment);
    void reorder(Set<T> set, Comparator<T> angleComparator);
}
