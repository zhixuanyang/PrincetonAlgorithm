import java.util.Arrays;
import java.util.ArrayList;
public class FastCollinearPoints {
    private int count;
    LineSegment[] result;
    public FastCollinearPoints(Point[] points) {
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
            Point[] temp = new Point[points.length - 1 - i];
            System.arraycopy(points, i, temp, 0, points.length - 1 - i);
            Arrays.sort(temp, points[i].slopeOrder());
        }
    }
}
