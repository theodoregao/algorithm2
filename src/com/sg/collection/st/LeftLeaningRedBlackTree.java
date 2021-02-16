package com.sg.collection.st;

import com.sg.collection.queue.LinkedListQueue;
import com.sg.collection.queue.Queue;

import java.util.Iterator;

public class LeftLeaningRedBlackTree<Key extends Comparable<Key>, Value> implements OrderST<Key, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root;

    public LeftLeaningRedBlackTree() {
        root = null;
    }

    @Override
    public void put(Key key, Value value) {
        if (key == null) return;
        if (value == null) {
            delete(key);
        } else {
            root = put(root, key, value);
            root.color = BLACK;
        }
    }

    @Override
    public Value get(Key key) {
        if (key == null) return null;
        final Node node = get(root, key);
        return node == null ? null : node.value;
    }

    @Override
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException();
        if (!contains(key)) return;
        if (!isRed(root.left) && isRed(root.right)) root.color = RED;
        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
    }

    @Override
    public boolean contains(Key key) {
        return key != null && get(root, key) != null;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int size() {
        return size(root);
    }

    @Override
    public Key min() {
        final Node node = min(root);
        return node == null ? null : node.key;
    }

    @Override
    public Key max() {
        final Node node = max(root);
        return node == null ? null : node.key;
    }

    @Override
    public Key floor(Key key) {
        if (key == null) return null;
        final Node node = floor(root, key);
        return node == null ? null : node.key;
    }

    @Override
    public Key ceiling(Key key) {
        if (key == null) return null;
        final Node node = ceiling(root, key);
        return node == null ? null : node.key;
    }

    @Override
    public int rank(Key key) {
        if (key == null) return -1;
        return rank(root, key);
    }

    @Override
    public Key select(int k) {
        if (k < 0 || k >= size(root)) throw new IllegalArgumentException();
        final Node node = select(root, k);
        return node == null ? null : node.key;
    }

    @Override
    public void deleteMin() {
        if (isEmpty()) throw new IllegalStateException();
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
    }

    @Override
    public void deleteMax() {
        if (isEmpty()) throw new IllegalStateException();
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
    }

    @Override
    public int size(Key lo, Key hi) {
        final Iterator<Key> it = keys(lo, hi).iterator();
        int size = 0;
        while (it.hasNext()) {
            size++;
            it.next();
        }
        return size;
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        final Queue<Key> queue = new LinkedListQueue<>();
        inOrderIterator(root, queue, lo, hi);
        return queue;
    }

    @Override
    public Iterable<Key> keys() {
        if (size() == 0) return new LinkedListQueue<>();
        return keys(min(), max());
    }

    private Node get(Node parent, Key key) {
        if (parent == null) return null;
        final int compare = key.compareTo(parent.key);
        if (compare == 0) return parent;
        else if (compare < 0) return get(parent.left, key);
        else return get(parent.right, key);
    }

    private Node put(Node parent, Key key, Value value) {
        Node node = parent;
        if (node == null) node = new Node(key, value, RED);
        final int compare = key.compareTo(node.key);
        if (compare == 0) node.value = value;
        else if (compare < 0) node.left = put(node.left, key, value);
        else node.right = put(node.right, key, value);

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

    private Node max(Node parent) {
        if (parent == null) return null;
        else if (parent.right == null) return parent;
        else return max(parent.right);
    }

    private Node floor(Node parent, Key key) {
        if (parent == null) return null;
        final int compare = key.compareTo(parent.key);
        if (compare == 0) return parent;
        else if (compare < 0) return floor(parent.left, key);
        else {
            final Node rightCandidate = floor(parent.right, key);
            return rightCandidate == null ? parent : rightCandidate;
        }
    }

    private Node ceiling(Node parent, Key key) {
        if (parent == null) return null;
        final int compare = parent.key.compareTo(key);
        if (compare == 0) return parent;
        else if (compare < 0) return ceiling(parent.right, key);
        else {
            final Node leftCandidate = ceiling(parent.left, key);
            return leftCandidate == null ? parent : leftCandidate;
        }
    }

    private int rank(Node parent, Key key) {
        if (parent == null) return 0;
        final int compare = key.compareTo(parent.key);
        if (compare == 0) return size(parent.left);
        else if (compare < 0) return rank(parent.left, key);
        else return 1 + size(parent.left) + rank(parent.right, key);
    }

    private Node select(Node parent, int k) {
        if (parent == null) return null;
        if (size(parent.left) == k) return parent;
        else if (size(parent.left) > k) return select(parent.left, k);
        else return select(parent.right, k - size(parent.left) - 1);
    }

    private Node deleteMin(Node parent) {
        if (parent.left == null) return null;
        if (!isRed(parent.left) && !isRed(parent.right)) parent = moveRedLeft(parent);
        parent.left = deleteMin(parent.left);
        return balance(parent);
    }

    private Node deleteMax(Node parent) {
        if (isRed(parent.left)) parent = rotateRight(parent);
        if (parent.right == null) return null;
        if (!isRed(parent.right) && !isRed(parent.right.left)) parent = moveRedRight(parent);
        parent.right = deleteMax(parent.right);
        return balance(parent);
    }

    private Node delete(Node parent, Key key) {
        if (parent == null) return null;
        if (key.compareTo(parent.key) < 0) {
            if (!isRed(parent.left) && !isRed(parent.left.left)) parent = moveRedLeft(parent);
            parent.left = delete(parent.left, key);
        } else {
            if (isRed(parent.left)) parent = rotateRight(parent);
            if (key.compareTo(parent.key) == 0 && parent.right == null) return null;
            if (!isRed(parent.right) && !isRed(parent.right.left)) parent = moveRedRight(parent);
            if (key.compareTo(parent.key) == 0) {
                final Node rightMin = min(parent.right);
                rightMin.right = deleteMin(parent.right);
                rightMin.left = parent.left;
                parent = rightMin;
            } else {
                parent.right = delete(parent.right, key);
            }
        }
        return balance(parent);
    }

    private void inOrderIterator(Node parent, Queue<Key> queue, Key lo, Key hi) {
        if (parent == null) return;
        inOrderIterator(parent.left, queue, lo, hi);
        if (lo.compareTo(parent.key) <= 0 && parent.key.compareTo(hi) <= 0) {
            queue.enqueue(parent.key);
        }
        inOrderIterator(parent.right, queue, lo, hi);
    }

    private boolean isRed(Node node) {
        return node != null && node.color;
    }

    private Node rotateLeft(Node node) {
        final Node x = node.right;
        node.right = x.left;
        x.left = node;
        x.color = x.left.color;
        x.left.color = RED;
        node.calculateSize();
        x.calculateSize();
        return x;
    }

    private Node rotateRight(Node node) {
        Node x = node.left;
        node.left = x.right;
        x.right = node;
        x.color = x.right.color;
        x.right.color = RED;
        node.calculateSize();
        x.calculateSize();
        return x;
    }

    private void flipColor(Node node) {
        node.color = !node.color;
        node.left.color = !node.left.color;
        node.right.color = !node.right.color;
    }

    private Node balance(Node node) {
        if (isRed(node.right) && !isRed(node.left)) node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right)) flipColor(node);
        node.calculateSize();
        return node;
    }

    private Node moveRedLeft(Node node) {
        flipColor(node);
        if (isRed(node.right.left)) {
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
            flipColor(node);
        }
        return node;
    }

    private Node moveRedRight(Node node) {
        flipColor(node);
        if (isRed(node.left.left)) {
            node = rotateRight(node);
            flipColor(node);
        }
        return node;
    }

    private class Node {
        final Key key;
        Value value;
        Node left;
        Node right;
        int size;
        boolean color;

        Node(Key key, Value value, boolean color) {
            this.key = key;
            this.value = value;
            this.color = color;
        }

        void calculateSize() {
            size = size(left) + size(right) + 1;
        }
    }
}
