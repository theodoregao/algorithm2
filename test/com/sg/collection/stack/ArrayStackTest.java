package com.sg.collection.stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

}