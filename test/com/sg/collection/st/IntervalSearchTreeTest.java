package com.sg.collection.st;

import com.sg.collection.st.IntervalSearchTree.Interval;
import com.sg.sort.SortUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntervalSearchTreeTest {

    private IntervalSearchTree<Integer, Interval<Integer>> intervalSearchTree;

    @BeforeEach
    void setUp() {
        intervalSearchTree = new IntervalSearchTree<>();
    }

    @Test
    void basic_test() {
        final Interval<Integer> interval_1_2 = new Interval<>(1, 2);
        final Interval<Integer> interval_1_3 = new Interval<>(1, 3);
        final Interval<Integer> interval_2_4 = new Interval<>(2, 4);
        final Interval<Integer> interval_3_5 = new Interval<>(3, 5);
        assertTrue(intervalSearchTree.isEmpty());
        intervalSearchTree.put(interval_1_2, interval_1_2);
        assertFalse(intervalSearchTree.isEmpty());
        intervalSearchTree.put(interval_1_3, interval_1_3);
        intervalSearchTree.put(interval_2_4, interval_2_4);
        intervalSearchTree.put(interval_3_5, interval_3_5);

        assertNull(intervalSearchTree.get(new Interval<>(1, 5)));
        assertTrue(intervalSearchTree.contains(new Interval<>(1, 2)));
        assertEquals(interval_1_2, intervalSearchTree.get(new Interval<>(1, 2)));
        assertTrue(intervalSearchTree.contains(new Interval<>(1, 3)));
        assertEquals(interval_1_3, intervalSearchTree.get(new Interval<>(1, 3)));
        assertTrue(intervalSearchTree.contains(new Interval<>(2, 4)));
        assertEquals(interval_2_4, intervalSearchTree.get(new Interval<>(2, 4)));
        assertTrue(intervalSearchTree.contains(new Interval<>(3, 5)));
        assertEquals(interval_3_5, intervalSearchTree.get(new Interval<>(3, 5)));
        assertEquals(interval_3_5, intervalSearchTree.anyIntersects(5, 6));

        intervalSearchTree.delete(interval_1_2);
        intervalSearchTree.delete(interval_3_5);
        assertFalse(intervalSearchTree.contains(new Interval<>(1, 2)));
        assertNull(intervalSearchTree.get(new Interval<>(1, 2)));
        assertTrue(intervalSearchTree.contains(new Interval<>(1, 3)));
        assertEquals(interval_1_3, intervalSearchTree.get(new Interval<>(1, 3)));
        assertTrue(intervalSearchTree.contains(new Interval<>(2, 4)));
        assertEquals(interval_2_4, intervalSearchTree.get(new Interval<>(2, 4)));
        assertFalse(intervalSearchTree.contains(new Interval<>(3, 5)));
        assertNull(intervalSearchTree.get(new Interval<>(3, 5)));

        intervalSearchTree.delete(interval_1_3);
        intervalSearchTree.delete(interval_2_4);
        assertFalse(intervalSearchTree.contains(new Interval<>(1, 2)));
        assertNull(intervalSearchTree.get(new Interval<>(1, 2)));
        assertFalse(intervalSearchTree.contains(new Interval<>(1, 3)));
        assertNull(intervalSearchTree.get(new Interval<>(1, 3)));
        assertFalse(intervalSearchTree.contains(new Interval<>(2, 4)));
        assertNull(intervalSearchTree.get(new Interval<>(2, 4)));
        assertFalse(intervalSearchTree.contains(new Interval<>(3, 5)));
        assertNull(intervalSearchTree.get(new Interval<>(3, 5)));
    }

    @Test
    void test_with1000Items_thenCorrectResultReturned() {
        final Interval<Integer>[] intervals = new Interval[1000];
        final Interval<Integer> interval = new Interval<>(-1, 5);
        final Interval<Integer> interval2 = new Interval<>(555, 666);
        int count = 0;
        int count2 = 0;
        for (int i = 0; i < 1000; i++) {
            intervals[i] = new Interval<>(i % 10, i);
            intervalSearchTree.put(intervals[i], intervals[i]);
            if (interval.intersects(intervals[i])) count++;
            if (interval2.intersects(intervals[i])) count2++;
        }
        for (Object i : intervalSearchTree.intersects(interval)) count--;
        for (Object i : intervalSearchTree.intersects(interval2)) count2--;
        for (Object i : intervalSearchTree.intersects(interval))
            intervalSearchTree.delete((IntervalSearchTree.Interval) i);
        for (Object i : intervalSearchTree.intersects(interval2))
            intervalSearchTree.delete((IntervalSearchTree.Interval) i);
        assertEquals(0, count);
        assertEquals(0, count2);
    }

    @Test
    void test_rotates() {
        for (int i = 10; i > 0; i--) {
            intervalSearchTree.put(new Interval<>(i, i), new Interval<>(i, i));
        }
        intervalSearchTree.delete(new Interval<>(1, 1));
        intervalSearchTree.delete(new Interval<>(2, 2));
        intervalSearchTree.delete(new Interval<>(3, 3));
    }

    @Test
    void test_buckInsertAndDelete_theCorrectResultReturned() {
        final int SIZE = 1000;
        final Interval<Integer>[] intervals = new Interval[SIZE];
        final Integer[] indexs = new Integer[SIZE];
        final Integer[] indexs2 = new Integer[SIZE];
        for (int i = 0; i < SIZE; i++) {
            indexs[i] = i;
            indexs2[i] = i;
            intervals[i] = new Interval<>(i % 10, i);
            intervalSearchTree.put(intervals[i], intervals[i]);
        }
        SortUtil.shuffle(indexs);
        SortUtil.shuffle(indexs2);
        for (int i = 0; i < SIZE; i++) {
//            System.out.println("interval -> (" + intervals[i].lo + ", " + intervals[i].hi + ")");
//            System.out.println("index: " + indexs[i] + ", " + indexs2[i]);
            intervalSearchTree.delete(intervals[i]);
        }
    }
}