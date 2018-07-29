import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<LineSegment>();

    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new java.lang.IllegalArgumentException();
        
        validatePoints(points);
        Point[] pointsClone = points.clone();
        Arrays.sort(pointsClone);
        

        for (int i = 0; i < points.length - 3; i++) {
            Point[] pts = Arrays.copyOfRange(pointsClone, i, points.length);
            Arrays.sort(pts, pts[0].slopeOrder());
            int first = 1, last = 2;
            while (last < pts.length) {
                double slope = pts[0].slopeTo(pts[first]);
                while (last < pts.length && 
                        Double.compare(slope, pts[0].slopeTo(pts[last])) == 0) last++;
                if (last - first >= 3) {
                        segments.add(new LineSegment(pts[0], pts[last - 1]));
                }
                first = last;
                last++;
            }
        }

    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }
    
	private void validatePoints(Point[] points) {
		for (int i = 0; i < points.length - 1; i++) {
			if (points[i] == null || points[i].compareTo(points[i+1]) == 0) {
				throw new java.lang.IllegalArgumentException();
			}
		}
	}
    public static void main(String[] args) {

	    // read the n points from a file
	    In in = new In("grid4x4.txt");
	    int n = in.readInt();
	    Point[] points = new Point[n];
	    for (int i = 0; i < n; i++) {
	        int x = in.readInt();
	        int y = in.readInt();
	        points[i] = new Point(x, y);
	    }

	    // draw the points
	    StdDraw.enableDoubleBuffering();
	    StdDraw.setXscale(0, 32768);
	    StdDraw.setYscale(0, 32768);
	    for (Point p : points) {
	        p.draw();
	    }
	    StdDraw.show();

	    // print and draw the line segments
	    FastCollinearPoints collinear = new FastCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
	}
}