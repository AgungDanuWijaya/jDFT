package init_calc;

import tools.array_operation;

/**
 *
 * @author agung
 */
public class ymlr2 {

    public double[][] main(int lmax2, int ng, double[][] g, double[] gg) {
        double eps = 1.0E-9;
        int lmax;
        for (lmax = 0; lmax <= 25; lmax++) {
            if (Math.pow(lmax + 1, 2) == lmax2) {
                break;
            }
        }
        array_operation ao = new array_operation();
        double ylm[][] = new double[ng][lmax2];
        double fpi = 4 * Math.PI;
        if (lmax == 0) {
            ylm = ao.adddot(ylm, Math.sqrt(1.0 / fpi));
            return ylm;
        }
        double Q[][][] = new double[ng][lmax + 1][lmax + 1];
        for (int ig = 0; ig < ng; ig++) {
            double gmod = Math.sqrt(gg[ig]);
            double cost;
            if (gmod < eps) {
                cost = 0.0;
            } else {
                cost = g[ig][2] / gmod;
            }
            double phi;
            if (g[ig][0] > eps) {
                phi = Math.atan(g[ig][1] / g[ig][0]);
            } else if (g[ig][0] < -eps) {
                phi = Math.atan(g[ig][1] / g[ig][0]) + Math.PI;

            } else {
                int sign = 1;
                if (g[ig][1] < 0) {
                    sign = -1;
                }
                phi = Math.abs(Math.PI / 2.0) * sign;
            }
            double sent = Math.sqrt(Math.max(0, 1.0 - (cost * cost)));
            Q[ig][0][0] = 1.0;
            Q[ig][1][0] = cost;
            Q[ig][1][1] = -sent / Math.sqrt(2.0);
            double c = Math.sqrt(3.0 / fpi);
            ylm[ig][0] = Math.sqrt(1.0 / fpi) * Q[ig][0][0];
            ylm[ig][1] = c * Q[ig][1][0];
            ylm[ig][2] = c * Math.sqrt(2.0) * Q[ig][1][1] * Math.cos(phi);
            ylm[ig][3] = c * Math.sqrt(2.0) * Q[ig][1][1] * Math.sin(phi);
            int lm = 4;
            for (int l = 2; l <= lmax; l++) {
                c = Math.sqrt((2.0 * l + 1.0) / fpi);
                for (int m = 0; m <= l - 2; m++) {
                    Q[ig][l][m] = cost * (2 * l - 1) / Math.sqrt((double) (l * l - m * m)) * Q[ig][l - 1][m]
                            - Math.sqrt((double) ((l - 1) * (l - 1) - m * m)) / Math.sqrt((double) (l * l - m * m)) * Q[ig][l - 2][m];
                }
                Q[ig][l][l - 1] = cost * Math.sqrt((double) (2 * l - 1)) * Q[ig][l - 1][l - 1];
                Q[ig][l][l] = -Math.sqrt((double) (2 * l - 1)) / Math.sqrt((double) (2 * l)) * sent * Q[ig][l - 1][l - 1];

                lm = lm + 1;
                ylm[ig][lm - 1] = c * Q[ig][l][0];
                for (int m = 1; m <= l; m++) {
                    ylm[ig][lm + 2 * m - 1 - 1] = c * Math.sqrt(2.0) * Q[ig][l][m] * Math.cos(m * phi);
                    ylm[ig][lm + 2 * m - 1] = c * Math.sqrt(2.0) * Q[ig][l][m] * Math.sin(m * phi);

                }

                lm = lm + 2 * l;
            }
        }
        return ylm;
    }

}
