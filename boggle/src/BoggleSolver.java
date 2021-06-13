import edu.princeton.cs.algs4.StdRandom;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class BoggleSolver {

    private class Node {
        private char c;
        private Node left;
        private Node mid;
        private Node right;
        private String value;
    }

    private class Dictionary {
        private Node root;

        public void add(String s) {
            root = put(root, s, s, 0);
        }

        private Node put(Node x, String s, String val, int d) {
            char c = s.charAt(d);
            if (x == null) {
                x = new Node();
                x.c = c;
            }
            if (c < x.c) {
                x.left = put(x.left, s, val, d);
            } else if (c > x.c) {
                x.right = put(x.right, s, val, d);
            } else if (d < s.length() - 1) {
                x.mid = put(x.mid, s, val, d + 1);
            } else {
                x.value = val;
            }
            return x;
        }

        private boolean contains(String key) {
            return get(key) != null;
        }

        private String get(String key) {
            if (key == null) {
                throw new NullPointerException();
            }
            if (key.length() == 0) {
                throw new IllegalArgumentException();
            }

            Node x = get(root, key, 0);
            if (x == null) {
                return null;
            }
            return x.value;
        }

        private Node get(Node x, String key, int d) {
            if (x == null) {
                return null;
            }
            char c = key.charAt(d);
            if (c < x.c) {
                return get(x.left, key, d);
            } else if (c > x.c) {
                return get(x.right, key, d);
            } else if (d < key.length() - 1) {
                return get(x.mid, key, d + 1);
            } else {
                return x;
            }
        }
    }
    private final Dictionary trie;
    public BoggleSolver(String[] dictionary) {
        if (dictionary == null) {
            throw new IllegalArgumentException();
        }
        trie = new Dictionary();
        String[] dic = new String[dictionary.length];
        for (int i = 0; i < dictionary.length; i++) {
            dic[i] = dictionary[i];
        }
        StdRandom.shuffle(dic);
        for (String e : dic) {
            trie.add(e);
        }
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null) {
            throw new IllegalArgumentException();
        }
        List<String> result = new ArrayList<>();
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); i++) {
                String prefix = getSuffix(board, i, j);
                Node node = trie.get(trie.root, prefix, 0);
                boolean[] visited = new boolean[board.rows() * board.cols()];
                if (node != null) {
                    exploreBoard(result, board, i, j, visited, node);
                }
            }
        }
        List<String> sorted = new ArrayList<>(new HashSet<>(result));
        Collections.sort(sorted);
        return sorted;
    }

    private String getSuffix(BoggleBoard board, int i, int j) {
        char letter = board.getLetter(i, j);
        if (letter == 'Q') {
            return "Qu";
        } else {
            return String.valueOf(letter);
        }
    }

    private void exploreBoard(List<String> words,
                              BoggleBoard board, int x, int y,
                              boolean[] visited, Node node) {
        String v = node.value;
        if (v != null && v.length() >= 3) {
            words.add(v);
        }
        visited[x * board.cols() + y] = true;
        for (int i = Math.max(0, x - 1); i <= Math.min(x + 1, board.rows() - 1); i++) {
            for (int j = Math.max(0, y - 1); j <= Math.min(y + 1, board.cols() - 1); j++) {
                if (visited[i * board.cols() + j]) {
                    continue;
                }
                String suffix = getSuffix(board, i, j);
                Node next = trie.get(node.mid, suffix, 0);
                if (next != null) {
                    exploreBoard(words, board, i, j, visited, next);
                }
            }
        }
        visited[x * board.cols() + y] = true;
    }

    public int scoreOf(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        if (word.length() <= 2 || !trie.contains(word)) {
            return 0;
        }
        switch (word.length()) {
            case 3:
            case 4:
                return 1;
            case 5:
                return 2;
            case 6:
                return 3;
            case 7:
                return 5;
            default:
                return 11;

        }
    }
}
