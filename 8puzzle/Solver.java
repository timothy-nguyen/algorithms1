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
    private int n = 0; // no. moves to solve board
    private Queue<Board> solutionStack;

    private class SearchNode {
        private Board board;
        private int manhattan;
        private int nMoves; // no. moves to reach search node

        public SearchNode(Board board, int n) {
            this.board = board;
            this.nMoves = n;
            this.manhattan = board.manhattan();
        }
    }

    private class PriorityFn implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode a, SearchNode b) {
            if ((a.manhattan + a.nMoves) - (b.manhattan + b.nMoves) > 0) return 1;
            if ((a.manhattan + a.nMoves) - (b.manhattan + b.nMoves) < 0) return -1;
            if (a.manhattan - b.manhattan > 0) return 1;
            if (a.manhattan - b.manhattan < 0) return -1;
            return 0;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        this.initial = initial;
        this.solutionStack = new Queue<Board>();

        // Initialise game tree
        MinPQ<SearchNode> tree = new MinPQ<SearchNode>(new PriorityFn());
        tree.insert(new SearchNode(initial, 0));

        if (isSolvable()) {
            while (true) {
                SearchNode minBoard = tree.min();
                // StdOut.println("Min board: ");
                // StdOut.println("manhattan: " + minBoard.manhattan);
                // StdOut.println("moves: " + minBoard.nMoves);
                // StdOut.println("priority: " + (int) (minBoard.nMoves + minBoard.board.manhattan()));
                // StdOut.println(minBoard.board.toString());

                this.solutionStack.enqueue(minBoard.board);
                tree.delMin();

                for (Board nb : minBoard.board.neighbors()) {
                    if (nb.equals(minBoard.board)) continue;
                    tree.insert(new SearchNode(nb, minBoard.nMoves + 1));
                    // StdOut.println("Neighbour: ");
                    // StdOut.println("manhattan: " + nb.manhattan());
                    // StdOut.println("moves: " + (int) (minBoard.nMoves + 1));
                    // StdOut.println("priority: " + (int) (minBoard.nMoves + 1 + nb.manhattan()));
                    // StdOut.println(nb.toString());
                    // StdOut.println();
                }
                if (minBoard.board.isGoal()) break;
                this.n += 1;
            }
        }
        else {
            this.solutionStack = null;
            this.n = -1;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        // solve initial board and twin in lockstep
        Queue<Board> solution1 = new Queue<Board>();
        Queue<Board> solution2 = new Queue<Board>();

        MinPQ<SearchNode> tree1 = new MinPQ<SearchNode>(new PriorityFn());
        MinPQ<SearchNode> tree2 = new MinPQ<SearchNode>(new PriorityFn());

        tree1.insert(new SearchNode(this.initial, 0));
        tree2.insert(new SearchNode(this.initial.twin(), 0));

        // Add neighbours to queue
        while (true) {
            SearchNode minBoard1 = tree1.min();
            SearchNode minBoard2 = tree2.min();

            solution1.enqueue(minBoard1.board);
            solution2.enqueue(minBoard1.board);

            tree1.delMin();
            tree2.delMin();

            for (Board nb : minBoard1.board.neighbors()) {
                if (nb.equals(minBoard1.board)) continue;
                tree1.insert(new SearchNode(nb, minBoard1.nMoves + 1));
            }

            for (Board nb : minBoard2.board.neighbors()) {
                if (nb.equals(minBoard2.board)) continue;
                tree2.insert(new SearchNode(nb, minBoard2.nMoves + 1));
            }

            if (minBoard1.board.isGoal()) return true;
            if (minBoard2.board.isGoal()) return false;
        }
    }

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

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                // StdOut.println("Manhattan: " + board.manhattan());
                StdOut.println(board);
            }
        }
    }
}
