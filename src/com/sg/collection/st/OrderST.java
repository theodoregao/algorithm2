package com.sg.collection.st;

public interface OrderST<Key extends Comparable<Key>, Value> extends ST<Key, Value> {
    Key min();

    Key max();

    Key floor(Key key);

    Key ceiling(Key key);

    /**
     * Determine key's rank, which means how many other keys less than current key
     *
     * @param key
     * @return the rank
     */
    int rank(Key key);

    Key select(int k);

    void deleteMin();

    void deleteMax();

    int size(Key lo, Key hi);

    Iterable<Key> keys(Key lo, Key hi);
}
