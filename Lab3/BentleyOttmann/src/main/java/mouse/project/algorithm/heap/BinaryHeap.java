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
        heapify(last());
    }

    private int last() {
        return list.size() - 1;
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
        T min = minimum();
        list.set(0, list.remove(last()));
        heapify(1);
        return min;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    private void heapify(int i) {
        int left = left(i);
        int right = right(i);
        int smallest;
        if (left < size() && less(left, i)) {
            smallest = left;
        } else {
            smallest = i;
        }
        if (right < size() && less(right, smallest)) {
            smallest = right;
        }
        if (smallest != i) {
            swap(i, smallest);
            heapify(smallest);
        }
    }

    private void swap(int i1, int i2) {
        T i1th = list.get(i1);
        T i2th = list.get(i1);
        list.set(i1, i2th);
        list.set(i2, i1th);
    }

    private boolean less(int left, int i) {
        return comparator.compare(list.get(left), list.get(i)) < 0;
    }

    private int parent(int i) {
        return (i >> 1) - 1;
    }
    private int left(int i) {
        return (i << 1) - 1;
    }
    private int right(int i) {
        return (i << 1);
    }
}
