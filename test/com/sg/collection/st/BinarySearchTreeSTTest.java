package com.sg.collection.st;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTreeSTTest {

    private OrderST<Character, Integer> st;

    @BeforeEach
    void setUp() {
        st = new BinarySearchTreeST<>();
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
    void testPutDifferentValues_thenCallMinMaxFloorCeilingRankSelect_thenCorrectValuesReturned() {
        assertNull(st.min());
        assertNull(st.max());
        assertNull(st.floor(null));
        assertNull(st.floor('\255'));
        assertNull(st.ceiling(null));
        assertNull(st.ceiling('\0'));
        assertEquals(-1, st.rank(null));
        assertEquals(0, st.rank('\0'));
        assertEquals(0, st.rank('\255'));
        assertThrows(IllegalArgumentException.class, () -> {
            st.select(-1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            st.select(0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            st.select(1);
        });

        st.put('b', 1);
        assertEquals('b', st.min());
        assertEquals('b', st.max());
        assertNull(st.floor(null));
        assertNull(st.floor('a'));
        assertEquals('b', st.floor('b'));
        assertEquals('b', st.floor('c'));
        assertEquals('b', st.floor('x'));
        assertEquals('b', st.floor('y'));
        assertEquals('b', st.floor('z'));
        assertEquals('b', st.floor('\255'));
        assertNull(st.ceiling(null));
        assertEquals('b', st.ceiling('\0'));
        assertEquals('b', st.ceiling('a'));
        assertEquals('b', st.ceiling('b'));
        assertNull(st.ceiling('c'));
        assertNull(st.ceiling('x'));
        assertNull(st.ceiling('y'));
        assertNull(st.ceiling('\255'));
        assertEquals(-1, st.rank(null));
        assertEquals(0, st.rank('\0'));
        assertEquals(0, st.rank('a'));
        assertEquals(0, st.rank('b'));
        assertEquals(1, st.rank('c'));
        assertEquals(1, st.rank('x'));
        assertEquals(1, st.rank('y'));
        assertEquals(1, st.rank('z'));
        assertEquals(1, st.rank('\255'));
        assertThrows(IllegalArgumentException.class, () -> {
            st.select(-1);
        });
        assertEquals('b', st.select(0));
        assertThrows(IllegalArgumentException.class, () -> {
            st.select(1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            st.select(2);
        });

        st.put('y', 10);
        assertEquals('b', st.min());
        assertEquals('y', st.max());
        assertNull(st.floor(null));
        assertNull(st.floor('a'));
        assertEquals('b', st.floor('b'));
        assertEquals('b', st.floor('c'));
        assertEquals('b', st.floor('x'));
        assertEquals('y', st.floor('y'));
        assertEquals('y', st.floor('z'));
        assertEquals('y', st.floor('\255'));
        assertNull(st.ceiling(null));
        assertEquals('b', st.ceiling('\0'));
        assertEquals('b', st.ceiling('a'));
        assertEquals('b', st.ceiling('b'));
        assertEquals('y', st.ceiling('c'));
        assertEquals('y', st.ceiling('x'));
        assertEquals('y', st.ceiling('y'));
        assertNull(st.ceiling('\255'));
        assertEquals(-1, st.rank(null));
        assertEquals(0, st.rank('\0'));
        assertEquals(0, st.rank('a'));
        assertEquals(0, st.rank('b'));
        assertEquals(1, st.rank('c'));
        assertEquals(1, st.rank('x'));
        assertEquals(1, st.rank('y'));
        assertEquals(2, st.rank('z'));
        assertEquals(2, st.rank('\255'));
        assertThrows(IllegalArgumentException.class, () -> {
            st.select(-1);
        });
        assertEquals('b', st.select(0));
        assertEquals('y', st.select(1));
        assertThrows(IllegalArgumentException.class, () -> {
            st.select(2);
        });

        st.delete('y');
        assertEquals('b', st.min());
        assertEquals('b', st.max());
        assertNull(st.floor(null));
        assertNull(st.floor('a'));
        assertEquals('b', st.floor('b'));
        assertEquals('b', st.floor('c'));
        assertEquals('b', st.floor('x'));
        assertEquals('b', st.floor('y'));
        assertEquals('b', st.floor('z'));
        assertEquals('b', st.floor('\255'));
        assertNull(st.ceiling(null));
        assertEquals('b', st.ceiling('\0'));
        assertEquals('b', st.ceiling('a'));
        assertEquals('b', st.ceiling('b'));
        assertNull(st.ceiling('c'));
        assertNull(st.ceiling('x'));
        assertNull(st.ceiling('y'));
        assertNull(st.ceiling('\255'));
        assertEquals(-1, st.rank(null));
        assertEquals(0, st.rank('\0'));
        assertEquals(0, st.rank('a'));
        assertEquals(0, st.rank('b'));
        assertEquals(1, st.rank('c'));
        assertEquals(1, st.rank('x'));
        assertEquals(1, st.rank('y'));
        assertEquals(1, st.rank('z'));
        assertEquals(1, st.rank('\255'));
        assertThrows(IllegalArgumentException.class, () -> {
            st.select(-1);
        });
        assertEquals('b', st.select(0));
        assertThrows(IllegalArgumentException.class, () -> {
            st.select(1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            st.select(2);
        });
    }

    @Test
    void testPutDifferentValues_thenCallDeleteMinDeleteMax_thenCorrectValuesReturned() {
        final String randomAtoZ = "eaiugdqsctyowvhbzxnmkflrpj";
        for (char ch : randomAtoZ.toCharArray()) {
            st.put(ch, (int) ch);
        }
        assertEquals('a', st.min());
        assertEquals('z', st.max());

        st.deleteMin();
        assertEquals('b', st.min());

        st.deleteMax();
        assertEquals('y', st.max());

        st.deleteMin();
        assertEquals('c', st.min());

        st.deleteMax();
        assertEquals('x', st.max());

        st.deleteMin();
        assertEquals('d', st.min());

        st.deleteMax();
        assertEquals('w', st.max());

        st.deleteMin();
        assertEquals('e', st.min());

        st.deleteMax();
        assertEquals('v', st.max());

        st.deleteMin();
        assertEquals('f', st.min());

        st.deleteMax();
        assertEquals('u', st.max());

        st.deleteMin();
        assertEquals('g', st.min());

        st.deleteMax();
        assertEquals('t', st.max());
    }

    @Test
    void testPutDifferentValues_thenCallKeys_correctValuesReturned() {
        for (char ch = 'a'; ch <= 'z'; ch++) {
            st.put(ch, (int) ch);
        }

        final char startCh = 'f';
        final char endCh = 'k';
        int index = 0;
        for (char ch : st.keys(startCh, endCh)) {
            assertEquals(startCh + index++, ch);
        }
        assertEquals(6, index);
        assertEquals(10, st.size('f', 'o'));
    }

    @Test
    void testDeleteLogicWithDifferentCases() {
        st.put('g', 0);
        st.put('o', 0);
        st.put('l', 0);
        st.delete('o');
        assertEquals('g', st.min());
        assertEquals('l', st.max());

        st.put('a', 0);
        st.delete('g');
        assertEquals('a', st.min());
    }

}