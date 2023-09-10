/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (this.y == that.y) return +0.0;                     // point is horizontal
        if (this.x == that.x) return Double.POSITIVE_INFINITY; // point is vertical
        if (this.compareTo(that) == 0) return Double.NEGATIVE_INFINITY;
        return ((double) (that.y - this.y) / (that.x - this.x));
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        int compareY = compareCoord(this.y, that.y);
        if (compareY != 0) {
            // Non-tied y
            return compareY;
        }
        else {
            int compareX = compareCoord(this.x, that.x);
            return compareX;
        }
    }

    private int compareCoord(int p, int q) {
        if (p > q) return 1;
        else if (p < q) return -1;
        else return 0;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /*
        return new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return Double.compare(slopeTo(o1), slopeTo(o2));
            }
        };
        */
        return new SlopeOrder();
    }

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point q1, Point q2) {
            // which point has smaller slope relative to invoking point p
            double m1 = slopeTo(q1);
            double m2 = slopeTo(q2);
            return Double.compare(m1, m2);
        }
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        // slope = 1
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 2);
        StdOut.println(p1.slopeTo(p2));

        // compare by y coord
        StdOut.println(p1.compareTo(p2));

        // sort array of points
        Point q0 = new Point(0, 0);
        Point q1 = new Point(+2, +1); // slope +0.5
        Point q2 = new Point(-1, +1); // slope -1
        Point q3 = new Point(-1, -1); // slope +1
        Point q4 = new Point(+3, +3); // slope +3

        Point[] pointArray = { q1, q2, q3, q4 };
        StdOut.println("Pre-sorted Array:");
        for (Point p : pointArray) StdOut.println(p);
        Arrays.sort(pointArray, 0, pointArray.length, q0.slopeOrder());
        StdOut.println("Sorted Array:");
        for (Point p : pointArray) StdOut.println(p);

        // Check compareTo method
        StdOut.println(new Point(18000, 10000).compareTo(new Point(32000, 10000))); // -1
        StdOut.println(new Point(40000, 10000).compareTo(new Point(32000, 10000))); // +1
        StdOut.println(new Point(1234, 5678).compareTo(new Point(32000, 10000)));   // -1
        StdOut.println(new Point(1234, 5678).compareTo(new Point(1234, 5678)));     // 0

        StdOut.println(new Point(18000, 10000).compareTo(new Point(19000, 10000))); // -1
        StdOut.println(new Point(19000, 10000).compareTo(new Point(21000, 10000))); // -1
        StdOut.println(new Point(21000, 10000).compareTo(new Point(32000, 10000))); // -1
    }
}
