import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Queue;
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
        private int height;
        Node(Point2D p, Node left, Node right, boolean lor, RectHV rect, int height) {
            this.left = left;
            this.p = p;
            this.right = right;
            this.lor = lor;
            this.rect = rect;
            this.height = height;
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
            RectHV temp = n.rect;
            RectHV result;
            if (n.height % 2 == 0 & status) {
                result = new RectHV(temp.xmin(), temp.ymin(), n.p.x(), temp.ymax());
            } else if (n.height % 2 == 0 & !status) {
                result = new RectHV(n.p.x(), temp.ymin(), temp.xmax(), temp.ymax());
            } else if (n.height % 2 == 1 & status) {
                result = new RectHV(temp.xmin(), temp.ymin(), temp.xmax(), n.p.y());
            } else {
                result = new RectHV(temp.xmin(), n.p.y(), temp.xmax(), temp.ymax());
            }
            return new Node(p, null, null, status, result, n.height + 1);
        }

        if (!h.lor) {
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
                    false, new RectHV(0.0, 0.0, 1.0, 1.0), 1);
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
        Queue<Node> q = new Queue<Node>();
        inorder(root, q);

        for (Node node : q) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            node.p.draw();

            if (node.lor) {
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

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        Stack<Point2D> result = new Stack<>();
        Node curr = root;
        range(curr, rect, result);
        return result;
    }

    private void range(Node x, RectHV rect, Stack result) {
        if (x == null) {
            return;
        }
        if (x.left != null & rect.intersects(x.rect)) {
            range(x.left, rect, result);
        }
        if (rect.contains(x.p)) {
            result.push(x.p);
        }
        if (x.right != null & rect.intersects(x.rect)) {
            range(x.right, rect, result);
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        Node curr = root;
        Point2D result = findnearest(curr, p, root.p);
        return null;
    }

    private Point2D findnearest(Node x, Point2D p, Point2D best) {
        if (x == null) {
            return best;
        }
        if (p.distanceSquaredTo(x.p) < p.distanceSquaredTo(best)) {
            best = x.p;
        }
        Node first;
        Node second;
        if (x.left != null & x.left.rect.contains(p)) {
            first = x.left;
            second = x.right;
        } else {
            first = x.right;
            second = x.left;
        }
        if (first != null & first.rect.distanceSquaredTo(p) < p.distanceSquaredTo(best)) {
            Point2D temp1 = findnearest(first, p, best);
            if (p.distanceSquaredTo(temp1) < p.distanceSquaredTo(best)) {
                best = temp1;
            }
        }
        if (second != null & second.rect.distanceSquaredTo(p) < p.distanceSquaredTo(best)) {
            Point2D temp2 = findnearest(second, p, best);
            if (p.distanceSquaredTo(temp2) < p.distanceSquaredTo(best)) {
                best = temp2;
            }
        }
        return best;
    }

    private void inorder(Node x, Queue<Node> q) {
        if (x == null) {
            return;
        }
        inorder(x.left, q);
        q.enqueue(x);
        inorder(x.right, q);
    }

    public static void main(String[] args) {

    }
}
