import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        if (this.y == that.y) {
        	if (this.x == that.x)
        		return Double.NEGATIVE_INFINITY;
        	else return +0;        		
        }
        else if (this.x == that.x)
        	return Double.POSITIVE_INFINITY;
        else
        	return (that.y - this.y) / (double)(that.x - this.x);

    }

    public int compareTo(Point that) {
        if (that == null) {
            throw new java.lang.NullPointerException();
        }
        
        if (this.y < that.y) 
            return -1;
        else if (this.y > that.y)
            return 1;
        else if (this.x < that.x)
            return -1;
        else if (this.x > that.x)
            return 1;
        else
            return 0;
    }

    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            public int compare(Point point1, Point point2) {
                double slopeDiff = slopeTo(point1) - slopeTo(point2);
                if (slopeDiff > 0)
                	return 1;
                else if (slopeDiff < 0)
                	return -1;
                else
                	return 0;
            }
        };
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public static void main(String[] args) {

    }
}