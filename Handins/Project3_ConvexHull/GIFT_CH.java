package Handins.Project3_ConvexHull;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class GIFT_CH {
    public static List<Point2D.Double> wrap(Point2D.Double[] array) {
        final int n = array.length;
        final ArrayList<Point2D.Double> output = new ArrayList<Point2D.Double>();

        var lm = getLeftMostPointIndex(array);

        var p = lm;
        do {
            output.add(array[p]);

            var q = (p+1)%n; // could be just 1 but this way we handle sets with length of 2
            for (int i = 0; i < n; i++) {
                // if not right turn
                if (Orientation.GetOrientation_1(array[p], array[q], array[i]) > 0F) {
                    q = i;
                }
            }
            p = q;

        } while (lm != p);


        return output;
    }

    private static int getLeftMostPointIndex(Point2D.Double[] array) {
        var mostLeft = 0;
        for(int i=1; i < array.length; i++ ) {
            if (array[i].x < array[mostLeft].x) {
                mostLeft = i;
            }
        }
        return mostLeft;
    }
}
