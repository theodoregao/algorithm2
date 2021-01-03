package com.sg.sort;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {

    @Test
    void initializeAnArrayWith1000000Items_thenCallSort_correctOrderGenerated() {
        final int SIZE = 1000000;
        final Integer[] items = new Integer[SIZE];
        for (int i = 0; i < SIZE; i++) {
            items[i] = i;
        }
        SortUtil.shuffle(items);
        assertFalse(SortUtil.isSorted(items));
        MergeSort.sort(items);
        assertTrue(SortUtil.isSorted(items));
    }

    @Test
    void initializeAnArrayWith1000Items_thenCallBottomUpSort_correctOrderGenerated() {
        final int SIZE = 1000000;
        final Integer[] items = new Integer[SIZE];
        for (int i = 0; i < SIZE; i++) {
            items[i] = i;
        }
        SortUtil.shuffle(items);
        assertFalse(SortUtil.isSorted(items));
        MergeSort.bottomUpSort(items);
        assertTrue(SortUtil.isSorted(items));
    }

}