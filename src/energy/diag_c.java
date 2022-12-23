package energy;

import main.parameter;
import java.util.HashMap;
import lapack.JNIJava;
import lapack.gev_object;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class diag_c {

    public void main(parameter param, int ik) {

        array_operation ao = new array_operation();
        double atomicwfc_[][][] = param.atomicwfc_l;
        double[][][] hal = param.hal.get(ik);
        double[][][] sc = param.sc.get(ik);

        int r = (int) (hal.length / 2.0) - param.top_cut;

        int pan = hal.length - r;
        int len = param.pan - param.iband;
        r = hal.length - (pan + len);

        double[][][] hal_ = new double[hal.length - r][hal.length - r][];
        double[][][] sc_ = new double[hal.length - r][hal.length - r][];

        for (int i = 0; i < hal.length - r; i++) {
            for (int j = 0; j < hal.length - r; j++) {
                sc_[i][j] = sc[i][j];
                hal_[i][j] = hal[i][j];
            }
        }

        gev_object a = new JNIJava().main(hal_, sc_, param.iband);
        double h_psi[][][] = param.h_psi_l;

        for (int i = 0; i < param.h_psi_2.get(ik).length; i++) {
            h_psi[param.pan + i] = param.h_psi_2.get(ik)[i];

        }
        param.h_psi_l = h_psi;

        double eigen[] = new double[param.iband];
        for (int i = 0; i < param.iband; i++) {
            eigen[i] = param.solusi.eigen_[i];
        }
        boolean con[] = new boolean[param.iband];
        int np = -1;
        int index[] = new int[param.iband];
        int cond = 0;
        for (int i = 0; i < param.iband; i++) {
            index[i] = i;
            if (Math.abs(eigen[i] - a.eigen[i]) > param.con) {
                np = np + 1;
                if (np != i) {
                    index[np] = i;
                }
                param.solusi.eigen[np] = a.eigen[i];
                cond += 1;
            }
        }
        param.non_conv_0 = param.non_conv;
        param.non_conv = cond;
        param.top_cut = np + 1;
        param.solusi.eigen_ = a.eigen;
        param.solusi.vec = a.vec;
        param.vec = a.vec;

        param.notcnv = cond;
        if (param.notcnv == 0) {
            double evc__[][][] = ao.time_complex(ao.conjugate(ao.complex_transpose(atomicwfc_)), ao.conjugate(ao.complex_transpose(a.vec)));
            double evc_[][][] = ao.conjugate(ao.complex_transpose(evc__));
            HashMap<Integer, HashMap<Integer, double[]>> atomicwfc_new = new HashMap<>();
            for (int i = 0; i < param.iband; i++) {
                HashMap<Integer, double[]> atomicwfc_sub = new HashMap<>();
                for (int j = 0; j < evc_[0].length; j++) {
                    atomicwfc_sub.put(j, evc_[i][j]);
                }

                atomicwfc_new.put(i, atomicwfc_sub);
            }
            param.atomicwfc.replace(ik, atomicwfc_new);
        } else {
            if (param.nbase + cond > param.iband * 2) {
                param.nbase = param.iband;

                double evc__[][][] = ao.time_complex(ao.conjugate(ao.complex_transpose(atomicwfc_)), ao.conjugate(ao.complex_transpose(a.vec)));
                double evc_[][][] = ao.conjugate(ao.complex_transpose(evc__));
                HashMap<Integer, HashMap<Integer, double[]>> atomicwfc_new = new HashMap<>();
                for (int i = 0; i < param.iband; i++) {
                    HashMap<Integer, double[]> atomicwfc_sub = new HashMap<>();
                    for (int j = 0; j < evc_[0].length; j++) {
                        atomicwfc_sub.put(j, evc_[i][j]);
                    }

                    atomicwfc_new.put(i, atomicwfc_sub);
                }
                param.atomicwfc.replace(ik, atomicwfc_new);

                if (param.usp == 1) {
                    evc__ = ao.time_complex(ao.conjugate(ao.complex_transpose(param.spsi_c)), ao.conjugate(ao.complex_transpose(a.vec)));
                    evc_ = ao.conjugate(ao.complex_transpose(evc__));

                    for (int i = 0; i < param.spsi.length; i++) {
                        param.spsi[i] = evc_[i];

                    }
                }

                double evc__1[][][] = ao.time_complex(ao.conjugate(ao.complex_transpose(h_psi)), ao.conjugate(ao.complex_transpose(a.vec)));
                double evc_1[][][] = ao.conjugate(ao.complex_transpose(evc__1));
                HashMap<Integer, HashMap<Integer, double[]>> atomicwfc_atas = new HashMap<>();
                for (int i = param.top_cut; i < param.iband; i++) {
                    HashMap<Integer, double[]> atomicwfc_sub = new HashMap<>();
                    for (int j = 0; j < evc_1[0].length; j++) {
                        atomicwfc_sub.put(j, evc_1[i][j]);
                    }
                    atomicwfc_atas.put(i, atomicwfc_sub);
                }
                for (int i = 0; i < param.top_cut; i++) {
                    HashMap<Integer, double[]> atomicwfc_sub = new HashMap<>();
                    for (int j = 0; j < evc_[0].length; j++) {
                        atomicwfc_sub.put(j, evc_[index[i]][j]);
                    }
                    atomicwfc_atas.put(i, atomicwfc_sub);
                }
                param.atomicwfc_1.put(ik, atomicwfc_atas);

                double h_psi_[][][] = new double[atomicwfc_.length / 2][][];
                double h_psi__[][][] = new double[atomicwfc_.length / 2][][];
                for (int i = 0; i < h_psi_.length; i++) {
                    h_psi_[i] = evc_1[index[i]];
                    h_psi__[i] = evc_1[i];
                }
                param.h_psi.put(ik, h_psi_);
                param.h_psi_1.put(ik, h_psi__);
                new diag_b_sub().main(param, ik);
                param.tannn = 0;
            } else {
                param.tannn = 1;
                double vec[][][] = new double[a.vec.length][a.vec[0].length][2];
                for (int i = 0; i < a.vec.length; i++) {
                    vec[i] = a.vec[i];
                }
                for (int i = 0; i < index.length; i++) {
                    vec[i] = ao.copy(a.vec[index[i]]);
                }
                param.solusi.vec = vec;
                param.vec = vec;

                double evc__[][][] = ao.time_complex(ao.conjugate(ao.complex_transpose(atomicwfc_)), ao.conjugate(ao.complex_transpose(vec)));
                double evc_[][][] = ao.conjugate(ao.complex_transpose(evc__));

                if (param.usp == 1) {
                    evc__ = ao.time_complex(ao.conjugate(ao.complex_transpose(param.spsi_c)), ao.conjugate(ao.complex_transpose(vec)));
                    evc_ = ao.conjugate(ao.complex_transpose(evc__));

                }
                HashMap<Integer, HashMap<Integer, double[]>> atomicwfc_new = new HashMap<>();
                for (int i = 0; i < param.iband; i++) {
                    HashMap<Integer, double[]> atomicwfc_sub = new HashMap<>();
                    for (int j = 0; j < evc_[0].length; j++) {
                        atomicwfc_sub.put(j, evc_[i][j]);
                    }
                    atomicwfc_new.put(i, atomicwfc_sub);
                }

                param.atomicwfc_1.put(ik, atomicwfc_new);
            }
        }
    }

}
