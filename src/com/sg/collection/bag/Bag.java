package com.sg.collection.bag;

public interface Bag<Item> extends Iterable<Item> {
    void add(Item item);
    int size();
}
