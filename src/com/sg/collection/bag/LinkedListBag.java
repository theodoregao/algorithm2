package com.sg.collection.bag;

import com.sg.collection.Node;

import java.util.Iterator;

public class LinkedListBag<Item> implements Bag<Item> {

    private Node<Item> first;
    private int size;

    @Override
    public void add(Item item) {
        first = new Node(item, first);
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Item> iterator() {
        return new LinkedListBagIterator();
    }

    private class LinkedListBagIterator implements Iterator<Item> {

        Node<Item> currentItem = first;

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
