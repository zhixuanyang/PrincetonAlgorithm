import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Queue;

enum Direction {
    LEFT, BOTTOM, RIGHT, TOP
}

public class KdTree {
    private Node root;
    private int size;
    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;
        private boolean isH;
        Node(Point2D point, RectHV r, boolean H) {
            p = point;
            rect = r;
            isH = H;
        }
    }

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (root == null) {
            root = new Node(p, new RectHV(0.0, 0.0, 1.0, 1.0), false);
            size++;
            return;
        }

        int cmp, cmp2;
        cmp = Double.compare(p.x(), root.p.x());
        cmp2 = Double.compare(p.y(), root.p.y());

        if (cmp == 0 && cmp2 == 0) {
            return;
        }

        if (cmp < 0) {
            root.lb = put(root.lb, p, root, Direction.LEFT);
        } else {
            root.rt = put(root.rt, p, root, Direction.RIGHT);
        }


    }

    private Node put(Node x, Point2D p, Node bN, Direction drct) {
        if (x == null) {
            RectHV bRect = bN.rect;
            RectHV nRect;
            Point2D bPoint = bN.p;
            Double xmin, ymin, xmax, ymax;
            xmin = bRect.xmin(); xmax = bRect.xmax(); ymin = bRect.ymin(); ymax = bRect.ymax();

            //StdOut.println(drct);

            if (drct == Direction.LEFT) {
                nRect = new RectHV(xmin, ymin, bPoint.x(), ymax);
            } else if (drct == Direction.RIGHT) {
                nRect = new RectHV(bPoint.x(), ymin, xmax, ymax);
            } else if (drct == Direction.BOTTOM) {
                nRect = new RectHV(xmin, ymin, xmax, bPoint.y());
            } else {
                nRect = new RectHV(xmin, bPoint.y(), xmax, ymax);
            }
            size++;
            return new Node(p, nRect, drct == Direction.RIGHT || drct == Direction.LEFT);
        }

        if (x.p == p) {
            return x;
        }
        int cmp, cmp2;
        if (drct == Direction.BOTTOM || drct == Direction.TOP) {
            cmp = Double.compare(p.x(), x.p.x());
            cmp2 = Double.compare(p.y(), x.p.y());
        } else {
            cmp = Double.compare(p.y(), x.p.y());
            cmp2 = Double.compare(p.x(), x.p.x());
        }

        if (cmp == 0 && cmp2 == 0) {
            return x;
        }
        if (cmp < 0) {
            x.lb = put(x.lb, p, x, drct == Direction.LEFT
                    || drct == Direction.RIGHT
                    ? Direction.BOTTOM : Direction.LEFT);
        } else {
            x.rt = put(x.rt, p, x, drct == Direction.LEFT
                    || drct == Direction.RIGHT
                    ? Direction.TOP : Direction.RIGHT);
        }
        return x;
    }

    public boolean contains(Point2D p) {
        Node now = root;
        while (now != null) {
            int cmp, cmp2;
            cmp = Double.compare(p.x(), now.p.x());
            cmp2 = Double.compare(p.y(), now.p.y());

            if (cmp == 0 && cmp2 == 0) {
                return true;
            }

            if (now.isH) {
                if (p.y() < now.p.y()) {
                    now = now.lb;
                } else {
                    now = now.rt;
                }
            } else {
                if (p.x() < now.p.x()) {
                    now = now.lb;
                } else {
                    now = now.rt;
                }
            }
        }
        return false;
    }

    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> st = new Stack<>();
        range(root, rect, st);
        return st;
    }

    private void range(Node x, RectHV rect, Stack<Point2D> st) {
        if (x == null) {
            return;
        }
        if (x.lb != null && rect.intersects(x.lb.rect)) {
            range(x.lb, rect, st);
        }

        if (rect.contains(x.p)) {
            st.push(x.p);
        }

        if (x.rt != null && rect.intersects(x.rt.rect)) {
            range(x.rt, rect, st);
        }
    }

    public Point2D nearest(Point2D p) {
        Point2D nearP = root.p;
        nearP = findNearest(root, p, nearP);
        //StdOut.println(nearP);
        return nearP;
    }

    private Point2D findNearest(Node x, Point2D p, Point2D nearP) {
        if (x == null) {
            return nearP;
        }
        if (p.distanceSquaredTo(x.p) < p.distanceSquaredTo(nearP)) {
            nearP = x.p;
        }
        Node first, second;
        if (x.lb != null && x.lb.rect.contains(p)) {
            first = x.lb;
            second = x.rt;
        } else {
            first = x.rt;
            second = x.lb;
        }

        if (first != null && first.rect.distanceSquaredTo(p) < p.distanceSquaredTo(nearP)) {
            Point2D firstP = findNearest(first, p, nearP);
            if (p.distanceSquaredTo(firstP) < p.distanceSquaredTo(nearP)) {
                nearP = firstP;
            }
        }
        if (second != null && second.rect.distanceSquaredTo(p) < p.distanceSquaredTo(nearP)) {
            Point2D secondP = findNearest(second, p, nearP);
            if (p.distanceSquaredTo(secondP) < p.distanceSquaredTo(nearP)) {
                nearP = secondP;
            }
        }

        return nearP;
    }

    public void draw() {
        Queue<Node> q = new Queue<>();
        inorder(root, q);

        for (Node node : q) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            node.p.draw();

            if (node.isH) {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius();
                StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
            } else {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius();
                StdDraw.line(node.p.x(), node.rect.ymax(), node.p.x(), node.rect.ymin());
            }
        }
    }

    private void inorder(Node x, Queue<Node> q) {
        if (x == null) {
            return;
        }
        inorder(x.lb, q);
        q.enqueue(x);
        inorder(x.rt, q);
    }

    public static void main(String[] args) {
     
    }
}
