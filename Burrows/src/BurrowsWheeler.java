import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
public class BurrowsWheeler {
    public static void transform() {
        String result = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(result);
        int length = result.length();
        for (int i = 0; i < length; i++) {
            if (csa.index(i) == 0) {
                BinaryStdOut.write(i);
                break;
            }
        }
        for (int i = 0; i < length; i++) {
            BinaryStdOut.write(result.charAt((csa.index(i) + length - 1) % length));
        }
        BinaryStdOut.close();
    }

    public static void inverseTransform() {
        int start = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        int length = s.length();
        char[] t = new char[length];
        int[] temp = new int[256];
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            t[i] = c;
            temp[c] += 1;
        }

        int[] offset = new int[256];
        int cum = 0;
        for (int i = 1; i < 256; i++) {
            cum += temp[i - 1];
            offset[i] = cum;
        }
        int[] next = new int[length];
        temp = new int[256];
        for (int i = 0; i < length; i++) {
            char c = t[i];
            int alph = offset[c] + temp[c];
            temp[c] += 1;
            next[alph] = i;
        }
        int result = next[start];
        for (int i = 0; i < length - 1; i++) {
            BinaryStdOut.write(t[result]);
            result = next[result];
        }
        BinaryStdOut.close();
        BinaryStdOut.write(t[start]);
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        String par = args[0];
        if (par.equals("-")) {
            transform();
        }
        if (par.equals("+")) {
            inverseTransform();
        }
    }
}
