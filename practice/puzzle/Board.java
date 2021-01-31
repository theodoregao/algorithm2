import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Board {

    private static final int[][] DIRS = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};

    private final int[][] tiles;
    private final int n;
    private final int hamming;
    private final int manhattan;
    private int blankR;
    private int blankC;
    private int hashCode = Integer.MIN_VALUE;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this(tiles, -1, -1);
    }

    private Board(int[][] tiles, int blankR, int blankC) {
        if (tiles == null || tiles.length <= 1 || tiles[0].length <= 1) {
            throw new IllegalArgumentException();
        } else {
            n = tiles.length;
            this.tiles = deepCloneTiles(tiles);
            this.blankR = blankR;
            this.blankC = blankC;
            for (int r = 0; r < n && (this.blankR < 0); r++) {
                for (int c = 0; c < n && (this.blankR < 0); c++) {
                    this.tiles[r][c] = tiles[r][c];
                    if (tiles[r][c] == 0) {
                        this.blankR = r;
                        this.blankC = c;
                    }
                }
            }
            hamming = calculateHamming();
            manhattan = calculateManhattan();
        }
    }

    private static int[][] deepCloneTiles(int[][] tiles) {
        final int n = tiles.length;
        final int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copy[i][j] = tiles[i][j];
            }
        }
        return copy;
    }

    private static void exchange(int[][] tiles, int r0, int c0, int r1, int c1) {
        final int temp = tiles[r0][c0];
        tiles[r0][c0] = tiles[r1][c1];
        tiles[r1][c1] = temp;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

    // string representation of this board
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n).append("\n");
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                stringBuilder.append(tiles[r][c]);
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y == this) return true;
        if (!(y instanceof Board)) return false;
        final Board that = (Board) y;
        if (that.n != n) return false;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (tiles[r][c] != that.tiles[r][c]) return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        if (hashCode != Integer.MIN_VALUE) return hashCode;
        int result = Objects.hash(hamming, manhattan, blankR, blankC);
        result = 31 * result + Arrays.deepHashCode(tiles);
        return result;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        final List list = new ArrayList();
        for (int i = 0; i < DIRS.length; i++) {
            final int[][] tiles = deepCloneTiles(this.tiles);
            final int blankR = this.blankR + DIRS[i][0];
            final int blankC = this.blankC + DIRS[i][1];
            if (blankR >= 0 && blankR < n && blankC >= 0 && blankC < n) {
                exchange(tiles, blankR, blankC, this.blankR, this.blankC);
                list.add(new Board(tiles, blankR, blankC));
            }
        }
        return list;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        final int[][] tiles = deepCloneTiles(this.tiles);
        if (tiles[0][0] != 0 && tiles[0][1] != 0) {
            exchange(tiles, 0, 0, 0, 1);
        } else {
            exchange(tiles, 1, 0, 1, 1);
        }
        return new Board(tiles);
    }

    private int calculateHamming() {
        int hamming = 0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (tiles[r][c] == 0) {
                    continue;
                } else {
                    hamming += tiles[r][c] == r * n + c + 1 ? 0 : 1;
                }
            }
        }
        return hamming;
    }

    private int calculateManhattan() {
        int manhattan = 0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (tiles[r][c] == 0) {
                    continue;
                } else {
                    manhattan += Math.abs((tiles[r][c] - 1) / n - r) + Math.abs((tiles[r][c] - 1) % n - c);
                }
            }
        }
        return manhattan;
    }

    int tileAt(int row, int col) {
        return tiles[row][col];
    }
}