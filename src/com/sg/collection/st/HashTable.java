package com.sg.collection.st;

public abstract class HashTable<Key, Value> implements ST<Key, Value> {
    protected final int DEFAULT_SIZE = 16;
    protected int bucketCount;
    protected int size;

    @Override
    public boolean contains(Key key) {
        return get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return size;
    }

    protected int hash(Key key) {
        if (key == null) return 0;
        int h = key.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12) ^ (h >>> 7) ^ (h >>> 4);
        return h & (bucketCount - 1);
    }

    protected static class Pair<Key, Value> {
        Key key;
        Value value;

        Pair() {
        }

        Pair(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }
}
