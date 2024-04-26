package mouse.project.algorithm.red;

import lombok.Setter;

import java.util.*;

public class RBTreeImpl<T> implements RBTree<T> {
    private final Comparator<T> comparator;
    private final NilNode<T> nil = new NilNode<>();
    private RBNode<T> root = nil;
    private int size = 0;
    public RBTreeImpl(Comparator<T> comparator) {
        this.comparator = comparator;
    }
    public void insert(T value) {
        size++;
        if (root.isLeaf()) {
            root = new InnerRBNode<>(value, Color.BLACK, nil, nil);
            root.setParent(nil);
            return;
        }
        RBNode<T> z = new InnerRBNode<>(value, Color.RED, nil, nil);

        RBNode<T> y = nil;
        RBNode<T> x = root;

        while (!x.isLeaf()) {
            y = x;
            if (less(key(z), key(x))) {
                x = left(x);
            } else {
                x = right(x);
            }
        }
        p(z, y);
        if (less(key(z), key(y))) {
            left(y, z);
        } else {
            right(y, z);
        }
        insertFixup(z);
        nil.reset();
    }

    private void insertFixup(RBNode<T> z) {
        while (color(p(z)) == Color.RED) {
            if (p(z) == left(p(p(z)))) {
                RBNode<T> y = right(p(p(z)));
                if (color(y) == Color.RED) {
                    color(p(z), Color.BLACK);
                    color(y, Color.BLACK);
                    color(p(p(z)), Color.RED);
                    z = p(p(z));
                } else {
                    if (z == right(p(z))) {
                        z = p(z);
                        leftRotate(p(p(z)));
                    }
                    color(p(z), Color.BLACK);
                    color(p(p(z)), Color.RED);
                    rightRotate(p(p(z)));
                }
            } else {
                RBNode<T> y = left(p(p(z)));
                if (color(y) == Color.RED) {
                    color(p(z), Color.BLACK);
                    color(y, Color.BLACK);
                    color(p(p(z)), Color.RED);
                    z = p(p(z));
                } else {
                    if (z == left(p(z))) {
                        z = p(z);
                        rightRotate(p(p(z)));
                    }
                    color(p(z), Color.BLACK);
                    color(p(p(z)), Color.RED);
                    leftRotate(p(p(z)));
                }
            }
        }
        color(root, Color.BLACK);
    }

    public boolean contains(T value) {
        Optional<RBNode<T>> toDelete = findValue(value);
        return toDelete.isPresent();
    }
    public boolean delete(T value) {
        Optional<RBNode<T>> toDelete = findValue(value);
        if (toDelete.isEmpty()) {
            return false;
        }
        size--;
        RBNode<T> trbNode = toDelete.get();
        deleteNode(trbNode);
        nil.reset();
        return true;
    }

    private Optional<RBNode<T>> findValue(T value) {
        RBNode<T> x = root;
        while (!x.isLeaf()) {
            if (value.equals(key(x))) {
                return Optional.of(x);
            }
            if (less(value, key(x))) {
                x = left(x);
            } else {
                x = right(x);
            }
        }
        return Optional.empty();
    }

    private void transplant(RBNode<T> u, RBNode<T> v) {
        if (p(u).isLeaf()) {
            setRoot(v);
        }
        else if (u == left(p(u))) {
            left(p(u), v);
        } else {
            right(p(u), v);
        }
        p(v, p(u));
    }
    private void deleteNode(RBNode<T> z) {
        RBNode<T> y = z;
        RBNode<T> x;
        Color color = y.color();

        if (left(z).isLeaf()) {
            x = right(z);
            transplant(z, right(z));
        } else if (right(z).isLeaf()) {
            x = left(z);
            transplant(z, left(z));
        } else {
            y = minimum(right(z));
            color = y.color();
            x = right(y);
            if (p(y) == z) {
                p(x, y);
            } else {
                transplant(y, right(y));
                right(y, right(z));
                p(right(y), y);
            }
            transplant(z, y);
            left(y, left(z));
            p(left(y), y);
            color(y, color(z));
        }
        if (color == Color.BLACK) {
            deleteFixup(x);
        }
    }

