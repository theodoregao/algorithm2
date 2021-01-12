package com.sg.collection.priorityqueue;

public interface PriorityQueue<Item extends Comparable<Item>> {
    void insert(Item item);
    Item max();
    Item deleteMax();
    boolean isEmpty();
    int size();
}
