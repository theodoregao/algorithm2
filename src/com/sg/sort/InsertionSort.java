package com.sg.sort;

import java.util.Comparator;

public class InsertionSort {
    public static void sort(Comparable[] items) {
        for (int i = 0; i < items.length; i++) {
            for (int j = i; j > 0 && SortUtil.less(items[j], items[j - 1]); j--) {
                SortUtil.swap(items, j, j - 1);
            }
        }
    }

    public static <Item> void sort(Item[] items, Comparator<Item> comparator) {
        for (int i = 0; i < items.length; i++) {
            for (int j = i; j > 0 && comparator.compare(items[j], items[j - 1]) < 0; j--) {
                SortUtil.swap(items, j, j - 1);
            }
        }
    }
}
