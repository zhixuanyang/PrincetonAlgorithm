import edu.princeton.cs.algs4.Queue;
import java.util.Arrays;
public class BruteCollinearPoints {
    private Queue<LineSegment> temp = new Queue<>();
    private int count;
    private LineSegment[] result;
    public BruteCollinearPoints(Point[] points) {
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
        Point[] test = new Point[points.length];
        System.arraycopy(points, 0, test, 0, points.length);
        Arrays.sort(test);
        count = 0;
        for (int i = 0; i < test.length - 3; i++) {
            for (int j = i + 1; j < test.length - 2; j++) {
                for (int k = j + 1; k < test.length - 1; k++) {
                    for (int m = k + 1; m < test.length; m++) {
                        if (test[i].slopeTo(test[j]) == test[i].slopeTo(test[k])
                                & test[i].slopeTo(test[j]) == test[i].slopeTo(test[m])) {
                            temp.enqueue(new LineSegment(test[i], test[m]));
                            count += 1;
                        }
                    }
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
        while (!temp.isEmpty()) {
            result[number] = temp.dequeue();
            number += 1;
        }
        return result;
    }
}
