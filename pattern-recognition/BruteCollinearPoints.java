import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
	private ArrayList<LineSegment> segments = new ArrayList<LineSegment>();
	
	public BruteCollinearPoints(Point[] points) {
		if (points == null)
			throw new java.lang.IllegalArgumentException();
		
		Point[] pointsSorted = points.clone();
		Arrays.sort(pointsSorted);
		
		validatePoints(points);
		
		for (int i = 0; i < pointsSorted.length ; i++) {
			for (int j = i+1; j < pointsSorted.length; j++) {
				double slopeFS = pointsSorted[i].slopeTo(pointsSorted[j]);
				for (int k = j+1; k < pointsSorted.length; k++) {
					double slopeSD = pointsSorted[i].slopeTo(pointsSorted[k]);
					if (Double.compare(slopeFS, slopeSD) == 0) {
						for (int m = k+1; m < pointsSorted.length; m++) {
							double slopeTD = pointsSorted[i].slopeTo(pointsSorted[m]);
							if (Double.compare(slopeFS, slopeTD) == 0) {
								segments.add(new LineSegment(pointsSorted[i], pointsSorted[m]));
							}
						}
					}
				}
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
	    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
	}
}
