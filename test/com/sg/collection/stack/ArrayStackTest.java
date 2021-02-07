package com.sg.collection.stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class ArrayStackTest {

    private Stack<String> stack;

    @BeforeEach
    void setUp() {
        stack = new ArrayStack<>();
    }

    @Test
    void pushAndPopItemsToStack_thenCallIsEmpty_correctValueReturned() {
        assertTrue(stack.isEmpty());
        stack.push("Shun");
        assertFalse(stack.isEmpty());
        stack.push("Xia");
        assertFalse(stack.isEmpty());
        stack.pop();
        assertFalse(stack.isEmpty());
        stack.pop();
        assertTrue(stack.isEmpty());
    }

    @Test
    void pushSomeItemsToStack_thenCallPop_correctItemsReturned() {
        stack.push("Shun");
        stack.push("Xia");
        assertEquals("Xia", stack.pop());
        assertEquals("Shun", stack.pop());
    }

    @Test
    void stackIsEmpty_thenCallPop_throwException() {
        assertThrows(IllegalStateException.class, () -> stack.pop());
        stack.push("Shun");
        stack.pop();
        assertThrows(IllegalStateException.class, () -> stack.pop());
    }

    @Test
    void pushItemsToStack_thenCallPop_monitorResizeGetCalled() {
        for (int i = 0; i < 100; i++) {
            stack.push(Integer.toString(i));
        }
        Iterator it = stack.iterator();
        int count = 100;
        while (it.hasNext()) {
            assertEquals(Integer.toString(--count), it.next());
        }

        for (int i = 0; i < 90; i++) {
            stack.pop();
        }
        it = stack.iterator();
        count = 10;
        while (it.hasNext()) {
            assertEquals(Integer.toString(--count), it.next());
        }
    }

}