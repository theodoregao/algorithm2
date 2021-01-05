package com.sg.sort;

public class QuickSort {

    public static void sort(Comparable[] items) {
        SortUtil.shuffle(items);
        sortInternal(items, 0, items.length - 1);
    }

    public static void threeWaySort(Comparable[] items) {
        threeWaySortInternal(items, 0, items.length - 1);
    }

    private static void sortInternal(Comparable[] items, int lo, int hi) {
        if (lo >= hi) return;
        final int j = partition(items, lo, hi);
        sortInternal(items, lo, j - 1);
        sortInternal(items, j + 1, hi);
    }

    private static void threeWaySortInternal(Comparable[] items, int lo, int hi) {
        if (lo >= hi) return;
        final Comparable v = items[lo];
        int i = lo + 1, lt = lo, gt = hi;
        while (i <= gt) {
            if (SortUtil.less(items[i], v)) SortUtil.swap(items, i++, lt++);
            else if (SortUtil.less(v, items[i])) SortUtil.swap(items, i, gt--);
            else i++;
        }
        threeWaySortInternal(items, lo, lt - 1);
        threeWaySortInternal(items, gt + 1, hi);
    }

    private static int partition(Comparable[] items, int lo, int hi) {
        int i = lo + 1, j = hi;
        while (true) {
            while (i <= hi && !SortUtil.less(items[lo], items[i])) i++;
            while (j > lo && !SortUtil.less(items[j], items[lo])) j--;
            if (i >= j) break;
            SortUtil.swap(items, i, j);
        }
        SortUtil.swap(items, lo, j);
        return j;
    }

}
