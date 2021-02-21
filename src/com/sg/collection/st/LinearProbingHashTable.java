package com.sg.collection.st;

import com.sg.collection.bag.ArrayBag;
import com.sg.collection.bag.Bag;

public class LinearProbingHashTable<Key, Value> extends HashTable<Key, Value> {
    private final double LOAD_FACTOR = .5;
    private final Pair<Key, Value> TOMBSTONE = new Pair<>(null, null);

    private Pair<Key, Value>[] items;
    private int tombstoneSize;

    public LinearProbingHashTable() {
        items = allocatePairArray(DEFAULT_SIZE);
        bucketCount = DEFAULT_SIZE;
        tombstoneSize = 0;
    }

    @Override
    public void put(Key key, Value value) {
        if (key == null || value == null) {
            delete(key);
            return;
        }
        int index = hash(key);
        while (items[index] != null && items[index] != TOMBSTONE && !key.equals(items[index].key))
            index = (index + 1) % items.length;
        if (items[index] == null || items[index] == TOMBSTONE) {
            if (items[index] == TOMBSTONE) tombstoneSize--;
            items[index] = new Pair(key, value);
            size++;
            if (1.0 * (size + tombstoneSize) / items.length > LOAD_FACTOR) resize(items.length * 2);
        } else {
            items[index].value = value;
        }
    }

    @Override
    public Value get(Key key) {
        if (key == null) return null;
        int index = hash(key);
        while (items[index] != null && !key.equals(items[index].key)) index = (index + 1) % items.length;
        return (items[index] == null || items[index] == TOMBSTONE) ? null : items[index].value;
    }

    @Override
    public void delete(Key key) {
        if (key == null) return;
        int index = hash(key);
        while (items[index] != null && !key.equals(items[index].key)) index = (index + 1) % items.length;
        if (items[index] == null) return;
        items[index] = TOMBSTONE;
        tombstoneSize++;
        size--;
        if (1.0 * size / items.length < (LOAD_FACTOR / 2)) resize(Math.max(DEFAULT_SIZE, items.length / 2));
    }

    @Override
    public Iterable<Key> keys() {
        final Bag<Key> bag = new ArrayBag<>();
        for (int i = 0; i < items.length; i++)
            if (items[i] != TOMBSTONE && items[i] != null)
                bag.add(items[i].key);
        return bag;
    }

    private void resize(int capacity) {
        if (capacity == items.length && tombstoneSize > 0) return;
        final Pair<Key, Value>[] oldItems = items;
        items = allocatePairArray(capacity);
        size = 0;
        tombstoneSize = 0;
        bucketCount = capacity;
        for (int i = 0; i < oldItems.length; i++) {
            if (oldItems[i] != TOMBSTONE && oldItems[i] != null)
                put(oldItems[i].key, oldItems[i].value);
        }
    }

    private Pair<Key, Value>[] allocatePairArray(int capacity) {
        final Pair<Key, Value>[] pairs = new Pair[capacity];
        for (int i = 0; i < capacity; i++) pairs[i] = null;
        return pairs;
    }
}
