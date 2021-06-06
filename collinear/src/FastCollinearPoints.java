import java.util.Arrays;
import edu.princeton.cs.algs4.Queue;

public class FastCollinearPoints {
    private int count;
    private LineSegment[] result;
    private Queue<LineSegment> store;
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
        store = new Queue<>();
        count = 0;
        for (int i = 0; i < points.length - 3; i++) {
            Point[] temp = new Point[points.length - 1 - i];
            System.arraycopy(points, i, temp, 0, points.length - 1 - i);
            Arrays.sort(temp, points[i].slopeOrder());
            for (int j = 3; j < temp.length; j++) {
                if (temp[j].slopeTo(temp[j - 1]) == temp[j].slopeTo(temp[j - 2])) {
                    if (temp[j].slopeTo(temp[j - 3]) == temp[j].slopeTo(temp[j - 1])) {
                        int record = j;
                        int number = 0;
                        if (temp[j + 1] != null) {
                            number += 1;
                            while (temp[record].slopeTo(temp[record + number])
                                    == temp[record].slopeTo(temp[record - 1])) {
                                j += 1;
                                number += 1;
                            }
                        }
                        if (number > 0) {
                            number = number - 1;
                        }
                        count += 1;
                        store.enqueue(new LineSegment(points[record - 3], points[record + number]));
                    }
                } else {
                    break;
                }
            }
        }
    }

    public int numberOfSegments() {
        return count;
    }

    public LineSegment[] segments() {
        result = new LineSegment[count];
        int number = 0;
        while (!store.isEmpty()) {
            result[number] = store.dequeue();
            number += 1;
        }
        return result;
    }
}
