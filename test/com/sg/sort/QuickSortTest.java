package com.sg.sort;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {

    @Test
    void initializeAnArrayWith1000000Items_thenCallSort_correctOrderGenerated() {
        final int SIZE = 1000000;
        final Integer[] items = new Integer[SIZE];
        for (int i = 0; i < SIZE; i++) {
            items[i] = i;
        }
        SortUtil.shuffle(items);
        assertFalse(SortUtil.isSorted(items));
        QuickSort.sort(items);
        assertTrue(SortUtil.isSorted(items));
    }

    @Test
    void initializeAnArrayWith1000000Items_thenCallThreeWaySort_correctOrderGenerated() {
        final int SIZE = 1000000;
        final Integer[] items = new Integer[SIZE];
        for (int i = 0; i < SIZE; i++) {
            items[i] = i;
        }
        SortUtil.shuffle(items);
        assertFalse(SortUtil.isSorted(items));
        QuickSort.threeWaySort(items);
        assertTrue(SortUtil.isSorted(items));
    }

    @Test
    void initializeAnArrayWith1000000ItemsWith10DistinctValues_thenCallThreeWaySort_correctOrderGenerated() {
        final int SIZE = 1000000;
        final Integer[] items = new Integer[SIZE];
        for (int i = 0; i < SIZE; i++) {
            items[i] = i % 10;
        }
        SortUtil.shuffle(items);
        assertFalse(SortUtil.isSorted(items));
        QuickSort.threeWaySort(items);
//        QuickSort.sort(items); // won't able to stop for quick sort.
        assertTrue(SortUtil.isSorted(items));
    }

}