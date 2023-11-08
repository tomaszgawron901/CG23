package Handins.Project3_ConvexHull;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

public class MbC_CH {

    public static List<Point2D.Double> wrap(List<Point2D.Double> set) {
        final var verticalPartition = (set.get(0).x + set.get(1).x)/2;
        final int partitionIndex = partition(set, verticalPartition);

        double[][] constrains = set.stream()
            .map(p -> new double[] { -(p.x - verticalPartition), 1., -p.y })
            .toArray(double[][]::new);

        
        String s = "";
        for (double[] ds : constrains) {
            s += String.format("%,.4f", ds[0]) + "*x + y <= " + String.format("%,.4f", ds[2]) + ", ";
        }
        System.out.println(s);

        try {
            var aaa = LinearProgramming.maximizeY_2D(constrains);
            int a = 0;
        }
        catch(Exception e) {
        }
        



        var leftHull = wrap(set);
        var rightHull = wrap(set);

        leftHull.add(null);
        leftHull.addAll(rightHull);
        return leftHull;
    }

    private static int partition(List<Point2D.Double> set, double partition) {
        final int n = set.size();
        int i = 0;
        int j = n-1;

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
