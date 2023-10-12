import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {
    private int[][] board; // n-by-n array of tiles
    private int N;         // board dimension

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        int n = tiles[0].length;
        this.N = n;
        this.board = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.board[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return N;
    }

    // number of tiles out of place
    public int hamming() {
        int check = 0;
        int dist = 0;
        for (int i = 0; i < board[0].length; i++) {
            for (int j = 0; j < board[1].length; j++) {
                check += 1;
                if (board[i][j] == 0) continue;
                if (board[i][j] != check) dist += 1;
            }
        }
        return dist;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int dist = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int x = board[i][j];
                if (x == 0) continue;

                // correct position
                int col;
                if (x % N == 0) {
                    col = N - 1;
                }
                else {
                    col = Math.max(x % N - 1, 0);
                }
                double row = Math.ceil((double) x / N) - 1;
                dist = dist + (int) Math.abs(i - row) + Math.abs(j - col);
            }
        }
        return dist;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < board[0].length; i++) {
            for (int j = 0; j < board[1].length; j++) {
                if ((i + 1) * (j + 1) != board[i][j]) return false;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        // see p.104 of Algorithms 4th Ed.
        if (this == y) return true; // checks for same references
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        if (!Arrays.deepEquals(this.board, that.board)) return false;
        if (this.N != that.N) return false;
        return true;
    }

    // all neighboring boards
    // public Iterable<Board> neighbors() {
    public void neighbours() {
        // if corner then 2 neighbours
        // if centre of board then 4 neighbours
        // else middle (row or col) and on edge has 3 neighbours
        int nNeighbours;

        int[] idxBlank = getBlankTile();
        int row = idxBlank[0];
        int col = idxBlank[1];

        if (row % N == 0 && col % N == 0) nNeighbours = 2;
        else if (row % N == 0 || col % N == 0) nNeighbours = 3;
        else nNeighbours = 4;

        Board[] NeighbourBoard = new Board[nNeighbours];
        int[] rowShift = { -1, 0, 1 };
        int[] colShift = { -1, 0, 1 };

        for (int i = 0; i < rowShift.length; i++) {
            for (int j = 0; j < colShift.length; j++) {
                try {
                    board[row - 1][col - 1] = board[row + rowShift[i] - 1][col + colShift[j] - 1];
                    board[row + rowShift[i] - 1][col + colShift[j] - 1] = 0;
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }
            }
        }
    }

    private int[] getBlankTile() {
        // get row and col index of blank tile (1 indexing)
        int row = 0;
        int col = 0;
        outerloop:
        for (int i = 0; i < board[0].length; i++) {
            for (int j = 0; j < board[1].length; j++) {
                if (board[i][j] == 0) {
                    row = i + 1; // use 1 indexing for neighbours check
                    col = j + 1;
                    break outerloop;
                }
            }
        }
        return new int[] { row, col };
    }

    // a board that is obtained by exchanging any pair of tiles
    // public Board twin() {
    //
    // }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = { { 0, 1, 3 }, { 4, 2, 5 }, { 7, 8, 9 } };
        Board board = new Board(tiles);
        StdOut.print(board.toString());
        StdOut.println(board.hamming());   // expect 3


        StdOut.println(board.manhattan()); // expect 3
    }

}
