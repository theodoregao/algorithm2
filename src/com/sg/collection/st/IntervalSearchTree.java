package com.sg.collection.st;

import com.sg.collection.bag.ArrayBag;
import com.sg.collection.bag.Bag;

public class IntervalSearchTree<Key extends Comparable<Key>, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root;

    public IntervalSearchTree() {
        root = null;
    }

    public void put(Interval<Key> interval, Value value) {
        if (interval == null) return;
        if (value == null) {
            delete(interval);
        } else {
            root = put(root, interval, value);
            root.color = BLACK;
        }
    }

    public Value get(Interval<Key> interval) {
        if (interval == null) return null;
        final Node node = get(root, interval);
        return node == null ? null : node.value;
    }

    public void delete(Interval<Key> interval) {
        if (interval == null) throw new IllegalArgumentException();
        if (!contains(interval)) return;
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = delete(root, interval);
        if (!isEmpty()) root.color = BLACK;
    }

    public Value anyIntersects(Key lo, Key hi) {
        final Interval<Key> interval = new Interval<>(lo, hi);
        Node x = root;
        while (x != null) {
            if (x.interval.intersects(interval)) return get(x.interval);
            else if (x.left == null) x = x.right;
            else if (x.left.max.compareTo(lo) < 0) x = x.right;
            else x = x.left;
        }
        return null;
    }

    public Iterable<Value> intersects(Interval<Key> interval) {
        final Bag<Value> bag = new ArrayBag<>();
        return intersects(root, interval, bag);
    }

    private Iterable<Value> intersects(Node parent, Interval<Key> interval, Bag<Value> bag) {
        if (parent == null) {
            return bag;
        }
        if (parent.interval.intersects(interval)) bag.add(get(parent.interval));
        if (interval.hi.compareTo(parent.interval.lo) >= 0) {
            intersects(parent.right, interval, bag);
        }
        intersects(parent.left, interval, bag);
        return bag;
    }

    public boolean contains(Interval<Key> interval) {
        return interval != null && get(root, interval) != null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size(root);
    }


    /***************************************************************************
     *  Internal helper methods.
     ***************************************************************************/

    private Node get(Node parent, Interval<Key> interval) {
        if (parent == null) return null;
        final int compare = interval.compareTo(parent.interval);
        if (compare == 0) return parent;
        else if (compare < 0) return get(parent.left, interval);
        else return get(parent.right, interval);
    }

    private Node put(Node parent, Interval<Key> interval, Value value) {
        Node node = parent;
        if (node == null) node = new Node(interval, value, RED);
        final int compare = interval.compareTo(node.interval);
        if (compare == 0) node.value = value;
        else if (compare < 0) node.left = put(node.left, interval, value);
        else node.right = put(node.right, interval, value);

        return balance(node);
    }

    private int size(Node parent) {
        if (parent == null) return 0;
        return parent.size;
    }

    private Node min(Node parent) {
        if (parent == null) return null;
        else if (parent.left == null) return parent;
        else return min(parent.left);
    }

    private Node maxInterval(Node parent) {
        Node max = maxInterval(parent.left, parent.right);
        return maxInterval(parent, max);
    }

    private Node maxInterval(Node a, Node b) {
        if (a == null) return b;
        if (b == null) return a;
        return a.interval.compareTo(b.interval) >= 0 ? a : b;
    }

    private Node deleteMin(Node parent) {
        if (parent.left == null) return null;
        if (!isRed(parent.left) && !isRed(parent.left.left)) parent = moveRedLeft(parent);
        parent.left = deleteMin(parent.left);
        return balance(parent);
    }

    private Node delete(Node parent, Interval<Key> interval) {
        if (parent == null) return null;
        if (interval.compareTo(parent.interval) < 0) {
            if (!isRed(parent.left) && !isRed(parent.left.left)) parent = moveRedLeft(parent);
            parent.left = delete(parent.left, interval);
        } else {
            if (isRed(parent.left)) parent = rotateRight(parent);
            if (interval.compareTo(parent.interval) == 0 && parent.right == null) return null;
            if (!isRed(parent.right) && !isRed(parent.right.left)) parent = moveRedRight(parent);
            if (interval.compareTo(parent.interval) == 0) {
                final Node rightMin = min(parent.right);
                parent.interval = rightMin.interval;
                parent.value = rightMin.value;
                parent.right = deleteMin(parent.right);
            } else {
                parent.right = delete(parent.right, interval);
            }
        }
        return balance(parent);
    }

    private boolean isRed(Node node) {
        return node != null && node.color;
    }

    /***************************************************************************
     *  Red-black tree helper functions.
     ***************************************************************************/

    private Node rotateLeft(Node node) {
        assert node != null && isRed(node.right);
        final Node x = node.right;
        node.right = x.left;
        x.left = node;
        x.color = x.left.color;
        x.left.color = RED;
        node.calculateInternalFields();
        x.calculateInternalFields();
        return x;
    }

    private Node rotateRight(Node node) {
        assert node != null && isRed(node.left);
        Node x = node.left;
        node.left = x.right;
        x.right = node;
        x.color = x.right.color;
        x.right.color = RED;
        node.calculateInternalFields();
        x.calculateInternalFields();
        return x;
    }

    private void flipColor(Node node) {
        assert node != null && node.left != null && node.right != null;
        assert (!isRed(node) && isRed(node.left) && isRed(node.right))
                || (isRed(node) && !isRed(node.left) && !isRed(node.right));
        node.color = !node.color;
        node.left.color = !node.left.color;
        node.right.color = !node.right.color;
    }

    private Node balance(Node node) {
        assert node != null;
        if (isRed(node.right) && !isRed(node.left)) node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right)) flipColor(node);
        node.calculateInternalFields();
        return node;
    }

    private Node moveRedLeft(Node node) {
        assert (node != null);
        assert isRed(node) && !isRed(node.left) && !isRed(node.left.left);
        flipColor(node);
        if (isRed(node.right.left)) {
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
            flipColor(node);
        }
        return node;
    }

    private Node moveRedRight(Node node) {
        assert (node != null);
        assert isRed(node) && !isRed(node.right) && !isRed(node.right.left);
        flipColor(node);
        if (isRed(node.left.left)) {
            node = rotateRight(node);
            flipColor(node);
        }
        return node;
    }

    public static class Interval<Key extends Comparable<Key>> implements Comparable<Interval<Key>> {
        final Key lo, hi;

        Interval(Key lo, Key hi) {
            this.lo = lo;
            this.hi = hi;
        }

        public boolean intersects(Interval<Key> interval) {
            return !(lo.compareTo(interval.hi) > 0 || hi.compareTo(interval.lo) < 0);
        }

        @Override
        public int compareTo(Interval<Key> interval) {
            final int diffLo = lo.compareTo(interval.lo);
            final int diffHi = hi.compareTo(interval.hi);
            return diffLo != 0 ? diffLo : diffHi;
        }
    }

    private class Node {
        Interval<Key> interval;
        Value value;
        Node left;
        Node right;
        int size;
        Key max;
        boolean color;

        Node(Interval<Key> interval, Value value, boolean color) {
            this.interval = interval;
            this.value = value;
            this.color = color;
        }

        void calculateInternalFields() {
            size = size(left) + size(right) + 1;
            max = maxInterval(this).interval.hi;
        }
    }
}
