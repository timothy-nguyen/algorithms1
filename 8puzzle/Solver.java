/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {
    private Board initial;
    private int n; // no. moves
    private Queue<Board> solutionStack;

    public class PriorityFn implements Comparator<Board> {
        @Override
        public int compare(Board a, Board b) {
            return Integer.compare(a.manhattan() + n, b.manhattan() + n);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        this.solutionStack = new Queue<Board>();

        // Initialise game tree
        MinPQ<Board> tree = new MinPQ<Board>(new PriorityFn());
        tree.insert(initial);

        // Add neighbours to queue
        while (true) {
            Board minBoard = tree.min();
            this.solutionStack.enqueue(minBoard);
            if (minBoard.isGoal()) break;
            tree.delMin();
            for (Board nb : minBoard.neighbours()) {
                if (nb.equals(minBoard)) continue;
                tree.insert(nb);
            }
            n += 1;
        }
    }

    // is the initial board solvable? (see below)
    // public boolean isSolvable() {
    //     Solver solve1 = new Solver(this.initial);
    //     Solver solve2 = new Solver(this.initial.twin());
    // }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return this.n;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return this.solutionStack;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        StdOut.println("Initial board:");
        StdOut.println(initial.toString());

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        // if (!solver.isSolvable())
        //     StdOut.println("No solution possible");
        // else {
        //     StdOut.println("Minimum number of moves = " + solver.moves());
        //     for (Board board : solver.solution())
        //         StdOut.println(board);
        // }
        for (Board board : solver.solution())
            StdOut.println(board);
    }
}
