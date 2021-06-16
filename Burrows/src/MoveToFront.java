import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.BinaryStdIn;
import java.util.LinkedList;
public class MoveToFront {

    public static void encode() {
        LinkedList<Character> list = new LinkedList<>();
        for (int i = 0; i < 256; i++) {
            list.add((char) i);
        }
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int index = list.indexOf(c);
            BinaryStdOut.write(index, 8);
            list.remove(index);
            list.addFirst(c);
        }
        BinaryStdOut.close();
    }

    public static void decode() {
        LinkedList<Character> list = new LinkedList<>();
        for (int i = 0; i < 256; i++) {
            list.add((char) i);
        }
        while (!BinaryStdIn.isEmpty()) {
            char index = BinaryStdIn.readChar();
            char c = list.get(index);
            BinaryStdOut.write(c);
            list.remove(index);
            list.addFirst(c);
        }
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if (("-").equals(args[0])) {
            encode();
        } else if (("+").equals(args[0])) {
            decode();
        }
    }
}
