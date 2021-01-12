package com.sg.sort;

import com.sg.collection.priorityqueue.HeapPriorityQueue;
import com.sg.collection.priorityqueue.PriorityQueue;

public class HeapSort {

    public static <Item extends Comparable<Item>> void sortWithPriorityQueue(Item[] items) {
        if (items == null || items.length <= 1) return;
        final PriorityQueue<Item> pq = new HeapPriorityQueue<>(items);
        for (int i = items.length - 1; i >= 0; i--) {
            items[i] = pq.deleteMax();
        }
    }

    public static <Item extends Comparable<Item>> void sort(Item[] items) {
        if (items == null || items.length <= 1) return;
        int size = items.length;
        for (int i = (size - 1) / 2; i >= 0; i--) sink(items, size, i);
        while (size > 0) {
            SortUtil.swap(items, 0, --size);
            sink(items, size, 0);
        }
    }

    private static <Item extends Comparable<Item>> void sink(Item[] items, int size, int parent) {
        final int left = 2 * parent + 1, right = 2 * (parent + 1);
        if (left < size) {
            if (right < size && SortUtil.less(items[left], items[right]) && SortUtil.less(items[parent], items[right])) {
                SortUtil.swap(items, parent, right);
                sink(items, size, right);
            } else if (SortUtil.less(items[parent], items[left])) {
                SortUtil.swap(items, parent, left);
                sink(items, size, left);
            }
        }
    }
}
