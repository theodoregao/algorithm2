package com.sg.collection.stack;

public interface Stack<Item> extends Iterable<Item> {
    void push(Item item);

    Item pop();

    boolean isEmpty();
}
