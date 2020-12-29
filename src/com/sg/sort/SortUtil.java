package com.sg.sort;

import java.util.Random;

public class SortUtil {

    public static <Item> void swap(Item[] items, int i, int j) {
        if (i < 0 || j < 0 || i >= items.length || j >= items.length) {
            throw new IllegalArgumentException("invalid index");
        }
        final Item item = items[i];
        items[i] = items[j];
        items[j] = item;
    }

    public static <Item extends Comparable<Item>> boolean less(Item first, Item second) {
        return first.compareTo(second) == -1;
    }

    public static <Item extends Comparable<Item>> boolean isSorted(Item[] items) {
        for (int i = 1; i < items.length; i++) {
            if (less(items[i], items[i - 1])) {
                return false;
            }
        }
        return true;
    }

    public static <Item> void shuffle(Item[] items) {
        if (items == null || items.length == 0) {
            return;
        }
        final Random random = new Random();
        for (int i = 0; i < items.length; i++) {
            swap(items, i, i + random.nextInt(items.length - i));
        }
    }

}
