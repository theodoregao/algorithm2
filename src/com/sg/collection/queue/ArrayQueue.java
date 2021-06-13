package com.sg.collection.queue;

import java.util.Iterator;

public class ArrayQueue<Item> implements Queue<Item> {
    private static final int DEFAULT_CAPACITY = 4;
    private Item[] items = (Item[]) new Object[DEFAULT_CAPACITY];
    private int head = 0; // next dequeue position
    private int size = 0; // head + size will be next enqueue position

    @Override
    public void enqueue(Item item) {
        if (size == items.length) {
            resize(Math.max(DEFAULT_CAPACITY, size * 2));
        }
        items[(head + size++) % items.length] = item;
    }

    @Override
    public Item dequeue() {
        if (isEmpty()) throw new IllegalStateException("Queue is empty");
        size--;
        return items[head++ % items.length];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ArrayQueueIterator();
    }

    private void resize(int capacity) {
        final Item[] oldItems = items;
        if (capacity != oldItems.length) {
            items = (Item[]) new Object[capacity];
        }
        final int LENGTH = Math.min(items.length, oldItems.length);
        for (int tail = 0; tail < LENGTH; tail++) {
            items[tail] = oldItems[head++ % oldItems.length];
        }
        head = 0;
    }

    private class ArrayQueueIterator implements Iterator<Item> {
        private int sz = 0;

        @Override
        public boolean hasNext() {
            return sz < size;
        }

        @Override
        public Item next() {
            return items[(head + sz++) % items.length];
        }
    }
}
