package com.sg.collection.st;

import com.sg.collection.Node;
import com.sg.collection.bag.ArrayBag;
import com.sg.collection.bag.Bag;

public class SeparateChainingHashTable<Key, Value> extends HashTable<Key, Value> {
    private final double LOAD_FACTOR = 2.;

    private Node<Pair<Key, Value>>[] items;

    public SeparateChainingHashTable() {
        items = new Node[DEFAULT_SIZE];
        bucketCount = DEFAULT_SIZE;
        size = 0;
    }

    @Override
    public void put(Key key, Value value) {
        if (value == null || key == null) {
            delete(key);
            return;
        }
        final Node<Pair<Key, Value>> node = internalGet(key);
        if (node != null) node.item.value = value;
        else {
            final Pair pair = new Pair(key, value);
            items[hash(key)] = new Node(pair, items[hash(key)]);
            size++;
            if (1.0 * size / items.length > LOAD_FACTOR) {
                resize(items.length * 2);
            }
        }
    }

    @Override
    public Value get(Key key) {
        final Node<Pair<Key, Value>> node = internalGet(key);
        return node != null ? node.item.value : null;
    }

    @Override
    public void delete(Key key) {
        final Node<Pair<Key, Value>> node = internalGet(key);
        if (node == null) return;
        Node<Pair<Key, Value>> head = items[hash(key)];
        while (head != node && head.next != node) head = head.next;
        if (head == node) items[hash(key)] = head.next;
        else head.next = node.next;
        size--;
        if (size > 0 && 1.0 * items.length / size > LOAD_FACTOR) {
            resize(Math.max(DEFAULT_SIZE, items.length / 2));
        }
    }

    @Override
    public Iterable<Key> keys() {
        final Bag<Key> bag = new ArrayBag<>();
        for (Node<Pair<Key, Value>> node : items) {
            while (node != null) {
                bag.add(node.item.key);
                node = node.next;
            }
        }
        return bag;
    }

    private Node<Pair<Key, Value>> internalGet(Key key) {
        if (key == null) return null;
        Node<Pair<Key, Value>> node = items[hash(key)];
        while (node != null) {
            if (node.item.key.equals(key)) return node;
            node = node.next;
        }
        return null;
    }

    private void resize(int capacity) {
        if (capacity == items.length) return;
        final Node<Pair<Key, Value>>[] oldItems = items;
        items = new Node[capacity];
        size = 0;
        bucketCount = capacity;
        for (Node<Pair<Key, Value>> node : oldItems) {
            while (node != null) {
                put(node.item.key, node.item.value);
                node = node.next;
            }
        }
    }
}
