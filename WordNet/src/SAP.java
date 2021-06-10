import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
public class SAP {
    private Digraph graph;
    private final int root = 38003;
    private int min_V = -1;
    private int min_W = -1;
    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException();
        }
        this.graph = new Digraph(G);
    }

    public int length(int v, int w) {
        if (v >= graph.V() || v < 0 || w >= graph.V() || w < 0) {
            throw new IllegalArgumentException();
        }
        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(graph, w);
        if (!bfsv.hasPathTo(root) || !bfsw.hasPathTo(root)) {
            return -1;
        }
        ArrayList<Integer> tempv = new ArrayList<>();
        ArrayList<Integer> tempw = new ArrayList<>();
        bfsv.pathTo(root).forEach(tempv :: add);
        bfsw.pathTo(root).forEach(tempw :: add);
        tempv.retainAll(tempw);
        int firstcommon = tempv.get(0);
        return bfsv.distTo(firstcommon) + bfsw.distTo(firstcommon);
    }

    public int ancestor(int v, int w) {
        if (v >= graph.V() || v < 0 || w >= graph.V() || w < 0) {
            throw new IllegalArgumentException();
        }
        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(graph, w);
        if (!bfsv.hasPathTo(root) || !bfsw.hasPathTo(root)) {
            return -1;
        }
        ArrayList<Integer> tempv = new ArrayList<>();
        ArrayList<Integer> tempw = new ArrayList<>();
        bfsv.pathTo(root).forEach(tempv :: add);
        bfsw.pathTo(root).forEach(tempw :: add);
        tempv.retainAll(tempw);
        return tempv.get(0);
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        Integer min = Integer.MAX_VALUE;
        for (Integer i : v) {
            for (Integer j : w) {
                if (i == null || j == null) {
                    throw new IllegalArgumentException();
                }
                int temp = length(i, j);
                if (length(i, j) < min & temp != -1) {
                    min = temp;
                    min_V = i;
                    min_W = j;
                }
            }
        }
        if (min == Integer.MAX_VALUE) {
            return -1;
        }
        return min;
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        int temp = length(v, w);
        if (temp == -1) {
            return temp;
        }
        return ancestor(min_V, min_W);
    }

    public static void main(String[] args) {
        if (args == null) {
            throw new IllegalArgumentException();
        }
        In in = new In(args[0]);
        Digraph G = new Digraph(in); //Digraph(In in)
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
