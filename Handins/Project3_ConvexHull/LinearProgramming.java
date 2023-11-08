package Handins.Project3_ConvexHull;

import java.awt.geom.Point2D;

public class LinearProgramming {
    /**
     * Computes a value x that minimize c*x while having the bounds
     * @param c
     * @param bounds - array of bounds: bounds[i][0] * x <= bounds[i][1]
     * @return - null when solution is infeasible
     * - Double.POSITIVE_INFINITY | Double.NEGATIVE_INFINITY when unbounded
     * - otherwise a double
     * @throws Exception when solution is degenerate
     */
    public static Double minimize1D(double c, double[][] bounds) throws Exception {
        if(c == 0) {
            throw new Exception("degenerate solution");
        }
        
        double minBound = Double.NEGATIVE_INFINITY;
        double maxBound = Double.POSITIVE_INFINITY;
        for (double[] bound : bounds) {
            double a = bound[0];
            double b = bound[1];

            double xBound = b/a;
            if(a<0) {
                if(xBound > minBound) {
                    minBound = xBound;
                }
            }
            else if (a>0) {
                if(xBound < maxBound) {
                    maxBound = xBound;
                }
            }
            else {
                if(b<0) {
                    throw new Exception("degenerate solution");
                }
            }
        }

        if(minBound > maxBound) {
            return null; // solution infeasible
        }

        return c > 0 ? minBound : maxBound;
    }


    /**
     * @param c
     * @param bounds - array of bounds: bounds[i][0]*x + bounds[i][1]*y <= bounds[i][2]
     * @return
     * @throws Exception when solution is degenerate
     */
    public static Point2D.Double maximizeY_2D(double[][] bounds) throws Exception {
        Point2D.Double v = new Point2D.Double(0, Double.POSITIVE_INFINITY);
        for(int i = 0 ; i < bounds.length; i++) {
            if(bounds[i][0]*v.x + v.y > bounds[i][2])  // bound violates v
            {
                // transform 2d boundaries into 1d boundaries; ones that restrict x axes
                // from bounds[i] (ax + by <= c) we know that y = -ax/b + c/b
                // we substitute y in previous bounds and have 1d problem to minimize
                var bounds1D = new double[i][2];
                for(int j = 0; j < i; j++) {
                    bounds1D[j] = new double[]{
                        bounds[j][0] - bounds[j][1]*bounds[i][0],
                        bounds[j][2] - bounds[j][1]*bounds[i][2],
                    };
                }

                var c1D = bounds[i][0];
                var min_x = minimize1D(c1D, bounds1D);
                if (min_x == null) {
                    return null; // infeasible solution
                }
                var min_y = (bounds[i][2] - bounds[i][0]*min_x);
                v.x = min_x;
                v.y = min_y;
            }

        }

        return v;
    }
}
