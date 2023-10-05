package Excercises.lp;

public class lp2d {
    /**
     * Computes a value x[] that minimize c[0]*x[0] + c[1]*x[1] while having the bounds
     * @param c
     * @param bounds - array of bounds: bounds[i][0]*x[0] + bounds[i][1]*x[1] <= bounds[i][2]
     * @return
     * @throws Exception when solution is degenerate
     */
    public static float[] minimize(float[] c, float[][] bounds) throws Exception {
        var v = new float[] {Float.NEGATIVE_INFINITY*c[0], Float.NEGATIVE_INFINITY*c[1]};
        for(int i = 0 ; i < bounds.length; i++) {
            if(bounds[i][0]*v[0] + bounds[i][1]*v[1] > bounds[i][2])  // bound violates v
            {
                // transform 2d boundaries into 1d boundaries; ones that restrict x axes
                // from bounds[i] (ax + by <= c) we know that y = -ax/b + c/b
                // we substitute y in previous bounds and have 1d problem to minimize
                var bounds1D = new float[i][2];
                for(int j = 0; j < i; j++) {
                    bounds1D[j] = new float[]{
                        bounds[j][0] - bounds[j][1]*bounds[i][0]/bounds[i][1],
                        bounds[j][2] - bounds[j][1]*bounds[i][2]/bounds[i][1],
                    };
                }

                // project v vector onto bound vector -> get scalar.
                // we don't need exact scalar value; only the sign is important
                // therefore we don't need to transform bound vector into unit vector
                // about scalar projection: https://en.wikipedia.org/wiki/Scalar_projection
                var c1D = c[0]*bounds[i][1] + c[1]*bounds[i][0];

                var min_x_0 = lp1d.minimize(c1D, bounds1D);
                if (min_x_0 == null) {
                    return null; // infeasible solution
                }
                var min_x_1 = (bounds[i][2] - bounds[i][0]*min_x_0)/bounds[i][1];
                v = new float[]{min_x_0, min_x_1};
            }

        }

        return v;
    }

    public static void main(String[] args) throws Exception {
        float[] c = {1, 1};
        float[][] bounds = {
            {-1, -0.5F, 1},
            {1, 0.5F, 1},
            {-1, 0.5F, 1},
            {1, -0.5F, 1},
        };
        var out = minimize(c, bounds);
        System.out.println(out[0] + " " + out[1]);
        
    }
}
