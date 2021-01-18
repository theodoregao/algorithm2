import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;

public class Solver {

    private static final int BACKWARD_COST = Integer.MAX_VALUE / 2;
    private static final SearchNode UNSOLVABLE_SEARCH_NODE = new SearchNode(null, null, Integer.MAX_VALUE, false);
    private static final Comparator<SearchNode> HAMMING_COMPARATOR = new Comparator<SearchNode>() {
        @Override
        public int compare(SearchNode thiz, SearchNode that) {
            return thiz.cost + thiz.board.hamming() - that.cost - that.board.hamming();
        }
    };
    private static final Comparator<SearchNode> MANHATTAN_COMPARATOR = new Comparator<SearchNode>() {
        @Override
        public int compare(SearchNode thiz, SearchNode that) {
            return thiz.cost + thiz.board.manhattan() - that.cost - that.board.manhattan();
        }
    };
    private static final Comparator<SearchNode> NAIVE_COMPARATOR = new Comparator<SearchNode>() {
        @Override
        public int compare(SearchNode thiz, SearchNode that) {
            return thiz.cost - that.cost;
        }
    };
    private final SearchNode solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        final Board twin = initial.twin();
        final List<Board> history = new ArrayList<>();
        final List<Board> twinHistory = new ArrayList<>();
        final Set<Board> historySet = new HashSet<>();
        final Set<Board> twinHistorySet = new HashSet<>();
        final MinPQ<SearchNode> priorityQueue = new MinPQ<>(MANHATTAN_COMPARATOR);
        final MinPQ<SearchNode> twinPriorityQueue = new MinPQ<>(MANHATTAN_COMPARATOR);
        history.add(initial);
        twinHistory.add(twin);
        priorityQueue.insert(new SearchNode(null, initial, 0, null));
        twinPriorityQueue.insert(new SearchNode(null, twin, 0, null));
        while (!priorityQueue.isEmpty()) {
            final SearchNode next = nextStep(priorityQueue, twin, history, historySet);
            if (next != null) {
                solution = next;
                return;
            }
            final SearchNode twinNext = nextStep(twinPriorityQueue, initial, twinHistory, twinHistorySet);
            if (twinNext != null && twinNext.solvable) {
                solution = null;
                return;
            }
        }
        solution = null;
    }

    // test client (see below)
    public static void main(String[] args) {

    }

    private static int ignoreCount = 0;
    private static SearchNode nextStep(MinPQ<SearchNode> priorityQueue, Board twin, List<Board> history, Set<Board> set) {
        if (priorityQueue.isEmpty()) return UNSOLVABLE_SEARCH_NODE;
        final SearchNode searchNode = priorityQueue.delMin();
        if (searchNode.cost > BACKWARD_COST) {
            return UNSOLVABLE_SEARCH_NODE;
        }
        for (Board board : searchNode.board.neighbors()) {
            if (board.equals(twin)) {
                return UNSOLVABLE_SEARCH_NODE;
            } else if (board.isGoal()) {
                StdOut.println("get goal");
                return new SearchNode(searchNode, board, searchNode.cost + 1, true);
            } else if (!set.contains(board)/*!isInHistory(history, board)/**/) {
                StdOut.println(ignoreCount + " / " + history.size() + " : add new " + searchNode.cost + " + " + board.manhattan() + " = " + (searchNode.cost + board.manhattan()));
                history.add(board);
                set.add(board);
                priorityQueue.insert(new SearchNode(searchNode, board, searchNode.cost + 1, null));
            } else {
                ignoreCount++;
            }
        }
        return null;
    }

    private static boolean isInHistory(List<Board> history, Board board) {
        for (Board oldBoard : history) {
            if (oldBoard.equals(board)) {
                return true;
            }
        }
        return false;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solution != null && solution.solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solution != null ? solution.cost : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (solution == null) return null;
        SearchNode searchNode = solution;
        final Stack<Board> stack = new Stack<>();
        while (searchNode != null) {
            stack.push(searchNode.board);
            searchNode = searchNode.previousSearchNode;
        }
        final List<Board> list = new ArrayList<>();
        while (!stack.isEmpty()) {
            list.add(stack.pop());
        }
        return list;
    }

    private static class SearchNode {
        SearchNode previousSearchNode;
        Board board;
        int cost;
        Boolean solvable;

        SearchNode(SearchNode previousSearchNode, Board board, int cost, Boolean solvable) {
            this.previousSearchNode = previousSearchNode;
            this.board = board;
            this.cost = cost;
            this.solvable = solvable;
        }
    }

}