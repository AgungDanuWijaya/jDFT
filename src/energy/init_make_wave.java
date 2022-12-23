/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package energy;

import main.parameter;
import java.util.HashMap;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class init_make_wave {

    public void main(parameter param, int ik) {
        array_operation ao = new array_operation();
        double atomicwfc_l[][][] = new double[param.atomicwfc.get(ik).size()][param.atomicwfc.get(ik).get(0).size()][2];
        HashMap<Integer, HashMap<Integer, double[]>> atomicwfc_d2 = new HashMap<>();

        for (int i = 0; i < param.iband; i++) {
            for (int j = 0; j < param.atomicwfc.get(ik).get(i).size(); j++) {
                double tem1[] = {param.atomicwfc.get(ik).get(i).get(j)[0], param.atomicwfc.get(ik).get(i).get(j)[1]};
                atomicwfc_l[i][j] = tem1;
            }
        }
        if (param.usp == 1) {
            atomicwfc_l = ao.time_complex((param.vec), (param.spsi));
            for (int i = 0; i < atomicwfc_l.length; i++) {
                HashMap<Integer, double[]> atomicwfc_d2_ = new HashMap<>();
                for (int j = 0; j < atomicwfc_l[0].length; j++) {
                    double tem1[] = {atomicwfc_l[i][j][0], atomicwfc_l[i][j][1]};
                    atomicwfc_d2_.put(j, tem1);
                }
                atomicwfc_d2.put(i, atomicwfc_d2_);
            }
        }

        param.atomicwfc_l = atomicwfc_l;
        param.top_cut = param.iband;
        if (param.scf == 0) {
            if (param.usp == 1) {
                param.atomicwfc_1.put(ik, atomicwfc_d2);
            } else {
                param.atomicwfc_1.put(ik, param.atomicwfc.get(ik));
            }
        }

    }

}
