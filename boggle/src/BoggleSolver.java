import edu.princeton.cs.algs4.StdRandom;
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
}
