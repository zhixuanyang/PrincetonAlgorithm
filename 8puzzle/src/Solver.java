import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.HashMap;
public class Solver {
    private int move;
    private Stack<Board> path = new Stack<>();
    private HashMap<Board, Integer> hm = new HashMap<>();
    private class SearchNode implements Comparable<SearchNode> {
        private Board ws;
        private int move;
        private SearchNode prev;
        private int estimated;

        SearchNode(Board ws, int moves, SearchNode prev) {
            this.ws = ws;
            this.move = moves;
            this.prev = prev;
            if (hm.containsKey(ws)) {
                this.estimated = hm.get(ws);
            } else {
                this.estimated = ws.manhattan();
                hm.put(ws, this.estimated);
            }
        }

        @Override
        public int compareTo(SearchNode o) {
            return (this.move + this.estimated) - (o.move + o.estimated);
        }
    }

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        MinPQ<SearchNode> pq = new MinPQ<>();
        SearchNode curr = new SearchNode(initial, 0, null);
        while (!curr.ws.isGoal()) {
            for (Board neighbor : curr.ws.neighbors()) {
                if (curr.prev == null || !neighbor.equals(curr.prev.ws)) {
                    pq.insert(new SearchNode(neighbor, curr.move + 1, curr));
                }
            }
            curr = pq.delMin();
        }
        move = curr.move;
        while (curr != null) {
            path.push(curr.ws);
            curr = curr.prev;
        }
    }

    public boolean isSolvable() {
        return true;
    }

    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return move;
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        return path;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}
