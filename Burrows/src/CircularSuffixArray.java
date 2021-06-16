import java.util.Arrays;
public class CircularSuffixArray {
    private int length;
    private int[] id;
    private class CircularSuffix implements Comparable<CircularSuffix> {
        private String s;
        private int offset;
        CircularSuffix(String s, int offset) {
            this.s = s;
            this.offset = offset;
        }
        public char charAt(int pos) {
            return s.charAt((offset + pos) % s.length());
        }

        public int compareTo(CircularSuffix that) {
            if (this == that) {
                return 0;
            } else {
                for (int i = 0; i < s.length(); i++) {
                    if (this.charAt(i) < that.charAt(i)) {
                        return -1;
                    } else if (this.charAt(i) > that.charAt(i)) {
                        return 1;
                    }
                }
            }
            return 0;
        }
    }

    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
        length = s.length();
        id = new int[length];
        CircularSuffix[] array = new CircularSuffix[length];
        for (int i = 0; i < length; i++) {
            array[i] = new CircularSuffix(s, i);
        }
        Arrays.sort(array);
        for (int i = 0; i < length; i++) {
            id[i] = array[i].offset;
        }
    }

    public int length() {
        return length;
    }

    public int index(int i) {
        if (i < 0 | i > length - 1) {
            throw  new IllegalArgumentException();
        }
        return id[i];
    }
    public static void main(String[] args) {
        String s = "ABRACADABRA!";
        CircularSuffixArray c = new CircularSuffixArray(s);
        for (int i = 0; i < c.length(); i++) {
            System.out.println(c.index(i));
        }
    }
}
