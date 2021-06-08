import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
public class KdTree {
    private int size;
    private Node root;
    public KdTree() {
        size = 0;
        root = null;
    }
    private static class Node {
        private Node left;
        private Node right;
        private Point2D p;
        private boolean lor;
        private RectHV rect;
        Node(Point2D p, Node left, Node right, boolean lor, RectHV rect) {
            this.left = left;
            this.p = p;
            this.right = right;
            this.lor = lor;
            this.rect = rect;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private Node put(Node h, Point2D p, Node n) {
        if (h == null) {
            boolean status = !n.lor;
            size += 1;
            RectHV temp = ;
            return new Node(p, null, null, status, temp);
        }

        if (h.lor) {
            int cmp = Double.compare(p.x(), h.p.x());
            if (cmp < 0) {
                h.left = put(h.left, p, h);
            } else if (cmp > 0) {
                h.right = put(h.right, p, h);
            } else {
                h.p = p;
            }
        } else {
            int cmp = Double.compare(p.y(), h.p.y());
            if (cmp < 0) {
                h.left = put(h.left, p, h);
            } else if (cmp > 0) {
                h.right = put(h.right, p, h);
            } else {
                h.p = p;
            }
        }
        return h;
    }
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            root = new Node(p, null, null,
                    false, new RectHV(0.0, 0.0, 1.0, 1.0));
        }
        root = put(root, p, root);
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            return false;
        }

        Node temp = root;
        while (temp != null) {
            int cmpx = Double.compare(p.x(), temp.p.x());
            int cmpy = Double.compare(p.y(), temp.p.y());
            if (cmpx == 0 & cmpy == 0) {
                return true;
            }
            if (temp.lor) {
                if (cmpx < 0) {
                    temp = temp.left;
                } else {
                    temp = temp.right;
                }
            } else {
                if (cmpy < 0) {
                    temp = temp.left;
                } else {
                    temp = temp.right;
                }
            }
        }
        return false;
    }

    public void draw() {

    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        Stack<Point2D> result = new Stack<>();
        Node curr = root;
        return null;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return null;
    }

    public static void main(String[] args) {

    }
}
