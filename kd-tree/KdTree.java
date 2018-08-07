import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
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
        private Point2D point;
        private RectHV rect;
        private Node left;
        private Node right;

        private Node (Point2D point, RectHV rect) {
            this.point = point;
            this.rect = rect;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D point) {
        if (point == null) throw new IllegalArgumentException();
        root = insert(root, point, 0.00, 0.00, 1.00, 1.00, true);
    }

    private Node insert(Node node, Point2D point, double x1, double y1, double x2, double y2, boolean isVertical) {
        if (node == null) {
            size++;
            return new Node(point, new RectHV(x1, y1, x2, y2));
        }
        else if (node.point.equals(point)) return node;
        
        if (isVertical) {
            if (point.x() < node.point.x())
                node.left = insert(node.left, point, x1, y1, node.point.x(), y2, !isVertical);
            else
                node.right = insert(node.right, point, node.point.x(), y1, x2, y2, !isVertical);
        }
        else {
            if (point.y() < node.point.y())
                node.left = insert(node.left, point, x1, y1, x2, node.point.y(), !isVertical);
            else
                node.right = insert(node.right, point, x1, node.point.y(), x2, y2, !isVertical);
        }

        return node;
    }

    public boolean contains(Point2D point) {
        if (point == null) throw new IllegalArgumentException();
        return contains(root, point, true);
    }

    private boolean contains(Node node, Point2D point, boolean isVertical) {
        if (node == null) {
            return false;
        }
        else if (node.point.equals(point)) return true;
        
        if (isVertical) {
            if (point.x() < node.point.x())
                return contains(node.left, point, !isVertical);
            else
                return contains(node.right, point, !isVertical);
        }
        else {
            if (point.y() < node.point.y())
                return contains(node.left, point, !isVertical);
            else
                return contains(node.right, point, !isVertical);
        }
    }

    public void draw() {
        draw(root, true);
    }
    
    private void draw(Node node, boolean drawVert) {

        if (node == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        node.point.draw();

        if (drawVert) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
        }

        draw(node.left, !drawVert);
        draw(node.right, !drawVert);
    }

    public Point2D nearest(Point2D point) {
        if (point == null) throw new IllegalArgumentException();
        if (root == null) return null;
        return nearest(root, point, root.point, true);
    }

    private Point2D nearest(Node node, Point2D point, Point2D oldClosest, boolean isVertical) {
        Point2D closest = oldClosest;

        if (node == null) return closest;
        if (node.point.distanceSquaredTo(point) < closest.distanceSquaredTo(point))
            closest = node.point;

        if (node.rect.distanceSquaredTo(point) < closest.distanceSquaredTo(point)) {
            Node near;
            Node far;
            if ((isVertical && (point.x() < node.point.x())) || (!isVertical && (point.y() < node.point.y()))) {
                near = node.left;
                far = node.right;
            }
            else {
                near = node.right;
                far = node.left;
            }

            closest = nearest(near, point, closest, !isVertical);
            closest = nearest(far, point, closest, !isVertical);
        }

        return closest;
    }
 
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> queue = new Queue<Point2D>();
        range(root, rect, queue);
        return queue;
    }

    private void range(Node node, RectHV rect, Queue<Point2D> queue) {
        if (node == null) return;
        if (rect.contains(node.point)) {
            queue.enqueue(node.point);
        }
        if (rect.intersects(node.rect)) {
            range(node.left, rect, queue);
            range(node.right, rect, queue);
        }
    }
}