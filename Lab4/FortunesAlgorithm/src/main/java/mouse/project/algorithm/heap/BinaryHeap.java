package mouse.project.algorithm.heap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BinaryHeap<T> implements Heap<T> {

    private final List<T> list;
    private final Comparator<T> comparator;

    public BinaryHeap(Comparator<T> comparator) {
        this.comparator = comparator;
        this.list = new ArrayList<>();
    }

    @Override
    public void insert(T value) {
        list.add(value);
        heapifyUp(list.size() - 1);
    }

    private void heapifyUp(int index) {
        while (index > 0 && less(index, parent(index))) {
            swap(index, parent(index));
            index = parent(index);
        }
    }

    @Override
    public T minimum() {
        if (isEmpty()) {
            throw new HeapException("Empty heap");
        }
        return list.get(0);
    }

    @Override
    public T extractMin() {
        if(isEmpty()) {
            throw new HeapException("Empty heap");
        }
        T min = list.get(0);
        if (size()==1) {
            clear();
            return min;
        }
        list.set(0, list.remove(list.size() - 1));
        if (!list.isEmpty()) {
            heapifyDown(0);
        }
        return min;
    }

    private void heapifyDown(int index) {
        int smallest = index;
        int left = left(index);
        int right = right(index);

        if (left < list.size() && less(left, smallest)) {
            smallest = left;
        }
        if (right < list.size() && less(right, smallest)) {
            smallest = right;
        }

        if (smallest != index) {
            swap(index, smallest);
            heapifyDown(smallest);
        }
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    private void swap(int i1, int i2) {
        T temp = list.get(i1);
        list.set(i1, list.get(i2));
        list.set(i2, temp);
    }

    private boolean less(int i, int j) {
        return comparator.compare(list.get(i), list.get(j)) < 0;
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }
    private int left(int i) {
        return 2 * i + 1;
    }
    private int right(int i) {
        return 2 * i + 2;
    }

    public void clear() {
        list.clear();
    }

    @Override
    public String toString() {
        return "Heap: " + list;
    }
}