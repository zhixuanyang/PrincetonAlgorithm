import java.util.Arrays;
import java.util.ArrayList;

public class FastCollinearPoints {
    private LineSegment[] seg;
    private ArrayList<LineSegment> foundSeg = new ArrayList<>();
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

        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);

        for (int n = 0; n < pointsCopy.length - 3; n++) {
            Point bP = pointsCopy[n];
            Point[] nextPoints = new Point[ pointsCopy.length - n - 1];
            for (int m = 0; m < nextPoints.length; m++) {
                nextPoints[m] = pointsCopy[n + 1 + m];
            }
            Arrays.sort(nextPoints, bP.slopeOrder());
            int count = 0;
            double bSlope = Double.NEGATIVE_INFINITY;
            for (int i = 0; i < nextPoints.length; i++) {
                double nSlope = bP.slopeTo(nextPoints[i]);
                if (bSlope != nSlope || i == nextPoints.length - 1) {
                    if (bSlope == nSlope) {
                        count++;
                    }
                    if (count >= 3) {
                        boolean added = false;
                        for (int m = 0; m < n; m++) {
                            if (bP.slopeTo(pointsCopy[m]) == bSlope) {
                                added = true;
                            }
                        }

                        if (!added) {
                            int lastIndex = bSlope != nSlope ? i - 1 : i;
                            LineSegment nSeg = new LineSegment(bP, nextPoints[lastIndex]);
                            this.addNewSeg(nSeg);
                        }
                    }
                    count = 1;
                    bSlope = nSlope;

                } else {
                    count++;
                }
            }
        }

        seg = foundSeg.toArray(new LineSegment[ foundSeg.size()]);
    }

    private void addNewSeg(LineSegment nSeg) {
        boolean isAdd = true;
        if (isAdd) {
            foundSeg.add(nSeg);
        }
    }

    public int numberOfSegments() {
        return this.seg.length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(seg, seg.length);
    }
}
