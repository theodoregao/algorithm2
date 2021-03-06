package com.sg.collection.bag;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LinkedListBagTest {

    private Bag<String> bag;

    @BeforeEach
    void setUp() {
        bag = new LinkedListBag<>();
    }

    @Test
    void addSomeItemsIntoBag_thenCallSize_correctSizeReturned() {
        assertEquals(0, bag.size());
        for (int i = 1; i <= 10; i++) {
            bag.add(Integer.toString(i));
            assertEquals(i, bag.size());
        }
    }

    @Test
    void addSomeItemsIntoBag_thenIterateItems_correctItemsAccessed() {
        for (int i = 1; i <= 10; i++) {
            bag.add(Integer.toString(i));
        }
        int count = 0;
        for (String str : bag) {
            count++;
        }
        assertEquals(10, count);
    }

    @Test
    void addSomeItemsIntoBag_thenIterateItems2_correctItemsAccessed() {
        for (int i = 1; i <= 10; i++) {
            bag.add(Integer.toString(i));
        }
        int count = 0;
        for (String str : bag) {
            count++;
        }
        assertEquals(10, count);
    }
}