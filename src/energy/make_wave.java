package energy;

import main.parameter;
import java.util.HashMap;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class make_wave {

    public void main(parameter param, int ik) {
        array_operation ao = new array_operation();
        HashMap<Integer, HashMap<Integer, double[]>> atomicwfc = new HashMap<>();
        HashMap<Integer, HashMap<Integer, double[]>> atomicwfc_d2 = new HashMap<>();
        atomicwfc_d2 = param.atomicwfc_1.get(ik);

        for (int i = 0; i < param.iband; i++) {
            HashMap<Integer, double[]> atomicwfc_sub = new HashMap<>();
            for (int j = 0; j < atomicwfc_d2.get(i).size(); j++) {
                atomicwfc_sub.put(j, ao.mdot(atomicwfc_d2.get(i).get(j), -param.solusi.eigen[i]));
            }
            atomicwfc.put(i, atomicwfc_sub);
        }

        double vec[][][] = new double[param.vec.length][][];

        for (int i = 0; i < param.vec.length; i++) {

            vec[i] = param.solusi.vec[i];

        }
        double ews[] = new double[param.iband];

        double evc__1[][][] = ao.time_complex(ao.conjugate(ao.complex_transpose(param.h_psi.get(ik))), ao.conjugate(ao.complex_transpose(vec)));
        double evc_1[][][] = ao.conjugate(ao.complex_transpose(evc__1));

        double evc__2[][][];
        double evc_2[][][] = null;
        if (param.tannn == 1) {
            double hpsi__[][][] = new double[vec.length][][];
            for (int i = 0; i < vec.length; i++) {
                hpsi__[i] = param.h_psi_l[i];
            }

            evc__2 = ao.time_complex(ao.conjugate(ao.complex_transpose(hpsi__)), ao.conjugate(ao.complex_transpose(vec)));
            evc_2 = ao.conjugate(ao.complex_transpose(evc__2));

            for (int i = 0; i < param.iband; i++) {
                double ew = 0;
                HashMap<Integer, double[]> atomicwfc_sub = new HashMap<>();
                for (int j = 0; j < atomicwfc.get(i).size(); j++) {
                    double tem[] = null;
                    double tem_[] = {atomicwfc.get(i).get(j)[0] + evc_2[i][j][0],
                        atomicwfc.get(i).get(j)[1] + evc_2[i][j][1]};
                    tem = tem_;

                    atomicwfc_sub.put(j, tem);
                    ew += ao.sum(ao.complex_dot(tem, ao.conjugate(tem)));
                }
                atomicwfc.replace(i, atomicwfc_sub);
            }
        } else {

            for (int i = 0; i < param.iband; i++) {
                double ew = 0;
                HashMap<Integer, double[]> atomicwfc_sub = new HashMap<>();
                for (int j = 0; j < atomicwfc.get(i).size(); j++) {
                    double tem[] = null;
                    if (param.scf == 0) {
                        double tem_[] = {atomicwfc.get(i).get(j)[0] + param.h_psi.get(ik)[i][j][0],
                            atomicwfc.get(i).get(j)[1] + param.h_psi.get(ik)[i][j][1]};
                        tem = tem_;
                    } else {

                        if (param.iter == 0) {
                            double tem_[] = {atomicwfc.get(i).get(j)[0] + evc_1[i][j][0],
                                atomicwfc.get(i).get(j)[1] + evc_1[i][j][1]};
                            tem = tem_;
                        } else {
                            double tem_[] = {atomicwfc.get(i).get(j)[0] + param.h_psi.get(ik)[i][j][0],
                                atomicwfc.get(i).get(j)[1] + param.h_psi.get(ik)[i][j][1]};
                            tem = tem_;
                        }
                    }
                    atomicwfc_sub.put(j, tem);
                    ew += ao.sum(ao.complex_dot(tem, ao.conjugate(tem)));
                }
                atomicwfc.replace(i, atomicwfc_sub);
            }
        }

        //g_psi disinin
        double scala = 1.0;

        for (int i = 0; i < param.iband; i++) {
            HashMap<Integer, double[]> atomicwfc_sub = new HashMap<>();
            for (int j = 0; j < atomicwfc.get(i).size(); j++) {
                double x = (param.h_diag.get(ik)[j] - param.solusi.eigen[i] * param.s_diag.get(ik)[j]) * scala;
                double denm = 0.5 * (1.0 + x + Math.sqrt(1.0 + (x - 1) * (x - 1.0))) / scala;
                atomicwfc_sub.put(j, ao.mdot(atomicwfc.get(i).get(j), 1.0 / denm));
            }
            atomicwfc.replace(i, atomicwfc_sub);
        }

        for (int i = 0; i < param.iband; i++) {
            double ew = 0;
            for (int j = 0; j < atomicwfc.get(i).size(); j++) {
                double tem[] = {atomicwfc.get(i).get(j)[0], atomicwfc.get(i).get(j)[1]};

                ew += ao.sum(ao.complex_dot(tem, ao.conjugate(tem)));
            }
            ews[i] = ew;
        }

        for (int i = 0; i < param.iband; i++) {
            HashMap<Integer, double[]> atomicwfc_sub = new HashMap<>();
            for (int j = 0; j < atomicwfc.get(i).size(); j++) {
                atomicwfc_sub.put(j, ao.mdot(atomicwfc.get(i).get(j), 1.0 / Math.sqrt(ews[i])));
            }
            atomicwfc.replace(i, atomicwfc_sub);
        }
        param.atomicwfc_1.put(ik, param.atomicwfc.get(ik));
        param.atomicwfc.replace(ik, atomicwfc);

    }

}
