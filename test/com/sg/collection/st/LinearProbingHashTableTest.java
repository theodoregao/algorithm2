package com.sg.collection.st;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinearProbingHashTableTest {

    private ST<Character, Integer> st;

    @BeforeEach
    void setUp() {
        st = new LinearProbingHashTable<>();
    }

    @Test
    void testFillWithItemsAndDeleteThem_thenCallIsEmpty_correctResultReturned() {
        assertTrue(st.isEmpty());
        st.put('a', 1);
        assertFalse(st.isEmpty());
        st.delete('a');
        assertTrue(st.isEmpty());

        st.put('a', 1);
        assertFalse(st.isEmpty());
        st.put('b', 2);
        assertFalse(st.isEmpty());
        st.put('a', 3);
        assertFalse(st.isEmpty());
        st.delete('b');
        assertFalse(st.isEmpty());
        st.delete('a');
        assertTrue(st.isEmpty());

        st.put(null, 1);
        assertTrue(st.isEmpty());

        st.put('a', 1);
        assertFalse(st.isEmpty());
        st.put('a', null);
        assertTrue(st.isEmpty());
    }

    @Test
    void testFillWithItemsAndDeleteThem_thenCallContains_correctResultReturned() {
        assertFalse(st.contains(null));
        assertFalse(st.contains('a'));
        st.put('a', 1);
        assertTrue(st.contains('a'));
        assertFalse(st.contains(null));
        st.delete('a');
        assertFalse(st.contains('a'));

        st.put('a', 1);
        assertTrue(st.contains('a'));
        st.put('b', 2);
        assertTrue(st.contains('b'));
        st.put('a', 3);
        assertTrue(st.contains('a'));
        st.delete('b');
        assertFalse(st.contains('b'));
        st.delete('a');
        assertFalse(st.contains('a'));

        st.put(null, 1);
        assertFalse(st.contains(null));

        st.put('a', 1);
        assertTrue(st.contains('a'));
        st.put('a', null);
        assertFalse(st.contains('a'));
    }

    @Test
    void testFillWithItemsAndDeleteThem_thenCallSize_correctResultReturned() {
        assertEquals(0, st.size());
        st.put(null, 1);
        assertEquals(0, st.size());

        st.put('a', 1);
        assertEquals(1, st.size());
        st.put('b', 2);
        assertEquals(2, st.size());
        st.put('a', 0);
        assertEquals(2, st.size());
        st.put(null, 3);
        assertEquals(2, st.size());
        st.put('a', null);
        assertEquals(1, st.size());
        st.delete('a');
        assertEquals(1, st.size());
        st.delete('b');
        assertEquals(0, st.size());
    }

    @Test
    void testWithGetPut_thenCorrectValueReturned() {
        assertNull(st.get('a'));
        st.put('a', 1);
        assertEquals(1, st.get('a'));
        assertNull(st.get('b'));
        assertNull(st.get(null));

        st.put('b', 2);
        assertEquals(2, st.get('b'));

        st.put('a', 3);
        assertEquals(3, st.get('a'));

        st.put('b', null);
        assertNull(st.get('b'));
        assertEquals(3, st.get('a'));

        st.delete('a');
        assertNull(st.get('a'));
    }

    @Test
    void testInputWithCharacters_thenIterateWithAllKeys_correctOccurrenceCountReturned() {
        final String str = "I love China, and I came to United State 10 years ago. Now we are missing home!";
        for (char ch : str.toCharArray()) {
            final char key = Character.toLowerCase(ch);
            if (st.contains(key)) {
                st.put(key, st.get(key) + 1);
            } else {
                st.put(key, 1);
            }
        }
        assertEquals(16, st.get(' '));
        assertEquals(6, st.get('i'));

        int size = 0;
        for (Character ch : st.keys()) {
            size++;
        }
        assertEquals(st.size(), size);
    }

    @Test
    void testAddDeleteItems_causeResize_correctResultReturned() {
        int i = 0;
        for (char ch = 0; ch < 256; ch++) {
            st.put(ch, (int) ch);
            assertEquals(++i, st.size());
        }
        for (char ch = 0; ch < 256; ch++) {
            st.delete(ch);
            assertEquals(--i, st.size());
        }
        for (char ch = 0; ch < 256; ch++) {
            st.put(ch, (int) ch);
            assertEquals(++i, st.size());
        }
        for (char ch = 0; ch < 256; ch++) {
            st.delete(ch);
            assertEquals(--i, st.size());
        }
    }
}