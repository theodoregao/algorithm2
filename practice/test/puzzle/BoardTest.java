import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void testConstructor_withInvalidIntArray_exceptionThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Board(null));
        assertThrows(IllegalArgumentException.class, () -> new Board(new int[][]{}));
        assertThrows(IllegalArgumentException.class, () -> new Board(new int[][]{{1}}));
    }

    @Test
    void testConstructedWithValidArray_thenCallHamming_correctValueReturned() {
        final int[][] tiles_0 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        assertEquals(0, new Board(tiles_0).hamming());
        final int[][] tiles_2 = {
                {1, 2, 3},
                {4, 5, 6},
                {8, 7, 0}
        };
        assertEquals(2, new Board(tiles_2).hamming());
        final int[][] tiles_2_2 = {
                {0, 2, 3},
                {4, 1, 6},
                {7, 8, 5}
        };
        assertEquals(2, new Board(tiles_2_2).hamming());
    }

    @Test
    void testConstructedWithValidArray_thenCallManhattan_correctValueReturned() {
        final int[][] tiles_0 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        assertEquals(0, new Board(tiles_0).manhattan());
        final int[][] tiles_2 = {
                {1, 2, 3},
                {4, 5, 6},
                {8, 7, 0}
        };
        assertEquals(2, new Board(tiles_2).manhattan());
        final int[][] tiles_4 = {
                {1, 8, 3},
                {4, 2, 6},
                {7, 5, 0}
        };
        assertEquals(4, new Board(tiles_4).manhattan());
        final int[][] tiles_4_2 = {
                {0, 2, 3},
                {4, 1, 6},
                {7, 8, 5}
        };
        assertEquals(4, new Board(tiles_4_2).manhattan());
    }

    @Test
    void testIsGoal_withDiffCases_correctAnswerReturned() {
        final int[][] goal_2 = {
                {1, 2},
                {3, 0}
        };
        assertTrue(new Board(goal_2).isGoal());
        final int[][] non_goal_2 = {
                {1, 2},
                {0, 3}
        };
        assertFalse(new Board(non_goal_2).isGoal());
        final int[][] goal_3 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        assertTrue(new Board(goal_3).isGoal());
        final int[][] non_goal_3 = {
                {1, 2, 0},
                {4, 5, 6},
                {7, 8, 3}
        };
        assertFalse(new Board(non_goal_3).isGoal());
    }

    @Test
    void testEquals_withDiffCases_correctAnswerReturned() {
        final int[][] tile_2 = {
                {1, 2},
                {3, 0}
        };
        final int[][] tile_3 = {
                {1, 2, 4},
                {3, 0, 6},
                {7, 8, 5}
        };
        final int[][] tile_2_6 = {
                {1, 2, 4},
                {3, 0, 6, 7, 8, 5}
        };
        final Board board_2 = new Board(tile_2);
        assertTrue(board_2.equals(board_2));
        assertTrue(new Board(tile_2).equals(board_2));
        assertFalse(new Board(tile_3).equals(board_2));
        assertTrue(new Board(tile_2_6).equals(board_2));
    }

    @Test
    void testConstructWithDiffTiles_thenCallNeighbors_correctNeighborsReturned() {
        test2TilesNeighbors();
        test3TilesTopLeftNeighbors();
        test3TilesTopNeighbors();
        test3TilesLeftNeighbors();
        test3TilesMiddleNeighbors();
    }

    private void test2TilesNeighbors() {
        final int[][] tile_2 = {
                {1, 2},
                {3, 0}
        };
        final int[][][] tile_2_neighbors = {
                {
                        {1, 0},
                        {3, 2}
                },
                {
                        {1, 2},
                        {0, 3}
                }
        };
        final Board board2 = new Board(tile_2);
        final List<Board> board2Neighbors = StreamSupport.stream(board2.neighbors().spliterator(), false).collect(Collectors.toList());
        final List<Board> expectedBoard2Neighbors = new ArrayList<>();
        expectedBoard2Neighbors.add(new Board(tile_2_neighbors[0]));
        expectedBoard2Neighbors.add(new Board(tile_2_neighbors[1]));
        assertEquals(expectedBoard2Neighbors.size(), board2Neighbors.size());
        for (Board neighbor : board2.neighbors()) {
            boolean isFound = false;
            for (Board expectedNeighbor : expectedBoard2Neighbors) {
                isFound |= neighbor.equals(expectedNeighbor);
            }
            assertTrue(isFound);
        }
    }

    private void test3TilesTopLeftNeighbors() {
        final int[][] tile_3_top_left = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8}
        };
        final int[][][] tile_3_top_left_neighbors = {
                {
                        {1, 0, 2},
                        {3, 4, 5},
                        {6, 7, 8}
                },
                {
                        {3, 1, 2},
                        {0, 4, 5},
                        {6, 7, 8}
                }
        };
        final Board board3TilesTopLeft = new Board(tile_3_top_left);
        final List<Board> board3TilesTopLeftNeighbors = StreamSupport.stream(board3TilesTopLeft.neighbors().spliterator(), false).collect(Collectors.toList());
        final List<Board> expectedBoard2TopLeftNeighbors = new ArrayList<>();
        expectedBoard2TopLeftNeighbors.add(new Board(tile_3_top_left_neighbors[0]));
        expectedBoard2TopLeftNeighbors.add(new Board(tile_3_top_left_neighbors[1]));
        assertEquals(expectedBoard2TopLeftNeighbors.size(), board3TilesTopLeftNeighbors.size());
        for (Board neighbor : board3TilesTopLeft.neighbors()) {
            boolean isFound = false;
            for (Board expectedNeighbor : expectedBoard2TopLeftNeighbors) {
                isFound |= neighbor.equals(expectedNeighbor);
            }
            assertTrue(isFound);
        }
    }

    void test3TilesTopNeighbors() {
        final int[][] tile_3_top = {
                {1, 0, 2},
                {3, 4, 5},
                {6, 7, 8}
        };
        final int[][][] tile_3_top_neighbors = {
                {
                        {0, 1, 2},
                        {3, 4, 5},
                        {6, 7, 8}
                },
                {
                        {1, 2, 0},
                        {3, 4, 5},
                        {6, 7, 8}
                },
                {
                        {1, 4, 2},
                        {3, 0, 5},
                        {6, 7, 8}
                }
        };
        final Board board3Top = new Board(tile_3_top);
        final List<Board> board3TilesTopNeighbors = StreamSupport.stream(board3Top.neighbors().spliterator(), false).collect(Collectors.toList());
        final List<Board> expectedBoard3TilesTopNeighbors = new ArrayList<>();
        expectedBoard3TilesTopNeighbors.add(new Board(tile_3_top_neighbors[0]));
        expectedBoard3TilesTopNeighbors.add(new Board(tile_3_top_neighbors[1]));
        expectedBoard3TilesTopNeighbors.add(new Board(tile_3_top_neighbors[2]));
        assertEquals(expectedBoard3TilesTopNeighbors.size(), board3TilesTopNeighbors.size());
        for (Board neighbor : board3Top.neighbors()) {
            boolean isFound = false;
            for (Board expectedNeighbor : expectedBoard3TilesTopNeighbors) {
                isFound |= neighbor.equals(expectedNeighbor);
            }
            assertTrue(isFound);
        }
    }

    void test3TilesLeftNeighbors() {
        final int[][] tile_3_left = {
                {1, 0, 2},
                {3, 4, 5},
                {6, 7, 8}
        };
        final int[][][] tile_3_left_neighbors = {
                {
                        {0, 1, 2},
                        {3, 4, 5},
                        {6, 7, 8}
                },
                {
                        {1, 2, 0},
                        {3, 4, 5},
                        {6, 7, 8}
                },
                {
                        {1, 4, 2},
                        {3, 0, 5},
                        {6, 7, 8}
                }
        };
        final Board board3Left = new Board(tile_3_left);
        final List<Board> board3LeftNeighbors = StreamSupport.stream(board3Left.neighbors().spliterator(), false).collect(Collectors.toList());
        final List<Board> expectedBoard3LeftNeighbors = new ArrayList<>();
        expectedBoard3LeftNeighbors.add(new Board(tile_3_left_neighbors[0]));
        expectedBoard3LeftNeighbors.add(new Board(tile_3_left_neighbors[1]));
        expectedBoard3LeftNeighbors.add(new Board(tile_3_left_neighbors[2]));
        assertEquals(expectedBoard3LeftNeighbors.size(), board3LeftNeighbors.size());
        for (Board neighbor : board3Left.neighbors()) {
            boolean isFound = false;
            for (Board expectedNeighbor : expectedBoard3LeftNeighbors) {
                isFound |= neighbor.equals(expectedNeighbor);
            }
            assertTrue(isFound);
        }
    }

    void test3TilesMiddleNeighbors() {
        final int[][] tile_3_middle = {
                {1, 2, 3},
                {4, 0, 5},
                {6, 7, 8}
        };
        final int[][][] tile_3_middle_neighbors = {
                {
                        {1, 0, 3},
                        {4, 2, 5},
                        {6, 7, 8}
                },
                {
                        {1, 2, 3},
                        {0, 4, 5},
                        {6, 7, 8}
                },
                {
                        {1, 2, 3},
                        {4, 5, 0},
                        {6, 7, 8}
                },
                {
                        {1, 2, 3},
                        {4, 7, 5},
                        {6, 0, 8}
                }
        };
        final Board board3Middle = new Board(tile_3_middle);
        final List<Board> board3MiddleNeighbors = StreamSupport.stream(board3Middle.neighbors().spliterator(), false).collect(Collectors.toList());
        final List<Board> expectedBoard3LeftNeighbors = new ArrayList<>();
        expectedBoard3LeftNeighbors.add(new Board(tile_3_middle_neighbors[0]));
        expectedBoard3LeftNeighbors.add(new Board(tile_3_middle_neighbors[1]));
        expectedBoard3LeftNeighbors.add(new Board(tile_3_middle_neighbors[2]));
        expectedBoard3LeftNeighbors.add(new Board(tile_3_middle_neighbors[3]));
        assertEquals(expectedBoard3LeftNeighbors.size(), board3MiddleNeighbors.size());
        for (Board neighbor : board3Middle.neighbors()) {
            boolean isFound = false;
            for (Board expectedNeighbor : expectedBoard3LeftNeighbors) {
                isFound |= neighbor.equals(expectedNeighbor);
            }
            assertTrue(isFound);
        }
    }

}