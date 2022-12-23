
package mixer;

import main.parameter;
import java.util.HashMap;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class calbec {

    public double[][][] main(parameter param, int ik) {
        HashMap<Integer, HashMap<Integer, double[]>> atomicwfc = param.atomicwfc.get(ik);
        HashMap<Integer, HashMap<Integer, double[]>> vkb = param.vkb.get(ik);
        double vkb_[][][] = new double[vkb.size()][vkb.get(0).size()][2];
        double atomicwfc_[][][] = new double[atomicwfc.size()][atomicwfc.get(0).size()][2];
        for (int i = 0; i < vkb.size(); i++) {
            for (int j = 0; j < vkb.get(0).size(); j++) {
                double tem[] = {vkb.get(i).get(j)[0], vkb.get(i).get(j)[1]};
                vkb_[i][j] = tem;
            }
        }

        for (int i = 0; i < atomicwfc.size(); i++) {
            for (int j = 0; j < atomicwfc.get(0).size(); j++) {
                double tem1[] = {atomicwfc.get(i).get(j)[0], atomicwfc.get(i).get(j)[1]};
                atomicwfc_[i][j] = tem1;
            }
        }
        array_operation ao = new array_operation();
        double bec[][][] = ao.time_complex(ao.conjugate(vkb_), ao.conjugate(ao.complex_transpose(atomicwfc_)));

        return bec;
    }

}
