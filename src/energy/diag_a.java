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
public class diag_a {

    /**
     * @param args the command line arguments
     */
    public void main(parameter param, int ik) {

        array_operation ao = new array_operation();
        HashMap<Integer, HashMap<Integer, double[]>> atomicwfc = param.atomicwfc.get(ik);
        double atomicwfc_[][][] = new double[atomicwfc.size()][atomicwfc.get(0).size()][2];
        for (int i = 0; i < atomicwfc.size(); i++) {
            for (int j = 0; j < atomicwfc.get(0).size(); j++) {
                double tem1[] = {atomicwfc.get(i).get(j)[0], atomicwfc.get(i).get(j)[1]};
                atomicwfc_[i][j] = tem1;
            }
        }

        double[][][] hal = param.hal.get(ik);
        double[][][] sc = param.sc.get(ik);

        gev_object a = new JNIJava().main(hal, sc, param.iband);
        param.solusi = a;
        param.vec = a.vec;

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
        if (param.scf == 0) {
            param.atomicwfc.replace(ik, atomicwfc_new);
        } else {
            param.atomicwfc_1.put(ik, atomicwfc_new);
        }

    }

}
