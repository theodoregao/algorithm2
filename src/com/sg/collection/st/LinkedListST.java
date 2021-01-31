package com.sg.collection.st;

import com.sg.collection.queue.LinkedListQueue;
import com.sg.collection.queue.Queue;

public class LinkedListST<Key, Value> implements ST<Key, Value> {

    private Node head;
    private int size;

    public LinkedListST() {
        head = null;
    }

    @Override
    public void put(Key key, Value value) {
        if (key == null) return;
        if (value == null) {
            delete(key);
            return;
        }
        final Node node = getNode(key);
        if (node == null) {
            head = new Node(key, value, head);
            size++;
        } else {
            node.value = value;
        }
    }

    @Override
    public Value get(Key key) {
        final Node node = getNode(key);
        return node == null ? null : node.value;
    }

    @Override
    public void delete(Key key) {
        if (key == null) return;
        Node node = head;
        Node previousNode = null;
        while (node != null) {
            if (node.key.equals(key)) {
                if (previousNode == null) {
                    head = head.next;
                } else {
                    previousNode.next = node.next;
                }
                size--;
                return;
            } else {
                previousNode = node;
                node = node.next;
            }
        }
    }

    @Override
    public boolean contains(Key key) {
        return getNode(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterable<Key> keys() {
        final Queue<Key> queue = new LinkedListQueue<>();
        Node node = head;
        while (node != null) {
            queue.enqueue(node.key);
            node = node.next;
        }
        return queue;
    }

    private Node getNode(Key key) {
        if (key == null) return null;
        Node node = head;
        while (node != null) {
            if (node.key.equals(key)) return node;
            else node = node.next;
        }
        return null;
    }

    private class Node {
        final Key key;
        Value value;
        Node next;

        Node(Key key, Value value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