    private RBNode<T> minimum(RBNode<T> current) {
        RBNode<T> prev = current;
        while (!current.isLeaf()) {
            prev = current;
            current = current.left();
        }
        return prev;
    }

    private void deleteFixup(RBNode<T> x) {
        while (x != root && color(x)==Color.BLACK) {
            if (x == left(p(x))) {
                RBNode<T> w = right(p(x));
                if (color(w) == Color.RED) {
                    color(w, Color.BLACK);
                    color(p(x), Color.RED);
                    leftRotate(p(x));
                    w = right(p(x));
                }
                if (color(left(w)) == Color.BLACK && color(right(w)) == Color.BLACK) {
                    color(w, Color.RED);
                    x = p(x);
                } else if (color(right(w)) == Color.BLACK) {
                    color(left(w), Color.BLACK);
                    color(w, Color.RED);
                    rightRotate(w);
                    w = right(p(x));
                }
                color(w, color(p(x)));
                color(p(x), Color.BLACK);
                color(right(w), Color.BLACK);
                leftRotate(p(x));
                break;
            } else {
                RBNode<T> w = left(p(x));
                if (color(w) == Color.RED) {
                    color(w, Color.BLACK);
                    color(p(x), Color.RED);
                    rightRotate((p(x)));
                    w = left(p(x));
                }
                if (color(right(w)) == Color.BLACK && color(left(w)) == Color.BLACK) {
                    color(w, Color.RED);
                    x = p(x);
                } else if (color(left(w)) == Color.BLACK) {
                    color(right(w), Color.BLACK);
                    color(w, Color.RED);
                    leftRotate(w);
                    w = left(p(x));
                }
                color(w, color(p(x)));
                color(p(x), Color.BLACK);
                color(left(w), Color.BLACK);
                rightRotate(p(x));
                break;
            }
        }
        color(x, Color.BLACK);
    }

    private static <T> void key(RBNode<T> z, T key) {
        z.setKey(key);
    }

    private static  <T> void color(RBNode<T> p, Color color) {
        p.setColor(color);
    }

    private static <T> Color color(RBNode<T> p) {
        return p.color();
    }

    private static <T> T key(RBNode<T> of) {
        return of.key();
    }
    private boolean less(T t1, T t2) {
        return comparator.compare(t1, t2) < 0;
    }

    public void print() {
        print(0, root);
    }

    @Override
    public void clear() {
        root = nil;
    }

