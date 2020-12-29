package com.sg.sort;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortUtilTest {

    @Test
    void initializeAnArray_thenSwapTwoItems_correctOrderGenerated() {
        final int SIZE = 10;
        final Integer[] items = new Integer[SIZE];
        for (int i = 0; i < SIZE; i++) {
            items[i] = i;
        }
        SortUtil.swap(items, 1, 8);
        for (int i = 0; i < SIZE; i++) {
            if (i == 1) {
                assertEquals(8, items[i]);
            } else if (i == 8) {
                assertEquals(1, items[i]);
            } else {
                assertEquals(i, items[i]);
            }
        }
    }

    @Test
    void initializeAnArray_thenDoShuffle_randomOrderGenerated() {
        final int SIZE = 10;
        final Integer[] items = new Integer[SIZE];
        for (int i = 0; i < SIZE; i++) {
            items[i] = i;
        }
        assertTrue(SortUtil.isSorted(items));
        SortUtil.shuffle(items);
        assertFalse(SortUtil.isSorted(items));
    }

}