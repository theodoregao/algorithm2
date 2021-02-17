package com.sg.collection.priorityqueue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnorderedArrayPriorityQueueTest {

    private PriorityQueue<Integer> pq;

    @Test
    void addItemsToPriorityQueue_thenDeleteMax_correctValueReturned() {
        pq = new UnorderedArrayPriorityQueue<>();
        assertEquals(0, pq.size());
        assertTrue(pq.isEmpty());

        for (int i = 0; i < 5; i++) {
            pq.insert(i);
        }
        assertEquals(5, pq.size());
        assertFalse(pq.isEmpty());
        assertEquals(4, pq.max());
        assertEquals(4, pq.deleteMax());
        assertEquals(3, pq.max());
        assertEquals(3, pq.deleteMax());

        for (int i = 0; i < 5; i++) {
            pq.insert(i);
        }
        assertEquals(8, pq.size());
        assertFalse(pq.isEmpty());
        assertEquals(4, pq.max());
        assertEquals(4, pq.deleteMax());
        assertEquals(3, pq.max());
        assertEquals(3, pq.deleteMax());
    }

    @Test
    void addItemsToMinPriorityQueue_thenDeleteMax_correctValueReturned() {
        pq = new UnorderedArrayPriorityQueue<>((o1, o2) -> o2 - o1);
        assertEquals(0, pq.size());
        assertTrue(pq.isEmpty());

        for (int i = 0; i < 100; i++) {
            pq.insert(i);
        }
        assertEquals(100, pq.size());
        assertFalse(pq.isEmpty());
        assertEquals(0, pq.max());
        assertEquals(0, pq.deleteMax());
        assertEquals(1, pq.max());
        assertEquals(1, pq.deleteMax());

        for (int i = 0; i < 100; i++) {
            pq.insert(i);
        }
        assertEquals(198, pq.size());
        assertFalse(pq.isEmpty());
        assertEquals(0, pq.max());
        assertEquals(0, pq.deleteMax());
        assertEquals(1, pq.max());
        assertEquals(1, pq.deleteMax());

        while (!pq.isEmpty()) pq.deleteMax();
    }

}