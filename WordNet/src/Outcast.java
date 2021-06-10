import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
public class Outcast {
    private WordNet wordnet;
    public Outcast(WordNet wordnet) {
        if (wordnet == null) {
            throw new IllegalArgumentException();
        }
        this.wordnet = wordnet;
    }

    public String outcast(String[] nouns) {
        if (nouns == null) {
            throw new IllegalArgumentException();
        }
        int best = Integer.MIN_VALUE;
        String result = null;
        for (String i : nouns) {
            int temp = 0;
            for (String j : nouns) {
                if (!wordnet.isNoun(i) || !wordnet.isNoun(j)) {
                    throw  new IllegalArgumentException();
                }
                temp += wordnet.distance(i, j);
            }
            if (temp > best) {
                best = temp;
                result = i;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
