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
        var upperHull = upperHull(set);
        InvertIndexes(set);
        var lowerHull = upperHull(set);
        InvertIndexes(set);

        return Stream.concat(upperHull.stream(), lowerHull.stream()).toList();
    }

    private static List<Point2D.Double> upperHull(Point2D.Double[] set) {
        if (set.length < 3) {
            Arrays.sort(set, MbC_CH::CompareX);
            return Arrays.asList(set);
        }

        final double verticalPartition = (set[0].x + set[1].x)/2;
        var bride = LinearProgramming.minimize2D(verticalPartition, set);
        
        Arrays.sort(bride, MbC_CH::CompareX);
        var bridgeStart = bride[0];
        var bridgeEnd = bride[1];

        var leftHull = upperHull(Arrays.stream(set)
            .filter(p -> p.x < bridgeStart.x || p == bridgeStart)
            .toArray(Point2D.Double[]::new)
        );

        var rightHull = upperHull(Arrays.stream(set)
            .filter(p -> p.x > bridgeEnd.x || p == bridgeEnd)
            .toArray(Point2D.Double[]::new)
        );

        return Stream.concat(leftHull.stream(), rightHull.stream()).toList();
    }
}
