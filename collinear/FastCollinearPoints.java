import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] segments; // collinear line segments
    private int n = 0;              // number of line segments

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (int p = 1; p < points.length; p++)
            if (points[0].compareTo(points[p]) == 0) throw new IllegalArgumentException();

        // n choose 2 ways to construct line segments
        this.segments = new LineSegment[(points.length * (points.length - 1)) / 2];

        // copy array to interate over
        Point[] pointsStatic = new Point[points.length];
        for (int i = 0; i < points.length; i++) pointsStatic[i] = points[i];
        Arrays.sort(pointsStatic);
        Arrays.sort(points);
        
        for (int p = 0; p < pointsStatic.length; p++) {
            Point pt = pointsStatic[p];
            StdOut.println(pt.toString());

            // Sort by slope to point
            StdOut.println("Pre-slope sort " + Arrays.toString(points));
            Arrays.sort(points, 0, points.length, pt.slopeOrder());
            StdOut.println("Post-slope sort " + Arrays.toString(points));
            StdOut.println(pt.slopeTo(points[0]) + " " +
                                   pt.slopeTo(points[1]) + " " +
                                   pt.slopeTo(points[2]) + " " +
                                   pt.slopeTo(points[3]));

            // check if 3+ adjacent points in sorted order have equal slopes
            double slopePQ = points[0].slopeTo(points[1]);
            double slopePR = points[0].slopeTo(points[2]);
            double slopePS = points[0].slopeTo(points[3]);
            StdOut.println(slopePQ + " " + slopePR + " " + slopePS);


            // check if point is in the middle. Then it is not a maximal line
            boolean isMiddle = pt.compareTo(points[0]) > 0 && pt.compareTo(points[3]) < 0;
            StdOut.println(isMiddle);
            StdOut.println();

            if (slopePQ == slopePR && slopePR == slopePS && !isMiddle) {
                this.segments[n++] = new LineSegment(points[0], points[3]);
            }
        }

    }

    public int numberOfSegments() {
        return this.n;
    }

    public LineSegment[] segments() {
        LineSegment[] seg = new LineSegment[this.n];
        for (int i = 0; i < this.n; i++) seg[i] = this.segments[i];
        return seg;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);


        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

        // Equal slopes
        // Point[] points = {
        //         new Point(1000, 1000),
        //         new Point(5000, 5000),
        //         new Point(3000, 3000),
        //         new Point(9000, 9000),
        // };

        /*
        Point[] points = {
                new Point(1000, 1000),
                new Point(2000, 6000),
                new Point(5000, 2000),
                new Point(3000, 3000),
                new Point(9000, 9000)
        };

        // sort natural order
        StdOut.println(Arrays.toString(points));
        // Arrays.sort(points);
        StdOut.println(Arrays.toString(points));

        // sort by slope made to point (1000, 1000);
        Arrays.sort(points, 0, points.length,
                    points[0].slopeOrder()); // Arrays.sort(a, lo, hi) sorts subarray from a[lo] to a[hi - 1]
        StdOut.println(Arrays.toString(points));

        StdOut.println(new Point(1000, 1000).slopeTo(new Point(1000, 1000))); // 1
        StdOut.println(new Point(1000, 1000).slopeTo(new Point(2000, 6000))); // 4
        StdOut.println(new Point(1000, 1000).slopeTo(new Point(5000, 2000))); // 2
        StdOut.println(new Point(1000, 1000).slopeTo(new Point(3000, 3000))); // 3
        StdOut.println(new Point(1000, 1000).slopeTo(new Point(9000, 9000))); // 3
         */
    }
}

