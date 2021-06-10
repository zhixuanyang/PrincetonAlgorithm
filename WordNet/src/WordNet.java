import edu.princeton.cs.algs4.Bag;
import java.util.Map;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.DirectedCycle;
import java.util.HashMap;
public class WordNet {
    private Map<String, Bag<Integer>> wordlist;
    private Map<Integer, String> synsetslist;
    private SAP sap;
    private Digraph graph;
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }
        wordlist = new HashMap<>();
        synsetslist = new HashMap<>();
        int n = 0;
        In syn = new In(synsets);
        String line;
        while ((line = syn.readLine()) != null) {
            String[] parts = line.split(",");
            n = Integer.parseInt(parts[0]);
            String[] syns = parts[1].split(" ");
            for (String s : syns) {
                Bag<Integer> ids = wordlist.get(s);
                if (ids == null) {
                    ids = new Bag<>();
                    wordlist.put(s, ids);
                }
                ids.add(n);
            }
            synsetslist.put(n, parts[1]);
        }
        graph = new Digraph(n + 1);
        In hyp = new In(hypernyms);
        while ((line = hyp.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 2) {
                int start = Integer.parseInt(parts[0]);

                for (int i = 1; i < parts.length; i++) {
                    int end = Integer.parseInt(parts[i]);
                    graph.addEdge(start, end);
                }
            }
        }
        DirectedCycle cycle = new DirectedCycle(graph);
        if (cycle.hasCycle()) {
            throw new IllegalArgumentException();
        }
        int root = -1;
        for (int i = 0; i < graph.V(); i++) {
            if (!graph.adj(i).iterator().hasNext()) {
                if (root != -1) {
                    throw new IllegalArgumentException();
                } else {
                    root = i;
                }
            }
        }
        sap = new SAP(graph);
    }

    public Iterable<String> nouns() {
        return wordlist.keySet();
    }

    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return wordlist.containsKey(word);
    }

    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }
        Iterable<Integer> A = wordlist.get(nounA);
        Iterable<Integer> B = wordlist.get(nounB);
        if (A == null || B == null) {
            throw new IllegalArgumentException();
        }
        return sap.length(A, B);
    }

    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }
        Iterable<Integer> A = wordlist.get(nounA);
        Iterable<Integer> B = wordlist.get(nounB);
        if (A == null || B == null) {
            throw new IllegalArgumentException();
        }
        int result = sap.ancestor(A, B);
        if (result == -1) {
            return null;
        }
        return synsetslist.get(result);
    }

    public static void main(String[] args) {
        if (args == null) {
            throw  new IllegalArgumentException();
        }
    }
}
