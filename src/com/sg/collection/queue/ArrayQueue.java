package com.sg.collection.queue;

import java.util.Iterator;

public class ArrayQueue<Item> implements Queue<Item> {
    private static final int DEFAULT_CAPACITY = 4;
    private Item[] items = (Item[]) new Object[DEFAULT_CAPACITY];
    private int head = 0; // next dequeue position
    private int tail = 0; // next enqueue position

    @Override
    public void enqueue(Item item) {
        if (tail == items.length) {
            resize(Math.max(DEFAULT_CAPACITY, (tail - head) * 2));
        }
        items[tail++] = item;
    }

    @Override
    public Item dequeue() {
        if (isEmpty()) throw new IllegalStateException("Queue is empty");
        return items[head++];
    }

    @Override
    public boolean isEmpty() {
        return head == tail;
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
        final int count = tail - head;
        for (int i = 0; i < count; i++) {
            items[i] = oldItems[i + head];
        }
        head = 0;
        tail = count;
    }

    private class ArrayQueueIterator implements Iterator<Item> {

        private int currentIndex = head;

        @Override
        public boolean hasNext() {
            return currentIndex != tail;
        }

        @Override
        public Item next() {
            return items[currentIndex++];
        }
    }
}
