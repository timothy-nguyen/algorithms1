/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

// convex hull of a set of N points is the smallest perimetre fence enclosing the points

// Graham Scan
// 1. Choose point p with smallest y co-ordinate
// 2. Sort points by polar angle with p
// 3. Consider points in order; discard unless it creates a counter clockwise turn (using computational geometry)

import java.util.Comparator;

public class Point2D {
    private final double x;
    private final double y;

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static int ccw(Point2D a, Point2D b, Point2D c) {
        // signed area of planar triangle is determinant (or cross product)
        // if signed area > 0 then a -> b -> c is counterclockwise
        // if ... < 0 then ... clockwise
        // if ... = 0 then ... collinear
        double area2 = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
        if (area2 < 0) return -1;      // clockwise
        else if (area2 > 0) return +1; // counter-clockwise
        else return 0;                 // collinear
    }

    private class PolarOrder implements Comparator<Point2D> {
        // Given point p, order points by polar angle they make with p
        // note: angles are relative x-axis in ccw direction

        // If q1 is above p and q2 is below p, then q1 makes the smaller polar angle
        // If q1 is below p and q2 is above p, then q1 makes the bigger polar angle
        // Otherwise ccw() identifies which q1 or q2 makes the larger angle

        public int compare(Point2D q1, Point2D q2) {
            double dy1 = q1.y - y;
            double dy2 = q2.y - y;

            if (dy1 == 0 && dy2 == 0) return 0; // p, q1, q2 horizontal
            else if (dy1 >= 0 && dy2 < 0)
                return -1; // q1 above p; q2 below p; q1 less than q2
            else if (dy2 >= 0 && dy1 < 0)
                return +1; // q2 above p; q1 below p; q1 greater than q2

                // both above or below p so use ccw to determine direction
                // Point2D.this allows access to invoking point within inner class
            else return -ccw(Point2D.this, q1, q2);
        }
    }

    public static void main(String[] args) {

    }
}
