package mouse.project.algorithm.sorted;

import java.util.List;

public interface SortedList<T> extends List<T> {
    T min();
    T max();
    T median();

    @Override
    SortedList<T> subList(int fromIndex, int toIndex);
}
