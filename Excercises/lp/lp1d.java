package Excercises.lp;

public class lp1d {

    /**
     * Computes a value x that minimize c*x while having the bounds
     * @param c
     * @param bounds - array of bounds: bounds[i][0] * x <= bounds[i][1]
     * @return - null when solution is infeasible
     * - Float.POSITIVE_INFINITY | Float.NEGATIVE_INFINITY when unbounded
     * - otherwise a float
     * @throws Exception when solution is degenerate
     */
    public static Float minimize(float c, float[][] bounds) throws Exception {
        if(c == 0) {
            throw new Exception("degenerate solution");
        }

        float minBound = Float.NEGATIVE_INFINITY;
        float maxBound = Float.POSITIVE_INFINITY;
        for (float[] bound : bounds) {
            float a = bound[0];
            float b = bound[1];

            float xBound = b/a;
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

    public static void main(String[] args) throws Exception {
        float c = 1;
        float[][] bounds = {
            {1, -4},
            {-1, -4}
        };

        System.out.println(minimize(c, bounds));
        
    }
}
