package com.sg.collection.bag;

import java.util.Iterator;

public class ArrayBag<Item> implements Bag<Item> {
    private static final int DEFAULT_CAPACITY = 4;
    private Item[] items = (Item[]) new Object[DEFAULT_CAPACITY];
    private int size;

    @Override
    public void add(Item item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[size++] = item;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ArrayBagIterator();
    }

    private void resize(int capacity) {
        final Item[] oldItems = items;
        items = (Item[]) new Object[capacity];
        final int count = Math.min(oldItems.length, items.length);
        for (int i = 0; i < count; i++) {
            items[i] = oldItems[i];
        }
    }

    private class ArrayBagIterator implements Iterator<Item> {

        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public Item next() {
            return items[currentIndex++];
        }
    }
}