    @Override
    public Collection<T> collect() {
        List<T> list = new ArrayList<>();
        collect(root, list);
        return list;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void collect(RBNode<T> current, List<T> list) {
        if (current.isLeaf()) {
            return;
        }
        collect(current.left(), list);
        list.add(current.key());
        collect(current.right(), list);
    }

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private void print(int tabLevel, RBNode<T> current) {
        String level = "--".repeat(tabLevel);
        String key = "";
        if (!current.isLeaf()) {
            key = current.key().toString();
        }
        String total = level + key;
        if (current.color()==Color.RED) {
            total = ANSI_RED + total + ANSI_RESET;
        }
        System.out.println(total);
        if (current.isLeaf()) {
            return;
        }
        if (current.left().isLeaf() && current.right().isLeaf()) {
            return;
        }
        print(tabLevel+1, current.left());
        print(tabLevel+1, current.right());
    }

    private enum Color {
        BLACK, RED
    }
    private interface RBNode<T> {
        T key();
        Color color();
        RBNode<T> left();
        RBNode<T> right();
        RBNode<T> parent();
        boolean isLeaf();
        void setRight(RBNode<T> left);
        void setLeft(RBNode<T> left);
        void setParent(RBNode<T> x);
        void setColor(Color color);
        void setKey(T key);
    }
    private static class InnerRBNode<T> implements RBNode<T> {
        private T key;
        @Setter
        private Color color;
        private RBNode<T> left;
        private RBNode<T> right;
        private RBNode<T> parent;

        public InnerRBNode(T key, Color color, RBNode<T> left, RBNode<T> right) {
            this.key = key;
            this.color = color;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return color + ": " + key;
        }

        @Override
        public T key() {
            return key;
        }

        @Override
        public Color color() {
            return color;
        }

        @Override
        public RBNode<T> left() {
            return left;
        }

        @Override
        public RBNode<T> right() {
            return right;
        }

        @Override
        public RBNode<T> parent() {
            return parent;
        }

        @Override
        public boolean isLeaf() {
            return false;
        }

        @Override
        public void setRight(RBNode<T> right) {
            this.right = right;
        }

        @Override
        public void setLeft(RBNode<T> left) {
            this.left = left;
        }

        @Override
        public void setParent(RBNode<T> parent) {
            this.parent = parent;
        }

        @Override
        public void setKey(T key) {
            this.key = key;
        }
    }

    private static class NilNode<T> implements RBNode<T> {
        private Color color;
        private RBNode<T> parent = this;
        private RBNode<T> left = this;
        private RBNode<T> right = this;
        public NilNode() {
            color = Color.BLACK;
        }

        @Override
        public T key() {
            throw new RBTreeException("Nil node has no key");
        }

        @Override
        public Color color() {
            return color;
        }

        @Override
        public RBNode<T> left() {
            return left;
        }

        @Override
        public RBNode<T> right() {
            return right;
        }

        @Override
        public RBNode<T> parent() {
            return parent;
        }

        @Override
        public boolean isLeaf() {
            return true;
        }

        @Override
        public void setRight(RBNode<T> node) {
            this.right = node;
        }

        @Override
        public void setLeft(RBNode<T> left) {
            this.left = left;
        }

        @Override
        public void setParent(RBNode<T> x) {
            this.parent = x;
        }

        @Override
        public void setColor(Color color) {
            this.color = color;
        }

        @Override
        public void setKey(T key) {
            throw new RBTreeException("Nil cannot have a key");
        }

        @Override
        public String toString() {
            return "nil";
        }

        public void reset() {
            this.left = this;
            this.right = this;
            this.parent = this;
        }
    }
    private void resetNil() {
        nil.reset();
    }
    private void leftRotate(RBNode<T> x) {
        RBNode<T> y = right(x);
        right(x, left(y));

        if (!left(y).isLeaf()) {
            p(left(y), x);
        }
        p(y, p(x));
        if (p(x).isLeaf()) {
            setRoot(y);
        } else if (x == left(p(x))) {
            left(p(x), y);
        } else {
            right(p(x), y);
        }
        left(y, x);
        p(x, y);
    }

    private void rightRotate(RBNode<T> x) {
        RBNode<T> y = left(x);
        left(x, right(y));

        if (right(y).isLeaf()) {
            p(right(y), x);
        }
        p(y, p(x));
        if (p(x).isLeaf()) {
            setRoot(y);
        } else if (x == right(p(x))) {
            right(p(x), y);
        } else {
            left(p(x), y);
        }
        right(y, x);
        p(x, y);

    }
    private static <T> RBNode<T> left(RBNode<T> of) {
        return of.left();
    }
    private static <T> RBNode<T> right(RBNode<T> of) {
        return of.right();
    }
    private static <T> RBNode<T> p(RBNode<T> of) {
        return of.parent();
    }

    private static <T> void left(RBNode<T> of, RBNode<T> set) {
        of.setLeft(set);
    }
    private static <T> void right(RBNode<T> of, RBNode<T> set) {
        of.setRight(set);
    }
    private static <T> void p(RBNode<T> of, RBNode<T> set) {
        of.setParent(set);
    }

    private void setRoot(RBNode<T> root) {
        this.root = root;
    }

}
