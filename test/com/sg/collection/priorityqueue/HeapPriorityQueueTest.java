package com.sg.collection.priorityqueue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapPriorityQueueTest {

    private PriorityQueue<Integer> pq;

    @Test
    void addItemsToPriorityQueue_thenDeleteMax_correctValueReturned() {
        pq = new HeapPriorityQueue<>();
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
        pq = new HeapPriorityQueue<>((o1, o2) -> o2 - o1);
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

    @Test
    void testPriorityQueueWithInitialArray_thenCallDeleteMax_correctOrderReturned() {
        final Integer[] integers = {1, 3, 5, 7, 9, 2, 4, 6, 8, 10};
        final PriorityQueue<Integer> pq = new HeapPriorityQueue<>(integers);
        assertEquals(10, pq.size());
        assertFalse(pq.isEmpty());
        for (int i = 10; i > 0; i--) {
            assertEquals(i, pq.max());
            assertEquals(i, pq.deleteMax());
        }
    }

    @Test
    void testPriorityQueueWithInitialArrayWithDecedentOrder_thenCallDeleteMax_correctOrderReturned() {
        final Integer[] integers = {1, 3, 5, 7, 9, 2, 4, 6, 8, 10};
        final PriorityQueue<Integer> pq = new HeapPriorityQueue<>(integers, (o1, o2) -> o2 - o1);
        assertEquals(10, pq.size());
        assertFalse(pq.isEmpty());
        for (int i = 1; i <= 10; i++) {
            assertEquals(i, pq.max());
            assertEquals(i, pq.deleteMax());
        }
    }

}