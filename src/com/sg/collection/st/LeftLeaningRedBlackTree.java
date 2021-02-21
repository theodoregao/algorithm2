package com.sg.collection.st;

import com.sg.collection.queue.LinkedListQueue;
import com.sg.collection.queue.Queue;
import edu.princeton.cs.algs4.StdOut;

/**
 * Left Leaning Red Black Tree implementation. See more details:
 * <br>The @see <a href="https://www.cs.princeton.edu/~rs/talks/LLRB/RedBlack.pdf">Left-Leaning Red-Black Trees</a>
 * <br>The @see <a href="https://www.cs.princeton.edu/~rs/talks/LLRB/LLRB.pdf">Left-leaning Red-Black Trees</a>
 *
 * @param <Key>   Key type for Balanced Binary Search Tree
 * @param <Value> Value type for Balanced Binary Search Tree
 */
public class LeftLeaningRedBlackTree<Key extends Comparable<Key>, Value> implements OrderST<Key, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root;

    public LeftLeaningRedBlackTree() {
        root = null;
    }

    @Override
    public void put(Key key, Value value) {
        if (key == null) return;
        if (value == null) {
            delete(key);
        } else {
            root = put(root, key, value);
            root.color = BLACK;
        }
        assert check();
    }

    @Override
    public Value get(Key key) {
        if (key == null) return null;
        final Node node = get(root, key);
        return node == null ? null : node.value;
    }

    @Override
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException();
        if (!contains(key)) return;
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
        assert check();
    }

    @Override
    public boolean contains(Key key) {
        return key != null && get(root, key) != null;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int size() {
        return size(root);
    }

    @Override
    public Key min() {
        final Node node = min(root);
        return node == null ? null : node.key;
    }

    @Override
    public Key max() {
        final Node node = max(root);
        return node == null ? null : node.key;
    }

    @Override
    public Key floor(Key key) {
        if (key == null) return null;
        final Node node = floor(root, key);
        return node == null ? null : node.key;
    }

    @Override
    public Key ceiling(Key key) {
        if (key == null) return null;
        final Node node = ceiling(root, key);
        return node == null ? null : node.key;
    }

    @Override
    public int rank(Key key) {
        if (key == null) return -1;
        return rank(root, key);
    }

    @Override
    public Key select(int k) {
        if (k < 0 || k >= size(root)) throw new IllegalArgumentException();
        final Node node = select(root, k);
        return node == null ? null : node.key;
    }

    @Override
    public void deleteMin() {
        if (isEmpty()) throw new IllegalStateException();
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
        assert check();
    }

    @Override
    public void deleteMax() {
        if (isEmpty()) throw new IllegalStateException();
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
        assert check();
    }

    @Override
    public int size(Key lo, Key hi) {
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else return rank(hi) - rank(lo);
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        final Queue<Key> queue = new LinkedListQueue<>();
        inOrderIterator(root, queue, lo, hi);
        return queue;
    }

    @Override
    public Iterable<Key> keys() {
        if (size() == 0) return new LinkedListQueue<>();
        return keys(min(), max());
    }


    /***************************************************************************
     *  Internal helper methods.
     ***************************************************************************/

    private Node get(Node parent, Key key) {
        if (parent == null) return null;
        final int compare = key.compareTo(parent.key);
        if (compare == 0) return parent;
        else if (compare < 0) return get(parent.left, key);
        else return get(parent.right, key);
    }

    private Node put(Node parent, Key key, Value value) {
        Node node = parent;
        if (node == null) node = new Node(key, value, RED);
        final int compare = key.compareTo(node.key);
        if (compare == 0) node.value = value;
        else if (compare < 0) node.left = put(node.left, key, value);
        else node.right = put(node.right, key, value);

        return balance(node);
    }

    private int size(Node parent) {
        if (parent == null) return 0;
        return parent.size;
    }

    private Node min(Node parent) {
        if (parent == null) return null;
        else if (parent.left == null) return parent;
        else return min(parent.left);
    }

    private Node max(Node parent) {
        if (parent == null) return null;
        else if (parent.right == null) return parent;
        else return max(parent.right);
    }

    private Node floor(Node parent, Key key) {
        if (parent == null) return null;
        final int compare = key.compareTo(parent.key);
        if (compare == 0) return parent;
        else if (compare < 0) return floor(parent.left, key);
        else {
            final Node rightCandidate = floor(parent.right, key);
            return rightCandidate == null ? parent : rightCandidate;
        }
    }

    private Node ceiling(Node parent, Key key) {
        if (parent == null) return null;
        final int compare = parent.key.compareTo(key);
        if (compare == 0) return parent;
        else if (compare < 0) return ceiling(parent.right, key);
        else {
            final Node leftCandidate = ceiling(parent.left, key);
            return leftCandidate == null ? parent : leftCandidate;
        }
    }

    private int rank(Node parent, Key key) {
        if (parent == null) return 0;
        final int compare = key.compareTo(parent.key);
        if (compare == 0) return size(parent.left);
        else if (compare < 0) return rank(parent.left, key);
        else return 1 + size(parent.left) + rank(parent.right, key);
    }

    private Node select(Node parent, int k) {
        if (parent == null) return null;
        if (size(parent.left) == k) return parent;
        else if (size(parent.left) > k) return select(parent.left, k);
        else return select(parent.right, k - size(parent.left) - 1);
    }

    private Node deleteMin(Node parent) {
        if (parent.left == null) return null;
        if (!isRed(parent.left) && !isRed(parent.left.left)) parent = moveRedLeft(parent);
        parent.left = deleteMin(parent.left);
        return balance(parent);
    }

    private Node deleteMax(Node parent) {
        if (isRed(parent.left)) parent = rotateRight(parent);
        if (parent.right == null) return null;
        if (!isRed(parent.right) && !isRed(parent.right.left)) parent = moveRedRight(parent);
        parent.right = deleteMax(parent.right);
        return balance(parent);
    }

    private Node delete(Node parent, Key key) {
        if (parent == null) return null;
        if (key.compareTo(parent.key) < 0) {
            if (!isRed(parent.left) && !isRed(parent.left.left)) parent = moveRedLeft(parent);
            parent.left = delete(parent.left, key);
        } else {
            if (isRed(parent.left)) parent = rotateRight(parent);
            if (key.compareTo(parent.key) == 0 && parent.right == null) return null;
            if (!isRed(parent.right) && !isRed(parent.right.left)) parent = moveRedRight(parent);
            if (key.compareTo(parent.key) == 0) {
                final Node rightMin = min(parent.right);
                parent.key = rightMin.key;
                parent.value = rightMin.value;
                parent.right = deleteMin(parent.right);
            } else {
                parent.right = delete(parent.right, key);
            }
        }
        return balance(parent);
    }

    private void inOrderIterator(Node parent, Queue<Key> queue, Key lo, Key hi) {
        if (parent == null) return;
        inOrderIterator(parent.left, queue, lo, hi);
        if (lo.compareTo(parent.key) <= 0 && parent.key.compareTo(hi) <= 0) {
            queue.enqueue(parent.key);
        }
        inOrderIterator(parent.right, queue, lo, hi);
    }

    private boolean isRed(Node node) {
        return node != null && node.color;
    }

    /***************************************************************************
     *  Red-black tree helper functions.
     ***************************************************************************/

    /**
     * Rotate the subtree left with the node.
     * Be aware of that the link is red before/after rotation.
     * <pre>
     *     a          b
     *      \R  =>   /R
     *       b      a
     * </pre>
     *
     * @param node original subtree root node
     * @return new subtree root node
     */
    private Node rotateLeft(Node node) {
        assert node != null && isRed(node.right);
        final Node x = node.right;
        node.right = x.left;
        x.left = node;
        x.color = x.left.color;
        x.left.color = RED;
        node.calculateSize();
        x.calculateSize();
        return x;
    }

    /**
     * Rotate the subtree right with the node.
     * Be aware of that the link is red before/after rotation.
     * <pre>
     *     b     a
     *    /R  =>  \R
     *   a         b
     * </pre>
     *
     * @param node original subtree root node
     * @return new subtree root node
     */
    private Node rotateRight(Node node) {
        assert node != null && isRed(node.left);
        Node x = node.left;
        node.left = x.right;
        x.right = node;
        x.color = x.right.color;
        x.right.color = RED;
        node.calculateSize();
        x.calculateSize();
        return x;
    }

    /**
     * Flip color, which actually move the mid node up/down.
     * <pre>
     *     b
     *    / \  <=>  abc
     *   a   c
     * </pre>
     *
     * @param node original subtree root node
     */
    private void flipColor(Node node) {
        assert node != null && node.left != null && node.right != null;
        assert (!isRed(node) && isRed(node.left) && isRed(node.right))
                || (isRed(node) && !isRed(node.left) && !isRed(node.right));
        node.color = !node.color;
        node.left.color = !node.left.color;
        node.right.color = !node.right.color;
    }

    /**
     * Balance the subtree.
     * <pre>
     *                            c
     *      a           b        /R          b          b
     *       \R   =>   /R   =>  b     =>   /R \R  =>  /B \B
     *        b       a        /R         a    c     a    c
     *                        a
     * </pre>
     *
     * @param node original subtree root node
     * @return new subtree root node
     */
    private Node balance(Node node) {
        assert node != null;
        if (isRed(node.right) && !isRed(node.left)) node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right)) flipColor(node);
        node.calculateSize();
        return node;
    }

    /**
     * If right.left is red, move red node from right.left to left
     * <pre>
     *     b          c
     *    / \   =>   / \
     *   a   cd    ab   d
     * </pre>
     * <p>
     * If right.left is black, merge the nodes into a 3-key node
     * <pre>
     *     b
     *    / \   =>  abd
     *   a   d
     * </pre>
     *
     * @param node original subtree root node
     * @return new subtree root node
     */
    private Node moveRedLeft(Node node) {
        assert (node != null);
        assert isRed(node) && !isRed(node.left) && !isRed(node.left.left);
        flipColor(node);
        if (isRed(node.right.left)) {
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
            flipColor(node);
        }
        return node;
    }

    /**
     * If left.left is red, move red node from left.left to right
     * <pre>
     *     c          b
     *    / \   =>   / \
     *  ab   d      a   cd
     * </pre>
     * <p>
     * If left.left is black, merge the nodes into a 3-key node
     * <pre>
     *     b
     *    / \   =>  abd
     *   a   d
     * </pre>
     *
     * @param node original subtree root node
     * @return new subtree root node
     */
    private Node moveRedRight(Node node) {
        assert (node != null);
        assert isRed(node) && !isRed(node.right) && !isRed(node.right.left);
        flipColor(node);
        if (isRed(node.left.left)) {
            node = rotateRight(node);
            flipColor(node);
        }
        return node;
    }

    /***************************************************************************
     *  Check integrity of red-black tree data structure.
     ***************************************************************************/
    private boolean check() {
        if (!isBST()) StdOut.println("Not in symmetric order");
        if (!isSizeConsistent()) StdOut.println("Subtree counts not consistent");
        if (!isRankConsistent()) StdOut.println("Ranks not consistent");
        if (!is23()) StdOut.println("Not a 2-3 tree");
        if (!isBalanced()) StdOut.println("Not balanced");
        return isBST() && isSizeConsistent() && isRankConsistent() && is23() && isBalanced();
    }

    // Does this binary tree satisfy symmetric order?
    // Note: this test also ensures that data structure is a binary tree since order is strict
    private boolean isBST() {
        return isBST(root, null, null);
    }

    // Is the tree rooted at x a BST with all keys strictly between min and max
    // (if min or max is null, treat as empty constraint)
    // Credit: Bob Dondero's elegant solution
    private boolean isBST(Node x, Key min, Key max) {
        if (x == null) return true;
        if (min != null && x.key.compareTo(min) <= 0) return false;
        if (max != null && x.key.compareTo(max) >= 0) return false;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }

    // Are the size fields correct?
    private boolean isSizeConsistent() {
        return isSizeConsistent(root);
    }

    private boolean isSizeConsistent(Node x) {
        if (x == null) return true;
        if (x.size != size(x.left) + size(x.right) + 1) return false;
        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    }

    // Check that ranks are consistent
    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i))) return false;
        for (Key key : keys())
            if (key.compareTo(select(rank(key))) != 0) return false;
        return true;
    }

    // Does the tree have no red right links, and at most one (left)
    // red links in a row on any path?
    private boolean is23() {
        return is23(root);
    }

    private boolean is23(Node x) {
        if (x == null) return true;
        if (isRed(x.right)) return false;
        if (x != root && isRed(x) && isRed(x.left)) return false;
        return is23(x.left) && is23(x.right);
    }

    // Do all paths from root to leaf have same number of black edges?
    private boolean isBalanced() {
        int black = 0;     // number of black links on path from root to min
        Node x = root;
        while (x != null) {
            if (!isRed(x)) black++;
            x = x.left;
        }
        return isBalanced(root, black);
    }

    // Does every path from the root to a leaf have the given number of black links?
    private boolean isBalanced(Node x, int black) {
        if (x == null) return black == 0;
        if (!isRed(x)) black--;
        return isBalanced(x.left, black) && isBalanced(x.right, black);
    }

    private class Node {
        Key key;
        Value value;
        Node left;
        Node right;
        int size;
        boolean color;

        Node(Key key, Value value, boolean color) {
            this.key = key;
            this.value = value;
            this.color = color;
        }

        void calculateSize() {
            size = size(left) + size(right) + 1;
        }
    }
}
