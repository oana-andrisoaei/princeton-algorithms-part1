import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> points;

    public PointSET() {
        points = new TreeSet<Point2D>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D point) {
        if (!contains(point))
        points.add(point);
    }

    public boolean contains(Point2D point) {
        if (point == null) throw new IllegalArgumentException();
        return points.contains(point);
    }

    public void draw() {
        for (Point2D point : points) {
            point.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> queue = new Queue<Point2D>();
        for (Point2D point : points) {
            if (rect.contains(point))
                queue.enqueue(point);
        }
        return queue;
    }

    public Point2D nearest(Point2D point) {
        if (point == null) throw new IllegalArgumentException();
        Point2D nearest = null;
        for (Point2D p : points) {
            if (nearest == null
                || p.distanceSquaredTo(point) < nearest.distanceSquaredTo(point)) {
                nearest = p;
            }
        }
        return nearest;
    }
 }