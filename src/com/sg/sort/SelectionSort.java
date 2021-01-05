package com.sg.sort;

public class SelectionSort {
    public static void sort(Comparable[] items) {
        if (items == null || items.length <= 1) {
            return;
        }
        for (int i = 0; i < items.length; i++) {
            int minIndex = i;
            for (int j = i; j < items.length; j++) {
                if (SortUtil.less(items[j], items[minIndex])) {
                    minIndex = j;
                }
            }
            SortUtil.swap(items, i, minIndex);
        }
    }
}
