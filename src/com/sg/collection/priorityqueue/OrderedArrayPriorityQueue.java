package com.sg.collection.priorityqueue;

import java.util.Comparator;

public class OrderedArrayPriorityQueue<Item extends Comparable<Item>> implements PriorityQueue<Item> {

    private static final int DEFAULT_SIZE = 16;
    private final Comparator<Item> comparator;
    private Item[] items;
    private int size;

    public OrderedArrayPriorityQueue() {
        this(null);
    }

    public OrderedArrayPriorityQueue(Comparator<Item> comparator) {
        this.comparator = comparator;
        items = (Item[]) new Comparable[DEFAULT_SIZE];
        size = 0;
    }

    @Override
    public void insert(Item item) {
        if (size >= items.length) {
            resize(items.length * 2);
        }
        items[size++] = item;
        for (int i = size - 1; i >= 1 && less(items[i], items[i - 1]); i--) {
            swap(i, i - 1);
        }
    }

    @Override
    public Item max() {
        if (isEmpty()) throw new IllegalStateException();
        return items[size - 1];
    }

    @Override
    public Item deleteMax() {
        if (isEmpty()) throw new IllegalStateException();
        final Item item = items[--size];
        if (size < items.length / 4 && size / 2 > DEFAULT_SIZE) {
            resize(items.length / 2);
        }
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

    private boolean less(Item thiz, Item that) {
        return (comparator != null ? comparator.compare(thiz, that) : thiz.compareTo(that)) < 0;
    }

    private void swap(int i, int j) {
        final Item item = items[i];
        items[i] = items[j];
        items[j] = item;
    }
}
