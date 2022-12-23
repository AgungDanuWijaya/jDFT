package init_calc;

import Jama.Matrix;
import main.parameter;
import tools.randy;

/**
 *
 * @author agung
 */
public class aainit {

    double r[][];
    double rr[];

    public void main(parameter param) {
        int llx = ((param.lmaxkb + 1) * 2 - 1);
        llx *= llx;
        int lli = param.lmaxkb + 1;
        gen_rndm_r(llx);
        double[][] ylm = new ymlr2().main(llx, this.rr.length, this.r, this.rr);
        Matrix K = new Matrix(ylm);
        double mly[][] = K.inverse().getArray();
        double lpx[][] = new double[lli * lli][lli * lli];
        double lpl[][][] = new double[lli * lli][lli * lli][llx];

        double ap[][][] = new double[llx][lli * lli][lli * lli];
        for (int li = 0; li < lli * lli; li++) {
            for (int lj = 0; lj < lli * lli; lj++) {
                lpx[li][lj] = 0;
                for (int l = 0; l < llx; l++) {
                    double k = compute_ap(l, li, lj, llx, ylm, mly);
                    ap[l][li][lj] = k;
                    if (Math.abs(k) > Math.pow(10, -3)) {
                        lpx[li][lj] += 1;
                        lpl[li][lj][(int) lpx[li][lj]] = l;
                    }
                }
            }
        }
        param.ylm=ylm;
        param.mly=mly;
        param.lpx=lpx;
        param.lpl=lpl;
        param.ap=ap;

    }

    public double compute_ap(int l, int li, int lj, int llx, double ylm[][], double mly[][]) {
        double compute_ap = 0.0;
        for (int ir = 0; ir < llx; ir++) {
            compute_ap += mly[l][ir] * ylm[ir][li] * ylm[ir][lj];
        }
        return compute_ap;
    }

    public void gen_rndm_r(int llx) {
        randy gr = new randy();
        gr.init();
        double r[][] = new double[llx][3];
        double rr[] = new double[llx];
        for (int ir = 0; ir < llx; ir++) {
            double costheta = 2.0 * gr.rand() - 1.0;
            double sintheta = Math.sqrt(1.0 - costheta * costheta);
            double phi = 2.0 * Math.PI * gr.rand();
            r[ir][0] = sintheta * Math.cos(phi);
            r[ir][1] = sintheta * Math.sin(phi);
            r[ir][2] = costheta;
            rr[ir] = 1.0;
        }
        this.rr = rr;
        this.r = r;
    }
}
