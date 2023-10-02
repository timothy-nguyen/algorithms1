import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments; // collinear line segments
    private int n = 0;              // number of line segments

    public BruteCollinearPoints(Point[] points) {

        if (points == null) throw new IllegalArgumentException();
        for (int p = 0; p < points.length; p++)
            if (points[p] == null) throw new IllegalArgumentException();

        // copy array to iterate over
        Point[] pointsCopy = new Point[points.length];
        for (int i = 0; i < points.length; i++) pointsCopy[i] = points[i];

        Arrays.sort(pointsCopy);

        // check for duplicate points
        for (int p = 0; p < pointsCopy.length - 1; p++) {
            if (pointsCopy[p].compareTo(pointsCopy[p + 1]) == 0)
                throw new IllegalArgumentException();
        }

        // max no. of collinear points is n choose 2
        this.segments = new LineSegment[(points.length * (points.length - 1)) / 2];

        // iterate through each 4 tuple of points
        for (int i = 0; i < points.length; i++)
            for (int j = i + 1; j < points.length; j++)
                for (int k = j + 1; k < points.length; k++)
                    for (int m = k + 1; m < points.length; m++) {

                        // points are collinear if
                        // 1. the slopes between inner segments are equal
                        // 2. only form line segment if 4 points are in asc. order (excl. sub-segments, reverse order)
                        // 3. only pick maximal points
                        double slopePQ = pointsCopy[i].slopeTo(pointsCopy[j]);
                        double slopePR = pointsCopy[i].slopeTo(pointsCopy[k]);
                        double slopePS = pointsCopy[i].slopeTo(pointsCopy[m]);
                        // StdOut.println(points[i].toString() + " " +
                        //                        points[j].toString() + " " +
                        //                        points[k].toString() + " " +
                        //                        points[m].toString());

                        boolean equalSlope = (slopePQ == slopePR && slopePR == slopePS);

                        // If equal slopes then the maximal endpoints
                        if (equalSlope) {
                            this.segments[n++] = new LineSegment(pointsCopy[i], pointsCopy[m]);
                        }
                    }
    }

    public int numberOfSegments() {
        return this.n;
    }

    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[this.n];
        for (int i = 0; i < this.n; i++) segments[i] = this.segments[i];
        return segments;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
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
