
package mixer;

import main.parameter;
import fftw.fftw;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class interpolate {

    public void main(parameter param, int ik) {
        array_operation ao = new array_operation();
        double vrs[] = ao.adddot(param.v_dft, param.v_h);
        vrs = ao.adddot(vrs, param.v_loc);
        double psic[][] = new double[(int) (param.nr1 * param.nr2 * param.nr3)][2];
        for (int j = 0; j < vrs.length; j++) {
            double tem1[] = {vrs[j], 0};
            psic[j] = tem1;
        }
        int n_[] = {(int) param.nr1, (int) param.nr2, (int) param.nr3, 1};
        double[][] psic_ = ao.rotate(fftw.fftw(n_, psic));
        double psic__[][] = new double[(int) (param.nr1s * param.nr2s * param.nr3s)][2];
        psic_ = ao.mdot(psic_, 1.0 / (param.nr1 * param.nr2 * param.nr3));
        int ngm = Math.min(param.ngm_s,param.ngm);
        for (int j = 0; j < ngm; j++) {
            int in_ = param.nl.get(j) - 1;
            int in = param.nl_s.get(j) - 1;
            double tem1[] = {psic_[in_][0], psic_[in_][1]};
            psic__[in] = tem1;
        }
        int n[] = {(int) param.nr1s, (int) param.nr2s, (int) param.nr3s, -1};
        double[][] psic_r = fftw.fftw(n, psic__);
        for (int i = 0; i < psic_r[0].length; i++) {
           vrs[i]=psic_r[0][i];
        }
        param.vrs=vrs;

    }

}
