package Handins.Project3_ConvexHull;

import java.awt.geom.Point2D;

public class LinearProgramming {
    public static class Pair<T1, T2> {
        public T1 value;
        public T2 index;
        public Pair(T1 value, T2 index) {
            this.value = value;
            this.index = index;
        }
    }

    /**
     * Computes a value x that minimize c*x while having the bounds
     * @param c
     * @param bounds - array of bounds: bounds[i][0] * x <= bounds[i][1]
     */
    public static Pair<Double, Integer> minimize1D(double c, double[][] bounds) {
        if(c == 0) {
            throw new Error("degenerate solution");
        }
        
        double optimalValue = Double.NEGATIVE_INFINITY*c;
        Integer boundIndex = null;
        double oppositeValue = Double.POSITIVE_INFINITY*c;
        for (int i = 0; i < bounds.length; i++) {
            double a = bounds[i][0];
            double b = bounds[i][1];

            double xBound = b/a;
            if(a*c<0) {
                if(xBound < optimalValue) {
                    optimalValue = xBound;
                    boundIndex = i;
                }
            }
            else if (a*c>0) {
                if(xBound > oppositeValue) {
                    oppositeValue = xBound;
                }
            }
            else {
                if(b<0) {
                    throw new Error("degenerate solution");
                }
            }
        }

        if(optimalValue*c > oppositeValue*c) {
            return null; // solution infeasible
        }

        return new Pair<Double,Integer>(optimalValue, boundIndex);
    }


    /**
     * @param c
     * @param bounds - array of bounds: bounds[i][0]*x + bounds[i][1]*y <= bounds[i][2]
     * @return
     * @throws Exception when solution is degenerate
     */
    public static Pair<Point2D.Double, Integer[]> maximizeY_2D(double[][] bounds) throws Exception {
        Point2D.Double v = new Point2D.Double(0, Double.POSITIVE_INFINITY);
        var optimalBoundIds = new Integer[2];
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
                var output1D = minimize1D(c1D, bounds1D);
                if (output1D == null) {
                    return null; // infeasible solution
                }
                optimalBoundIds = new Integer[] {output1D.index, i};
                var min_x = output1D.value;
                var min_y = (bounds[i][2] - bounds[i][0]*min_x);
                v.x = min_x;
                v.y = min_y;
            }

        }

        return new Pair<Point2D.Double,Integer[]>(v, optimalBoundIds);
    }
}
