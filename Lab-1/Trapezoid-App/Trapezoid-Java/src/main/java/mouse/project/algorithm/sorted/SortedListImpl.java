package mouse.project.algorithm.sorted;

import java.util.*;

public class SortedListImpl<T> implements SortedList<T> {
    private final List<T> list;
    private final Comparator<T> comparator;
    public SortedListImpl(Comparator<T> comparator) {
        this.comparator = comparator;
        this.list = new ArrayList<>();
    }
    public SortedListImpl(List<T> list, Comparator<T> comparator) {
        this.comparator = comparator;
        this.list = list;
        list.sort(comparator);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(T t) {
        int index = -1;
        for (int i = 0; i < size(); i++) {
            if (comparator.compare(t, list.get(i)) < 0) {
                continue;
            }
            index = i;
            break;
        }
        if (index==-1) {
            list.add(t);
        } else {
            list.add(index, t);
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return new HashSet<>(list).containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        c.forEach(this::add);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException("Cannot add to specific index: sorted order may be violated.");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException("Cannot set to specific index. Sorted order may be violated");
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException("Cannot add to specific index. Sorted order may be violated");
    }

    @Override
    public T remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public SortedList<T> subList(int fromIndex, int toIndex) {
        return new SortedListImpl<>(list.subList(fromIndex, toIndex), comparator);
    }

    @Override
    public T min() {
        return get(0);
    }

    @Override
    public T max() {
        return get(size()-1);
    }

    @Override
    public T median() {
        return get(size()>>>1);
    }
}
