package Handins.Project3_ConvexHull;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class MbC_CH {

    private static int CompareX(Point2D.Double p1, Point2D.Double p2) {
        return Double.compare(p1.x, p2.x);
    }

    private static void InvertIndexes(Point2D.Double[] set) {
        for (Point2D.Double point : set) {
            point.y = -point.y;
            point.x = -point.x;
        }
    }

    public static List<Point2D.Double> wrap(Point2D.Double[] set) {
        final var list = Arrays.asList(set);
        var upperHull = upperHull(list);
        InvertIndexes(set);
        var lowerHull = upperHull(list);
        InvertIndexes(set);

        return Stream.concat(upperHull.stream(), lowerHull.stream()).toList();
    }

    private static List<Point2D.Double> upperHull(List<Point2D.Double> set) { 
        if (set.size() < 3) {
            set.sort(MbC_CH::CompareX);
            return set;
        }

        final double verticalPartition = (set.get(0).x + set.get(1).x)/2;
        final int partitionIndex = partition(set, verticalPartition, false);
        var leftPartition = set.subList(0, partitionIndex);
        var rightPartition = set.subList(partitionIndex, set.size());

        final var bride = LinearProgramming.minimize2D(verticalPartition, set);
        final var bridgeStart = bride[0];
        final var bridgeEnd = bride[1];

        // prune point below the bridge
        leftPartition = leftPartition.subList(0, partition(leftPartition, bridgeStart.x, false));
        rightPartition = rightPartition.subList(partition(rightPartition, bridgeEnd.x, true), rightPartition.size());

        final var leftHull = upperHull(leftPartition);
        final var rightHull = upperHull(rightPartition);

        return Stream.concat(leftHull.stream(), rightHull.stream()).toList();
    }

    private static int partition(List<Point2D.Double> set, double partition, boolean rightIcluded) {
        final int n = set.size();
        int i = 0;
        int j = n;

        while (i < j) {
            final var point = set.get(i);
            if(point.x > partition || (rightIcluded && point.x == partition)) {
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
