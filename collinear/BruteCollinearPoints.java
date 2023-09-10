import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private LineSegment[] segments; // collinear line segments
    private int n = 0;              // number of line segments

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (int p = 1; p < points.length; p++)
            if (points[0].compareTo(points[p]) == 0) throw new IllegalArgumentException();

        // max no. of collinear points is n choose 2
        this.segments = new LineSegment[(points.length * (points.length - 1)) / 2];
        int maxP;

        // iterate through each 4 tuple of points
        for (int i = 0; i < points.length; i++)
            for (int j = 0; j < points.length; j++)
                for (int k = 0; k < points.length; k++)
                    for (int m = 0; m < points.length; m++) {
                        if (i == j && j == k && k == m) continue;

                        // points are collinear if
                        // 1. the slopes between inner segments are equal
                        // 2. only form line segment if 4 points are in asc. order (excl. sub-segments, reverse order)
                        // 3. only pick maximal points
                        double slopePQ = points[i].slopeTo(points[j]);
                        double slopeQR = points[j].slopeTo(points[k]);
                        double slopeRS = points[k].slopeTo(points[m]);
                        boolean equalSlope = (slopePQ == slopeQR && slopeQR == slopeRS);

                        boolean ascOrder = (points[i].compareTo(points[j]) < 0) &&
                                (points[j].compareTo(points[k]) < 0) &&
                                (points[k].compareTo(points[m]) < 0);

                        if (equalSlope && ascOrder)
                            this.segments[n++] = new LineSegment(points[i], points[m]);
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

        /*
        // replicate input6.txt
        Point[] points = {
                new Point(19000, 10000),
                new Point(18000, 10000),
                new Point(32000, 10000),
                new Point(21000, 10000),
                new Point(1234, 5678),
                new Point(14000, 10000)
        };

        BruteCollinearPoints collinearPts = new BruteCollinearPoints(points);
        StdOut.println(collinearPts.numberOfSegments());
        for (LineSegment s : collinearPts.segments()) StdOut.println(s);
        */

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
