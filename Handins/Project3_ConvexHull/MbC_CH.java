package Handins.Project3_ConvexHull;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

public class MbC_CH {

    public static List<Point2D.Double> wrap(List<Point2D.Double> set) {
        final double verticalPartition = (set.get(0).x + set.get(1).x)/2;

        final int partitionIndex = partition(set, verticalPartition);
        var lpOutput = LinearProgramming.minimize2D(verticalPartition, set);
        

        //Arrays.sort(lpOutput.index);
        var bridgeStart = set.get(lpOutput.index[0]);
        var bridgeEnd = set.get(lpOutput.index[1]);
        
        int a = 0;

        //var leftHull = wrap(set);
        //var rightHull = wrap(set);

        //leftHull.add(null);
        //leftHull.addAll(rightHull);
        //return leftHull;
        return Arrays.asList(bridgeStart, bridgeEnd);
    }

    private static int partition(List<Point2D.Double> set, double partition) {
        final int n = set.size();
        int i = 0;
        int j = n;

        while (i < j) {
            final var point = set.get(i);
            if(point.x > partition) {
                j--;
                set.set(i, set.get(j));
                set.set(j, point);
            }
            else {
                i++;
            }
        }

        return j;
    }
}
