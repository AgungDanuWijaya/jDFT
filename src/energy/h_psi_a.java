package energy;

import main.parameter;
import fftw.fftw;
import java.util.HashMap;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class h_psi_a {

    public void main(parameter param, int ik) {

        array_operation ao = new array_operation();
        HashMap<Integer, HashMap<Integer, double[]>> atomicwfc = param.atomicwfc.get(ik);
        double atomicwfc_[][][] = new double[atomicwfc.size()][atomicwfc.get(0).size()][2];
        double h_psi[][][] = new double[atomicwfc.size()][atomicwfc.get(0).size()][2];
        double ek[] = param.g2_kin.get(ik);

        double efc[][][] = new double[atomicwfc.size()][(int) (param.nr1s * param.nr2s * param.nr3s)][2];
        for (int i = 0; i < atomicwfc.size(); i++) {
            for (int j = 0; j < atomicwfc.get(0).size(); j++) {
                double tem1[] = {atomicwfc.get(i).get(j)[0], atomicwfc.get(i).get(j)[1]};
                double in = param.igk.get(ik).get(j);
                atomicwfc_[i][j] = tem1;
                efc[i][param.nl_s.get((int) in) - 1] = tem1;
                h_psi[i][j] = ao.mdot(tem1, ek[j]);
            }
        }

        double v_rs[] = param.vrs;

        for (int ibnd = 0; ibnd < atomicwfc.size(); ibnd++) {
            int ib = ibnd;
            int n[] = {(int) param.nr1s, (int) param.nr2s, (int) param.nr3s, -1};
            double[][] psic = ao.rotate(fftw.fftw(n, efc[ib]));

            for (int i = 0; i < psic.length; i++) {
                psic[i] = ao.mdot(psic[i], v_rs[i]);
            }

            n[3] = 1;
            double psic_[][] = fftw.fftw(n, psic);

            psic_ = ao.mdot(psic_, 1.0 / (double) (n[0] * n[1] * n[2]));
            for (int i = 0; i < atomicwfc.get(0).size(); i++) {
                double in = param.igk.get(ik).get(i);
                int ijk = param.nl_s.get((int) in) - 1;
                double tem1[] = {psic_[0][ijk], psic_[1][ijk]};
                h_psi[ibnd][i] = ao.adddot(h_psi[ibnd][i], tem1);
            }
        }

        h_psi = ao.adddot(h_psi, param.non_local.get(ik));

        param.h_psi.put(ik, h_psi);
        param.h_psi_1.put(ik, h_psi);
        param.h_psi_2.put(ik, h_psi);
        double[][][] hal = ao.time_complex(ao.conjugate(atomicwfc_), ao.conjugate(ao.complex_transpose(h_psi)));
        double[][][] sc;
        if (param.usp == 1) {
            double aux[][][] = new s_psi().main(param, ik);
            aux = ao.adddot(aux, atomicwfc_);
            param.spsi = aux;
            param.vec = new double[aux.length][aux.length][2];
            for (int i = 0; i < param.vec.length; i++) {
                param.vec[i][i][0] = 1;
                param.vec[i][i][1] = 0;
            }
            sc = ao.time_complex(ao.conjugate(atomicwfc_), ao.conjugate(ao.complex_transpose(aux)));
        } else {
            sc = ao.time_complex(ao.conjugate(atomicwfc_), ao.conjugate(ao.complex_transpose(atomicwfc_)));// untuk selain cu
        }
        for (int i = 0; i < hal.length; i++) {
            hal[i][i][1] = 0;
            sc[i][i][1] = 0;
            for (int j = i + 1; j < hal.length; j++) {
                hal[i][j] = ao.conjugate(hal[j][i]);
                sc[i][j] = ao.conjugate(sc[j][i]);
            }
        }
        param.hal.put(ik, hal);
        param.sc.put(ik, sc);
    }

}
