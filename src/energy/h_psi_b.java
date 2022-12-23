package energy;

import main.parameter;
import fftw.fftw;
import java.util.HashMap;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class h_psi_b {

    public void main(parameter param, int ik) {

        array_operation ao = new array_operation();
        HashMap<Integer, HashMap<Integer, double[]>> atomicwfc = param.atomicwfc.get(ik);
        HashMap<Integer, HashMap<Integer, double[]>> atomicwfc_d2_ = param.atomicwfc_1.get(ik);
        double atomicwfc_[][][] = new double[atomicwfc.size()][atomicwfc.get(0).size()][2];
        double atomicwfc_d2[][][] = new double[atomicwfc.size()][atomicwfc.get(0).size()][2];
        double atomicwfc_l[][][] = new double[atomicwfc.size() * 2][atomicwfc.get(0).size()][2];
        double h_psi[][][] = new double[atomicwfc.size()][atomicwfc.get(0).size()][2];

        double ek[] = param.g2_kin.get(ik);
        double efc[][][] = new double[atomicwfc.size()][(int) (param.nr1s * param.nr2s * param.nr3s)][2];
        
        for (int i = 0; i < atomicwfc.size(); i++) {
            for (int j = 0; j < atomicwfc.get(0).size(); j++) {
                double tem1[] = {atomicwfc.get(i).get(j)[0], atomicwfc.get(i).get(j)[1]};
                double in = param.igk.get(ik).get(j);
                atomicwfc_[i][j] = tem1;
                double tem2[] = {atomicwfc_d2_.get(i).get(j)[0], atomicwfc_d2_.get(i).get(j)[1]};
                atomicwfc_d2[i][j] = tem2;
                efc[i][param.nl_s.get((int) in) - 1] = tem1;
                h_psi[i][j] = ao.mdot(tem1, ek[j]);
            }
        }
        for (int i = 0; i < atomicwfc.size(); i++) {
            atomicwfc_l[i] = atomicwfc_d2[i];
            atomicwfc_l[param.iband + i] = atomicwfc_[i];
        }
        param.atomicwfc_l = atomicwfc_l;

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
        param.h_psi_2.put(ik, h_psi);
        double[][][] sc_bawah;
        double[][][] sc_atas;

        double aux[][][];
        if (param.usp == 1) {
            aux = new s_psi().main(param, ik);
            aux = ao.adddot(aux, atomicwfc_);

            double da[][][] = new double[param.atomicwfc.get(ik).size() * 2][][];
            for (int i = 0; i < aux.length; i++) {
                da[i] = param.spsi[i];

            }
            for (int i = 0; i < aux.length; i++) {
                da[aux.length + i] = aux[i];

            }
            param.spsi_c = da;
            sc_bawah = ao.time_complex(ao.conjugate(aux), ao.conjugate(ao.complex_transpose(atomicwfc_)));
            sc_atas = ao.time_complex(ao.conjugate(aux), ao.conjugate(ao.complex_transpose(atomicwfc_d2)));

        } else {
            sc_bawah = ao.time_complex(ao.conjugate(atomicwfc_), ao.conjugate(ao.complex_transpose(atomicwfc_)));
            sc_atas = ao.time_complex(ao.conjugate(atomicwfc_), ao.conjugate(ao.complex_transpose(atomicwfc_d2)));
        }

        double[][][] hal_bawah = ao.time_complex(ao.conjugate(h_psi), ao.conjugate(ao.complex_transpose(atomicwfc_)));
        double[][][] hal_atas = ao.time_complex(ao.conjugate(h_psi), ao.conjugate(ao.complex_transpose(atomicwfc_d2)));
        double[][][] hal_0 = param.hal.get(ik);
        double[][][] sc_0 = param.sc.get(ik);

        double[][][] hal_c = new double[hal_bawah.length * 2][hal_bawah.length * 2][];
        double[][][] sc_c = new double[sc_bawah.length * 2][sc_bawah.length * 2][];

        for (int i = 0; i < hal_bawah.length; i++) {
            for (int j = 0; j < hal_bawah.length; j++) {
                hal_c[i][j] = hal_0[i][j];
                sc_c[i][j] = sc_0[i][j];

                hal_c[param.iband + i][j] = hal_atas[i][j];
                sc_c[param.iband + i][j] = sc_atas[i][j];
                hal_c[param.iband + i][param.iband + j] = hal_bawah[i][j];
                sc_c[param.iband + i][param.iband + j] = sc_bawah[i][j];
            }
        }

        param.nbase = param.nbase + param.notcnv;
        for (int i = 0; i < hal_bawah.length; i++) {
            for (int j = 0; j < hal_bawah.length; j++) {
                hal_c[i][param.iband + j] = ao.conjugate(hal_c[param.iband + j][i]);
                sc_c[i][param.iband + j] = ao.conjugate(sc_c[param.iband + j][i]);
            }
        }

        for (int i = 0; i < hal_c.length; i++) {
            hal_c[i][i][1] = 0;
            sc_c[i][i][1] = 0;

        }
      
        param.hal.put(ik, hal_c);
        param.sc.put(ik, sc_c);
        param.hal_1.put(ik, hal_c);
        param.sc_1.put(ik, sc_c);

    }

}
