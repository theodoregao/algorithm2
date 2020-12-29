package com.sg.sort;

public class InsertionSort {
    public static <Item extends Comparable<Item>> void sort(Item[] items) {
        if (items == null || items.length <= 1) {
            return;
        }
        for (int i = 0; i < items.length; i++) {
            for (int j = i; j > 0 && SortUtil.less(items[j], items[j - 1]); j--) {
                SortUtil.swap(items, j, j - 1);
            }
        }
    }
}
