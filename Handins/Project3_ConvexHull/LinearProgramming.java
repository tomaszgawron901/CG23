package Handins.Project3_ConvexHull;

import java.awt.geom.Point2D;
import java.util.List;

public class LinearProgramming {
    // TODO: replace it with Double.POSITIVE_INFINITY
    // and update if(bounds[i].x*v.x < bounds[i].y - v.y) so it works correctly with infinities
    private static final double LARGE_NUMBER = 1_000_000_000.0;
    
    public static record Pair<T1, T2> (T1 value, T2 index) {};

    /**
     * Computes a value x that minimize c*x while having the bounds
     * @param c
     * @param bounds - array of bounds: bounds[i][0] * x <= bounds[i][1]
     */
    public static Pair<Double, Integer> minimize1D(double c, double[][] bounds) {
        double optimalValue = -LARGE_NUMBER*c;
        Integer boundIndex = null;
        for (int i = 0; i < bounds.length; i++) {
            double a = bounds[i][0];
            double b = bounds[i][1];

            double xBound = b/a;
            double a_sign = Math.signum(a);
            if(c*a_sign<0) {
                if(xBound*a_sign < optimalValue*a_sign) {
                    optimalValue = xBound;
                    boundIndex = i;
                }
            }
        }

        return new Pair<Double,Integer>(optimalValue, boundIndex);
    }


    /**
     * Modified version of 2D linear programming in 'Excercises\lp\lp2d.java' <p>
     * Solve 2D minimization linear programming problem for c*x + y while having given bounds 
     * @param c
     * @param bounds - list of bounds: bounds[i].x*x + y >= bounds[i].y
     * @return Two bounds on which intersection the solution lies on
     */
    public static Point2D.Double[] minimize2D(double c, List<Point2D.Double> bounds) {
        Point2D.Double v = new Point2D.Double(-LARGE_NUMBER*c, -LARGE_NUMBER);
        var optimalBoundIds = new Integer[2];
        for(int i = 0 ; i < bounds.size(); i++) {
            final var b_i = bounds.get(i);
            if(b_i.x*v.x < b_i.y - v.y)  // bound violates v
            {
                // map 2d boundaries into 1d boundaries
                var bounds1D = new double[i][2];
                for(int j = 0; j < i; j++) {
                    final var b_j = bounds.get(j);
                    bounds1D[j] = new double[]{
                        b_i.x - b_j.x,
                        b_i.y - b_j.y,
                    };
                }

                var c1D = c - b_i.x;
                var output1D = minimize1D(c1D, bounds1D);
                optimalBoundIds = new Integer[] {output1D.index, i};
                v.x= output1D.value;
                v.y = (-b_i.x*v.x+ b_i.y);
            }

        }

        return new Point2D.Double[] {bounds.get(optimalBoundIds[0]), bounds.get(optimalBoundIds[1])};
    }



}
