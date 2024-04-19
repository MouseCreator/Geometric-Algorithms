package mouse.project.algorithm.red;

import lombok.Setter;

import java.util.Comparator;
import java.util.Optional;

public class RBTreeImpl<T> implements RBTree<T> {
    private final Comparator<T> comparator;
    private final RBNode<T> nil = new NilNode<>();
    private RBNode<T> root = nil;
    public RBTreeImpl(Comparator<T> comparator) {
        this.comparator = comparator;
    }
    public void insert(T value) {
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
        RBNode<T> trbNode = toDelete.get();
        deleteNode(trbNode);
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
    private void deleteNode(RBNode<T> z) {
        RBNode<T> x;
        RBNode<T> y;
        if (left(z).isLeaf() || right(z).isLeaf()) {
            y = z;
        } else {
            y = successor(z);
        }
        if (!left(y).isLeaf()) {
            x = left(y);
        } else {
            x = right(y);
        }
        if (p(y).isLeaf()) {
            setRoot(x);
        } else if (y == (left(p(y)))) {
            left(p(y), x);
        } else {
            right(p(y), x);
        }
        if (y != z) {
            key(z, key(y));
        }
        if (color(y) == Color.BLACK) {
            deleteFixup(x);
        }
        // return y;
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
                }
            }
        }
    }

    private RBNode<T> successor(RBNode<T> z) {
        RBNode<T> current = right(z);
        RBNode<T> prev = current;
        while (!current.isLeaf()) {
            prev = current;
            current = current.left();
        }
        return prev;
    }

    private static  <T> void key(RBNode<T> z, T key) {
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
        private final Color color;

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
           throw new RBTreeException("Nil node does not have left child");
        }

        @Override
        public RBNode<T> right() {
            throw new RBTreeException("Nil node does not have right child");
        }

        @Override
        public RBNode<T> parent() {
            throw new RBTreeException("Nil node does not have defined parent");
        }

        @Override
        public boolean isLeaf() {
            return true;
        }

        @Override
        public void setRight(RBNode<T> left) {
            throw new RBTreeException("Nil node cannot have right child");
        }

        @Override
        public void setLeft(RBNode<T> left) {
            throw new RBTreeException("Nil node cannot have left child");
        }

        @Override
        public void setParent(RBNode<T> x) {
            throw new RBTreeException("Nil node cannot have parent");
        }

        @Override
        public void setColor(Color color) {
            throw new RBTreeException("Nil node is always black");
        }

        @Override
        public void setKey(T key) {
            throw new RBTreeException("Nil cannot have a key");
        }
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
