import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
public class PointSET {
    private SET<Point2D> set;
    public PointSET() {
        set = new SET<>();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public int size() {
        return set.size();
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        set.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return set.contains(p);
    }

    public void draw() {
        for (Point2D p : set) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        Stack<Point2D> resultRange = new Stack<>();
        for (Point2D p : set) {
            if (rect.contains(p)) {
                resultRange.push(p);
            }
        }
        return resultRange;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        double distance = Double.POSITIVE_INFINITY;
        Point2D result = null;
        for (Point2D temp : set) {
            double currDistance = temp.distanceSquaredTo(p);
            if (currDistance < distance) {
                distance = currDistance;
                result = temp;
            }
        }
        return result;
    }

    public static void main(String[] args) {

    }
}
