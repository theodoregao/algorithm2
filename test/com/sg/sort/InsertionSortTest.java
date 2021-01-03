package com.sg.sort;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class InsertionSortTest {

    @Test
    void initializeAnArrayWith1000Items_thenCallSort_correctOrderGenerated() {
        final int SIZE = 1000;
        final Integer[] items = new Integer[SIZE];
        for (int i = 0; i < SIZE; i++) {
            items[i] = i;
        }
        SortUtil.shuffle(items);
        assertFalse(SortUtil.isSorted(items));
        InsertionSort.sort(items);
        assertTrue(SortUtil.isSorted(items));
    }

    @Test
    void initializeAnArrayWith1000ItemsAndComparator_thenCallSort_correctOrderGenerated() {
        final int SIZE = 1000;
        final Integer[] items = new Integer[SIZE];
        final Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer first, Integer second) {
                return second - first;
            }
        };
        for (int i = 0; i < SIZE; i++) {
            items[i] = i;
        }
        SortUtil.shuffle(items);
        assertFalse(SortUtil.isSorted(items, comparator));
        InsertionSort.sort(items, comparator);
        assertTrue(SortUtil.isSorted(items, comparator));
        for (int i = 0; i < SIZE; i++) {
            assertEquals(SIZE - i - 1, items[i]);
        }
    }

}