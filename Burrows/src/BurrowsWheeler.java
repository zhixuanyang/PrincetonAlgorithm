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

    }

    public static void main(String[] args) {

    }
}
