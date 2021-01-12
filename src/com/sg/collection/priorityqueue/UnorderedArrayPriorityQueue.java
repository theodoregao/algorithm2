package com.sg.collection.priorityqueue;

import java.util.Comparator;

public class UnorderedArrayPriorityQueue<Item extends Comparable<Item>> implements PriorityQueue<Item> {

    private static final int DEFAULT_SIZE = 16;
    private final Comparator<Item> comparator;
    private Item[] items;
    private int size;

    public UnorderedArrayPriorityQueue() {
        this(null);
    }

    public UnorderedArrayPriorityQueue(Comparator<Item> comparator) {
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
    }

    @Override
    public Item max() {
        return items[getMaxIndex()];
    }

    @Override
    public Item deleteMax() {
        final int maxIndex = getMaxIndex();
        final Item item = items[maxIndex];
        for (int i = maxIndex + 1; i < size; i++) {
            items[i - 1] = items[i];
        }
        size--;
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

    private int getMaxIndex() {
        if (isEmpty()) throw new IllegalStateException();
        int maxIndex = 0;
        for (int i = 1; i < size; i++) {
            int cmp = comparator != null ? comparator.compare(items[i], items[maxIndex]) : items[i].compareTo(items[maxIndex]);
            if (cmp > 0) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}
