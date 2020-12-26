package com.sg.collection;

public class Node<Item> {
    public Item item;
    public Node next;

    public Node() {
        this(null);
    }

    public Node(Item item) {
        this(item, null);
    }

    public Node(Item item, Node next) {
        this.item = item;
        this.next = next;
    }
}
