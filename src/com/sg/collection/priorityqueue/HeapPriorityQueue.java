package com.sg.collection.priorityqueue;

import java.util.Comparator;

public class HeapPriorityQueue<Item extends Comparable<Item>> implements PriorityQueue<Item> {

    private static final int DEFAULT_SIZE = 16;
    private final Comparator<Item> comparator;
    private Item[] items;
    private int size;

    public HeapPriorityQueue() {
        this((Comparator) null);
    }

    public HeapPriorityQueue(Comparator<Item> comparator) {
        this.comparator = comparator;
        items = (Item[]) new Comparable[DEFAULT_SIZE];
        size = 0;
    }

    public HeapPriorityQueue(Item[] data) {
        this(data, null);
    }

    public HeapPriorityQueue(Item[] data, Comparator<Item> comparable) {
        this(comparable);
        items = data.clone();
        size = items.length;
        for (int i = parent(size - 1); i >= 0; i--) sink(i);
    }

    @Override
    public void insert(Item item) {
        if (size >= items.length) {
            resize(items.length * 2);
        }
        items[size++] = item;
        swim(size - 1);
    }

    @Override
    public Item max() {
        if (isEmpty()) throw new IllegalStateException();
        return items[0];
    }

    @Override
    public Item deleteMax() {
        if (isEmpty()) throw new IllegalStateException();
        final Item item = items[0];
        items[0] = items[--size];
        items[size] = null;
        sink(0);
        return item;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    private void resize(int size) {
        final Item[] oldItems = items;
        items = (Item[]) new Comparable[size];
        for (int i = 0; i < Math.min(oldItems.length, size); i++) {
            items[i] = oldItems[i];
        }
    }

    private int left(int parent) {
        return 2 * parent + 1;
    }

    private int right(int parent) {
        return 2 * (parent + 1);
    }

    private int parent(int child) {
        return (child - 1) / 2;
    }

    private boolean less(int thiz, int that) {
        return (comparator != null ? comparator.compare(items[thiz], items[that]) : items[thiz].compareTo(items[that])) < 0;
    }

    private void swap(int i, int j) {
        final Item item = items[i];
        items[i] = items[j];
        items[j] = item;
    }

    private void sink(int parent) {
        while (left(parent) < size) {
            final int left = left(parent), right = right(parent);
            int child = left;
            if (right < size && less(left, right)) child = right;
            if (!less(parent, child)) break;
            swap(parent, child);
            parent = child;
        }
    }

    private void swim(int child) {
        if (child > 0) {
            int parent = parent(child);
            if (less(parent, child)) {
                swap(parent, child);
                swim(parent);
            }
        }
    }
}
