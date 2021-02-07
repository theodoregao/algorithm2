package com.sg.sort;

import java.util.Comparator;
import java.util.Random;

public class SortUtil {

    public static <Item> void swap(Item[] items, int i, int j) {
        final Item item = items[i];
        items[i] = items[j];
        items[j] = item;
    }

    public static boolean less(Comparable first, Comparable second) {
        return first.compareTo(second) < 0;
    }

    public static boolean isSorted(Comparable[] items) {
        for (int i = 1; i < items.length; i++) {
            if (less(items[i], items[i - 1])) {
                return false;
            }
        }
        return true;
    }

    public static <Item> boolean isSorted(Item[] items, Comparator<Item> comparator) {
        for (int i = 1; i < items.length; i++) {
            if (comparator.compare(items[i], items[i - 1]) < 0) {
                return false;
            }
        }
        return true;
    }

    public static void shuffle(Comparable[] items) {
        final Random random = new Random();
        for (int i = 0; i < items.length; i++) {
            swap(items, i, i + random.nextInt(items.length - i));
        }
    }

}
