package Handins.Project3_ConvexHull;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class INC_CH {
    public static List<Point2D.Double> wrap(Point2D.Double[] set) {
        set = set.clone();
        Comparator<Point2D.Double> comparator = Comparator
            .comparingDouble((Point2D.Double p) -> p.x)
            .thenComparingDouble((Point2D.Double p) -> p.y);
        Arrays.sort(set, comparator);

        var bound = new Point2D.Double[set.length+2];
        bound[0] = set[0];
        bound[1] = set[1];
        int j = 1;

        for (int i = 2; i < set.length; i++) {
            bound[++j] = set[i];
            while(j > 1  // at lest 3 elements
                && Orientation.GetOrientation_1(bound[j-2], bound[j-1], bound[j]) >= 0)  // is not right turn
            {
                // remove middle point
                bound[j-1] = bound[j];
                j--;
            }
        }
        
        int k = j+1;
        // bound[k-1] = set[set.length-1] is already in the array
        bound[k] = set[set.length-2];

        for (int i = set.length-3; i >= 0; i--) {
            bound[++k] = set[i];
            while(k-j > 1  // at lest 3 elements
                && Orientation.GetOrientation_1(bound[k-2], bound[k-1], bound[k]) >= 0)  // is not right turn
            {
                // remove middle point
                bound[k-1] = bound[k];
                k--;
            }
        }

        // bound[0] and bound[-1] are duplicates
        // subList toIndex is exclusive therefore we don't return the duplicate
        return Arrays.asList(bound).subList(0, k);
    }
}
