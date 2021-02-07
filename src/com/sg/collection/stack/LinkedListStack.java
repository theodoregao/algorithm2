package com.sg.collection.stack;

import com.sg.collection.Node;

import java.util.Iterator;

public class LinkedListStack<Item> implements Stack<Item> {

    private Node<Item> top;

    @Override
    public void push(Item item) {
        top = new Node(item, top);
    }

    @Override
    public Item pop() {
        if (isEmpty()) throw new IllegalStateException("Stack is empty");
        final Item item = top.item;
        top = top.next;
        return item;
    }

    @Override
    public boolean isEmpty() {
        return top == null;
    }

    @Override
    public Iterator<Item> iterator() {
        return new LinkedListStackIterator();
    }

    private class LinkedListStackIterator implements Iterator<Item> {

        private Node<Item> currentNode = top;

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public Item next() {
            final Item item = currentNode.item;
            currentNode = currentNode.next;
            return item;
        }
    }
}
