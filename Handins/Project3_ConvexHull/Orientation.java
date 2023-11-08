package Handins.Project3_ConvexHull;

import java.awt.geom.Point2D;

/**
 * -   right turn <p>
 * +   left turn <p>
 * 0   no turn <p>
 */
public class Orientation {
    public static double GetOrientation_1(Point2D.Double p1, Point2D.Double p2, Point2D.Double p3) {

        return p1.x*(p2.y - p3.y) + p2.x*(p3.y - p1.y) + p3.x*(p1.y - p2.y);
    }

    public static double GetOrientation_2(Point2D.Double p1, Point2D.Double p2, Point2D.Double p3) {

        return p1.x*p2.y - p1.x*p3.y + p2.x*p3.y - p2.x*p1.y + p3.x*p1.y - p3.x*p2.y;
    }

    public static double GetOrientation_3(Point2D.Double p1, Point2D.Double p2, Point2D.Double p3) {

        return p1.x*p2.y + p2.x*p3.y + p3.x*p1.y - p1.x*p3.y - p2.x*p1.y - p3.x*p2.y;
    }
}
