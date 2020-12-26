package com.sg.collection.stack;

import java.util.Iterator;

public class ArrayStack<Item> implements Stack<Item> {
    private static final int DEFAULT_CAPACITY = 4;

    private Item[] items = (Item[]) new Object[DEFAULT_CAPACITY];
    private int top = 0;

    @Override
    public void push(Item item) {
        if (top == items.length) {
            resize(items.length * 2);
        }
        items[top++] = item;
    }

    @Override
    public Item pop() {
        if (isEmpty()) throw new IllegalStateException("Stack is empty");
        return items[--top];
    }

    @Override
    public boolean isEmpty() {
        return 0 == top;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ArrayStackIterator();
    }

    private void resize(int capacity) {
        final Item[] oldItems = items;
        items = (Item[]) new Object[capacity];
        final int count = Math.min(oldItems.length, items.length);
        for (int i = 0; i < count; i++) {
            items[i] = oldItems[i];
        }
    }

    private class ArrayStackIterator implements Iterator<Item> {

        private int currentTop = top;

        @Override
        public boolean hasNext() {
            return currentTop > 0;
        }

        @Override
        public Item next() {
            return items[--currentTop];
        }
    }
}
