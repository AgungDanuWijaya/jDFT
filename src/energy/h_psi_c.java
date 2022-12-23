package energy;

import main.parameter;
import fftw.fftw;
import java.util.HashMap;
import lapack.JNIJava;
import lapack.gev_object;

import tools.array_operation;
import tools.gev;

/**
 *
 * @author agung
 */
public class h_psi_c {

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
    
        double atomicwfcp[][][] = new double[2 * h_psi.length - param.pan][atomicwfc.get(0).size()][2];
        double h_psip[][][] = new double[2 * h_psi.length - param.pan][atomicwfc.get(0).size()][2];

        for (int i = 0; i < atomicwfcp.length; i++) {
            atomicwfcp[i] = atomicwfc_[i];
            h_psip[i] = h_psi[i];
        }

        param.h_psi_2.put(ik, h_psip);
        double atomicwfc_dulu_panjang[][][] = new double[2 * h_psi.length][atomicwfc.get(0).size()][2];

        for (int i = 0; i < param.pan; i++) {
            atomicwfc_dulu_panjang[i] = param.atomicwfc_l[i];
        }

        for (int i = 0; i < 2 * h_psi.length - param.pan; i++) {
            atomicwfc_dulu_panjang[i + param.pan] = atomicwfc_[i];
        }
        param.atomicwfc_l = atomicwfc_dulu_panjang;

        double[][][] sc_0 = param.sc_1.get(ik);
        double[][][] sc_bawa;

        double aux[][][];
        if (param.usp == 1) {
            aux = new s_psi().main(param, ik);
            aux = ao.adddot(atomicwfcp, aux);
            double da[][][] = param.spsi_c;
            for (int i = 0; i < atomicwfcp.length; i++) {
                da[param.spsi_c.length - atomicwfcp.length + i] = aux[i];

            }
            param.spsi_c = da;
            sc_bawa = ao.time_complex(ao.conjugate(aux), ao.conjugate(ao.complex_transpose(atomicwfc_dulu_panjang)));
        } else {
            sc_bawa = ao.time_complex(ao.conjugate(atomicwfcp), ao.conjugate(ao.complex_transpose(atomicwfc_dulu_panjang)));
        }

        double[][][] hal_0 = param.hal_1.get(ik);
        double[][][] hal_bawa = ao.time_complex(ao.conjugate(h_psip), ao.conjugate(ao.complex_transpose(atomicwfc_dulu_panjang)));
        double[][][] hal_c = new double[h_psi.length * 2][h_psi.length * 2][];
        double[][][] sc_c = new double[h_psi.length * 2][h_psi.length * 2][];

        for (int i = 0; i < hal_0.length; i++) {
            for (int j = 0; j < hal_0.length; j++) {
                hal_c[i][j] = hal_0[i][j];
                sc_c[i][j] = sc_0[i][j];
            }
        }
        for (int i = 0; i < hal_bawa.length; i++) {
            for (int j = 0; j < hal_bawa[i].length; j++) {
                hal_c[param.pan + i][j] = hal_bawa[i][j];
                sc_c[param.pan + i][j] = sc_bawa[i][j];
            }
        }
        param.nbase = param.nbase + param.notcnv;
        for (int i = 0; i < hal_bawa[0].length; i++) {
            for (int j = 0; j < hal_bawa.length; j++) {
                hal_c[i][param.pan + j] = ao.conjugate(hal_c[param.pan + j][i]);
                sc_c[i][param.pan + j] = ao.conjugate(sc_c[param.pan + j][i]);
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
