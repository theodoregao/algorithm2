package com.sg.collection.queue;

import com.sg.collection.Node;

import java.util.Iterator;

public class LinkedListQueue<Item> implements Queue<Item> {

    private Node<Item> head;
    private Node<Item> tail;

    @Override
    public void enqueue(Item item) {
        final Node<Item> oldTail = tail;
        tail = new Node(item);
        if (oldTail != null) {
            oldTail.next = tail;
        }
        if (head == null) {
            head = tail;
        }
    }

    @Override
    public Item dequeue() {
        if (isEmpty()) throw new IllegalStateException("Queue is empty");
        final Item item = head.item;
        if (head == tail) {
            head = tail = null;
        } else {
            head = head.next;
        }
        return item;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public Iterator<Item> iterator() {
        return new LinkedListQueueIterator();
    }

    private class LinkedListQueueIterator implements Iterator<Item> {

        private Node<Item> currentItem = head;

        @Override
        public boolean hasNext() {
            return currentItem != null;
        }

        @Override
        public Item next() {
            final Item item = currentItem.item;
            currentItem = currentItem.next;
            return item;
        }
    }
}
