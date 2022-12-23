package init_calc;

import main.parameter;
import psudo.param_upf;

/**
 *
 * @author agung
 */
public class qvan2 {

    public double[][] main(param_upf param, parameter p_g, int ngy, int ih, int jh,
            double qmod[], double ylmk0[][]) {
        double qg[][] = new double[2][ngy];
        double dq = p_g.dq;
        double dqi = 1.0 / dq;
        double nb = param.indv.get(ih) + 1;
        double mb = param.indv.get(jh) + 1;
        double ijv = 0;
        if (nb >= mb) {
            ijv = nb * (nb - 1) / 2 + mb;
        } else {
            ijv = mb * (mb - 1) / 2 + nb;
        }

        double ivl = param.nhtolm.get(ih) - 1;
        double jvl = param.nhtolm.get(jh) - 1;

        for (int lm = 0; lm < p_g.lpx[(int) ivl][(int) jvl]; lm++) {
            double lp = p_g.lpl[(int) ivl][(int) jvl][lm + 1] + 1;

            double l = 0;
            double sig = 0;
            double ind = 0;
            if (lp == 1) {
                l = 1;
                sig = 1.0;
                ind = 1;
            } else if (lp <= 4) {
                l = 2;
                sig = -1.0;
                ind = 2;
            } else if (lp <= 9) {
                l = 3;
                sig = -1.0;
                ind = 1;
            } else if (lp <= 16) {
                l = 4;
                sig = 1.0;
                ind = 2;
            } else if (lp <= 25) {
                l = 5;
                sig = 1.0;
                ind = 1;
            } else if (lp <= 36) {
                l = 6;
                sig = -1.0;
                ind = 2;
            } else {
                l = 7;
                sig = -1.0;
                ind = 1;
            }
            sig = sig * p_g.ap[(int) lp-1][(int) ivl][(int) jvl];
            
            double qm1 = -1.0;
            double work = 0;
            for (int ig = 0; ig < ngy; ig++) {
                if (Math.abs(qmod[ig] - qm1) > Math.pow(10, -6)) {
                    double qm = qmod[ig] * dqi;
                    double px = qm - (int) (qm);
                    double sixth = 1.0 / 6.0;
                    double ux = 1.0 - px;
                    double vx = 2.0 - px;
                    double wx = 3.0 - px;
                    int i0 = (int) (qm) + 1;
                    int i1 = i0 + 1;
                    int i2 = i0 + 2;
                    int i3 = i0 + 3;
                    double uvx = ux * vx * sixth;
                    double pwx = px * wx * 0.5;//l,iq,ijv

                    work = param.QRAD.get((int) l).get(i0).get((int) ijv) * uvx * wx
                            + param.QRAD.get((int) l).get(i1).get((int) ijv) * pwx * vx
                            - param.QRAD.get((int) l).get(i2).get((int) ijv) * pwx * ux
                            + param.QRAD.get((int) l).get(i3).get((int) ijv) * px * uvx;

                    qm1 = qmod[ig];
                }
                qg[(int) ind - 1][ig] += sig * ylmk0[ig][(int) lp-1] * work;
            }

        }
        return qg;
    }

}
