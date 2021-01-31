package com.sg.collection.st;

import com.sg.collection.queue.LinkedListQueue;
import com.sg.collection.queue.Queue;

import java.util.Iterator;

public class BinarySearchTreeST<Key extends Comparable<Key>, Value> implements OrderST<Key, Value> {

    private Node root;

    public BinarySearchTreeST() {
        root = null;
    }

    @Override
    public void put(Key key, Value value) {
        if (key == null) return;
        if (value == null) {
            delete(key);
        } else {
            root = put(root, key, value);
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
        root = delete(root, key);
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
        if (size(root) == 0) throw new IllegalStateException();
        root = deleteMin(root);
    }

    @Override
    public void deleteMax() {
        if (size(root) == 0) throw new IllegalStateException();
        root = deleteMax(root);
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
        if (node == null) node = new Node(key);
        final int compare = key.compareTo(node.key);
        if (compare == 0) node.value = value;
        else if (compare < 0) node.left = put(node.left, key, value);
        else node.right = put(node.right, key, value);
        node.calculateSize();
        return node;
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
        if (parent == null) return null;
        if (parent.left == null) return parent.right;
        parent.left = deleteMin(parent.left);
        return parent;
    }

    private Node deleteMax(Node parent) {
        if (parent == null) return null;
        if (parent.right == null) return parent.left;
        parent.right = deleteMax(parent.right);
        return parent;
    }

    private Node delete(Node parent, Key key) {
        if (parent == null) return null;
        final int compare = key.compareTo(parent.key);
        if (compare < 0) {
            parent.left = delete(parent.left, key);
        } else if (compare > 0) {
            parent.right = delete(parent.right, key);
        } else {
            if (parent.left == null) return parent.right;
            else if (parent.right == null) return parent.left;
            else {
                final Node rightMin = min(parent.right);
                rightMin.left = parent.left;
                rightMin.right = deleteMin(parent.right);
                rightMin.calculateSize();
                parent = rightMin;
            }
        }
        parent.calculateSize();
        return parent;
    }

    private void inOrderIterator(Node parent, Queue<Key> queue, Key lo, Key hi) {
        if (parent == null) return;
        inOrderIterator(parent.left, queue, lo, hi);
        if (lo.compareTo(parent.key) <= 0 && parent.key.compareTo(hi) <= 0) {
            queue.enqueue(parent.key);
        }
        inOrderIterator(parent.right, queue, lo, hi);
    }

    private class Node {
        final Key key;
        Value value;
        Node left;
        Node right;
        int size;

        Node(Key key) {
            this.key = key;
        }

        void calculateSize() {
            size = size(left) + size(right) + 1;
        }
    }
}
