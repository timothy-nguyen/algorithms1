import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] segments; // collinear line segments
    private int n = 0;              // number of line segments

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (int p = 1; p < points.length; p++)
            if (points[0].compareTo(points[p]) == 0) throw new IllegalArgumentException();

        this.segments = new LineSegment[(points.length * (points.length - 1)) / 2];

        // rather than copying slopes into another array
        // can we sort the Point[] array using slope as the comparator
        Arrays.sort(points, 1, points.length, points[0].slopeOrder());

        // if slope to second and last point is equal then all points are collinear
        if (Math.abs(points[0].slopeTo(points[1])) == Math.abs(
                points[0].slopeTo(points[points.length - 1]))) {
            this.segments[n++] = new LineSegment(points[0], points[points.length - 1]);
        }
    }

    public int numberOfSegments() {
        return this.n;
    }

    public LineSegment[] segments() {
        return this.segments;
    }

    public static void main(String[] args) {

        // replicate input6.txt
        Point[] points = {
                new Point(19000, 10000),
                new Point(18000, 10000),
                new Point(32000, 10000),
                new Point(21000, 10000),
                new Point(1234, 5678),
                new Point(14000, 10000)
        };

        FastCollinearPoints collinearPts = new FastCollinearPoints(points);
        StdOut.println(collinearPts.numberOfSegments());
        for (LineSegment s : collinearPts.segments()) StdOut.println(s);

        /*
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        */
    }
}

