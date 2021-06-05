import edu.princeton.cs.algs4.Queue;
public class BruteCollinearPoints {
    Queue<LineSegment> temp = new Queue<>();
    private int count;
    LineSegment[] result;
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            for (int j = 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
        count = 0;
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = 1; j < points.length - 2; j++) {
                for (int k = 2; k < points.length - 1; k++) {
                    for (int m = 3; m < points.length; m++) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])
                                & points[i].slopeTo(points[j]) == points[i].slopeTo(points[m])) {
                            temp.enqueue(new LineSegment(points[i], points[m]));
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
