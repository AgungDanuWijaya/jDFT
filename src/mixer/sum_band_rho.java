package mixer;

import main.parameter;
import fftw.fftw;
import java.util.HashMap;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class sum_band_rho {

    /**
     * @param args the command line arguments
     */
    public double[][] main(parameter param, int ik) {
        array_operation ao = new array_operation();
        HashMap<Integer, HashMap<Integer, double[]>> atomicwfc = param.atomicwfc.get(ik);
        double efc[][][] = new double[atomicwfc.size()][(int) (param.nr1s * param.nr2s * param.nr3s)][2];

        for (int i = 0; i < atomicwfc.size(); i++) {
            for (int j = 0; j < atomicwfc.get(0).size(); j++) {
                double tem1[] = {atomicwfc.get(i).get(j)[0], atomicwfc.get(i).get(j)[1]};
                double in = param.igk.get(ik).get(j);
                efc[i][param.nl_s.get((int) in) - 1] = tem1;
            }
        }

        double rho[][] = new double[(int) (param.nr1s * param.nr2s * param.nr3s)][2];
        double eband = 0;
        for (int i = 0; i < efc.length; i++) {
            int n[] = {(int) param.nr1s, (int) param.nr2s, (int) param.nr3s, -1};
            double[][] psic = ao.rotate(fftw.fftw(n, efc[i]));
            double ca = param.campur;
            if (i > param.awal_panjang - 1) {
                ca = 0;
            }
            if (param.smar == 1) {
                ca = param.wg[i] / param.omega;
            }
            eband += param.wg[i] * param.solusi.eigen_[i];
            double rho_[][] = get_rho(psic, param, ca);
            rho = ao.adddot(rho, rho_);
        }

        param.eband = eband;

        return rho;

    }

    public double[][] get_rho(double psic[][], parameter param, double ca) {
        double rho[][] = new double[psic.length][2];
        for (int i = 0; i < psic.length; i++) {
            rho[i][0] = (Math.pow(psic[i][0], 2) + Math.pow(psic[i][1], 2)) * ca;
        }
        return rho;
    }

}
