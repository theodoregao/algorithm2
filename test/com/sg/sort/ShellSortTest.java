package com.sg.sort;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShellSortTest {

    @Test
    void initializeAnArrayWith100000Items_thenCallSort_correctOrderGenerated() {
        final int SIZE = 1000000;
        final Integer[] items = new Integer[SIZE];
        for (int i = 0; i < SIZE; i++) {
            items[i] = i;
        }
        SortUtil.shuffle(items);
        assertFalse(SortUtil.isSorted(items));
        ShellSort.sort(items);
        assertTrue(SortUtil.isSorted(items));
    }

}