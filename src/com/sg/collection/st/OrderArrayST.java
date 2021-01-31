package com.sg.collection.st;

import com.sg.collection.queue.LinkedListQueue;
import com.sg.collection.queue.Queue;

public class OrderArrayST<Key extends Comparable<Key>, Value> implements OrderST<Key, Value> {

    private static final int DEFAULT_CAPACITY = 16;

    private Key[] keys;
    private Value[] values;
    private int size;

    public OrderArrayST() {
        keys = (Key[]) new Comparable[DEFAULT_CAPACITY];
        values = (Value[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public void put(Key key, Value value) {
        if (key == null) return;
        final int index = indexOf(key);
        if (index >= 0) {
            if (value != null) {
                values[index] = value;
            } else {
                delete(key);
            }
        } else if (value != null) {
            final int rank = rank(key);
            if (size >= keys.length) resize(keys.length * 2);
            for (int i = size - 1; i >= rank; i--) {
                keys[i + 1] = keys[i];
                values[i + 1] = values[i];
            }
            keys[rank] = key;
            values[rank] = value;
            size++;
        }
    }

    @Override
    public Value get(Key key) {
        if (key == null) return null;
        final int index = indexOf(key);
        if (index < 0) return null;
        return values[index];
    }

    @Override
    public void delete(Key key) {
        if (key == null) return;
        int index = indexOf(key);
        if (index < 0) return;
        index++;
        while (index < size) {
            keys[index - 1] = keys[index];
            values[index - 1] = values[index];
            index++;
        }
        size--;
        if (size <= keys.length / 4 && keys.length / 2 >= DEFAULT_CAPACITY) {
            resize(keys.length / 2);
        }
    }

    @Override
    public boolean contains(Key key) {
        return key != null && indexOf(key) >= 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Key min() {
        return size == 0 ? null : keys[0];
    }

    @Override
    public Key max() {
        return size == 0 ? null : keys[size - 1];
    }

    @Override
    public Key floor(Key key) {
        if (key == null) return null;
        final int index = indexOf(key);
        if (index >= 0) return keys[index];
        final int rank = rank(key);
        return rank == 0 ? null : keys[rank - 1];
    }

    @Override
    public Key ceiling(Key key) {
        if (key == null) return null;
        final int index = indexOf(key);
        if (index >= 0) return keys[index];
        final int rank = rank(key);
        return rank >= size ? null : keys[rank];
    }

    @Override
    public int rank(Key key) {
        if (key == null) return -1;
        return rank(key, 0, size - 1);
    }

    @Override
    public Key select(int k) {
        if (k < 0 || k >= size) throw new IllegalArgumentException();
        return keys[k];
    }

    @Override
    public void deleteMin() {
        if (size <= 0) throw new IllegalStateException();
        delete(keys[0]);
    }

    @Override
    public void deleteMax() {
        if (size <= 0) throw new IllegalStateException();
        delete(keys[size - 1]);
    }

    @Override
    public int size(Key lo, Key hi) {
        final int loIndex = indexOf(lo);
        final int hiIndex = indexOf(hi);
        final int rangeLo = loIndex >= 0 ? loIndex : rank(lo);
        final int rangeHi = hiIndex >= 0 ? hiIndex : rank(hi) - 1;
        return rangeHi - rangeLo + 1;
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        final int loIndex = indexOf(lo);
        final int hiIndex = indexOf(hi);
        final int rangeLo = loIndex >= 0 ? loIndex : rank(lo);
        final int rangeHi = hiIndex >= 0 ? hiIndex : rank(hi) - 1;
        final Queue<Key> queue = new LinkedListQueue<>();
        for (int i = rangeLo; i <= rangeHi; i++) queue.enqueue(keys[i]);
        return queue;
    }

    @Override
    public Iterable<Key> keys() {
        final Queue<Key> queue = new LinkedListQueue<>();
        for (int i = 0; i < size; i++) queue.enqueue(keys[i]);
        return queue;
    }

    private void resize(int capacity) {
        final Key[] oldKeys = keys;
        final Value[] oldValues = values;
        keys = (Key[]) new Comparable[capacity];
        values = (Value[]) new Object[capacity];

        final int copyCapacity = Math.min(oldKeys.length, capacity);
        for (int i = 0; i < copyCapacity; i++) {
            keys[i] = oldKeys[i];
            values[i] = oldValues[i];
        }
    }

    private int indexOf(Key key) {
        return indexOf(key, 0, size - 1);
    }

    private int indexOf(Key key, int lo, int hi) {
        if (lo > hi) {
            return -1;
        }
        final int mid = (lo + hi) >> 1;
        final int compare = key.compareTo(keys[mid]);
        if (compare == 0) {
            return mid;
        } else if (compare < 0) {
            return indexOf(key, lo, mid - 1);
        } else {
            return indexOf(key, mid + 1, hi);
        }
    }

    private int rank(Key key, int lo, int hi) {
        if (lo > hi) {
            return lo;
        }
        final int mid = (lo + hi) >> 1;
        final int compare = key.compareTo(keys[mid]);
        if (compare == 0) {
            return mid;
        } else if (compare < 0) {
            return rank(key, lo, mid - 1);
        } else {
            return rank(key, mid + 1, hi);
        }
    }
}
