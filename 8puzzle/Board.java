import edu.princeton.cs.algs4.Stack;
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
                if (x % N == 0) col = N - 1;
                else col = Math.max(x % N - 1, 0);

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
                if (board[i][j] == 0) continue;
                if (i * N + (j + 1) != board[i][j]) return false;
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
    public Iterable<Board> neighbors() {
        // if corner then 2 neighbours
        // if centre of board then 4 neighbours
        // else middle (row or col) and on edge has 3 neighbours
        int nNeighbours;

        int[] idxBlank = getBlankTile();
        int row = idxBlank[0] - 1;
        int col = idxBlank[1] - 1;
        // StdOut.println("Loc of blank: i = " + row + " j = " + col);

        if (row % (N - 1) == 0 && col % (N - 1) == 0) nNeighbours = 2;
        else if (row % (N - 1) == 0 || col % (N - 1) == 0) nNeighbours = 3;
        else nNeighbours = 4;
        // StdOut.println("nNeighbours: " + nNeighbours);

        Stack<Board> neighbourBoards = new Stack<Board>();
        int[][] neighbourTiles = new int[N][N];
        int[] rowShift = { -1, 0, 1 };
        int[] colShift = { -1, 0, 1 };

        for (int i = 0; i < rowShift.length; i++) {
            for (int j = 0; j < colShift.length; j++) {
                if (Math.abs(rowShift[i]) == Math.abs(colShift[j])) continue;
                try {
                    // make copy of board to avoid modifying
                    int[][] boardCopy = new int[N][N];
                    for (int k = 0; k < board.length; k++)
                        boardCopy[k] = board[k].clone();

                    boardCopy[row][col] = board[row + rowShift[i]][col + colShift[j]];
                    boardCopy[row + rowShift[i]][col + colShift[j]] = 0;

                    // copy to neighbour tiles
                    for (int k = 0; k < boardCopy.length; k++)
                        neighbourTiles[k] = boardCopy[k].clone();

                    // create new neighbour board
                    Board neighbourBoard = new Board(neighbourTiles);

                    // save to stack
                    // StdOut.println("i: " + i + " j: " + j);
                    neighbourBoards.push(neighbourBoard);
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    // StdOut.println("Skipped: i = " + i + " j = " + j);
                    continue;
                }
            }
        }
        return neighbourBoards;
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
    public Board twin() {
        int[] idxBlank = getBlankTile();
        int row = idxBlank[0] - 1;
        int col = idxBlank[1] - 1;

        int[][] boardCopy = new int[N][N];
        for (int k = 0; k < board.length; k++)
            boardCopy[k] = board[k].clone();

        int k = 0, l = 0, m = 0, n = 0;
        outerloop:
        for (int i = 0; i <= board.length; i++) {
            for (int j = 0; j <= board.length; j++) {
                if (i != row || j != col) {
                    k = i;
                    l = j;
                    break outerloop;
                }
            }
        }

        outerloop:
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if ((i != row || j != col) && (i != k || j != l)) {
                    m = i;
                    n = j;
                    break outerloop;
                }
            }
        }

        int element = boardCopy[k][l];
        boardCopy[k][l] = boardCopy[m][n];
        boardCopy[m][n] = element;

        // while (true) {
        //     int j = StdRandom.uniformInt(0, N);
        //     int k = StdRandom.uniformInt(0, N);
        //     int l = StdRandom.uniformInt(0, N);
        //     int m = StdRandom.uniformInt(0, N);
        //
        //     if (j == l && k == m) continue;
        //     if ((j == row && k == col) || (l == row && m == col)) continue;
        //
        //     int element = boardCopy[l][m];
        //     boardCopy[l][m] = boardCopy[j][k];
        //     boardCopy[j][k] = element;
        //     break;
        // }
        return new

                Board(boardCopy);

    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // int[][] tiles = { { 0, 1, 3 }, { 4, 2, 5 }, { 7, 8, 9 } };
        // Board board = new Board(tiles);
        // StdOut.print(board.toString());
        // StdOut.println(board.hamming());   // expect 3
        // StdOut.println(board.manhattan()); // expect 3
        // StdOut.println();
        //
        // for (Board b : board.neighbours()) {
        //     StdOut.println(b.toString());
        //     StdOut.println();
        // }
        // StdOut.println(board.twin().toString());
        //
        // StdOut.println("Board 2");
        // Board board2 = new Board(tiles);
        // StdOut.println(board.equals(board2));
        //
        // StdOut.println("Board 3");
        // Board board3 = new Board(new int[][] { { 1, 2, 3 }, { 4, 0, 6 }, { 7, 8, 9 } });
        // StdOut.println(board3.isGoal());
        // StdOut.println();
        //
        // StdOut.println("Board 4");
        // Board board4 = new Board(new int[][] { { 1, 0 }, { 2, 3 } });
        // StdOut.println(board4.toString());
        // for (Board b : board4.neighbours()) {
        //     StdOut.println(b.toString());
        //     StdOut.println();
        // }

        Board board = new Board(new int[][] { { 1, 2, 3 }, { 4, 6, 0 }, { 7, 5, 8 } });
        StdOut.println(board.toString());
        StdOut.println(board.twin().toString());
        for (Board b : board.neighbors())
            StdOut.println(b.toString());

        Board board2 = new Board(new int[][] { { 3, 1 }, { 2, 0 } });
        StdOut.println(board2.toString());
        StdOut.println(board2.twin().toString());
        // for (Board b : board2.neighbors())
        //     StdOut.println(b.toString());

        // Board board3 = new Board(new int[][] { { 2, 1, 3 }, { 4, 0, 5 }, { 7, 8, 6 } });
        // StdOut.println(board3.toString());
        // StdOut.println(board3.twin().toString());
    }
}
