package energy;

import main.parameter;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class diag_b_sub {

    public void main(parameter param, int ik) {

        array_operation ao = new array_operation();
        double h_psi[][][] = param.h_psi.get(ik);
        double[][][] hal = new double[h_psi.length][h_psi.length][2];
        double[][][] sc = new double[h_psi.length][h_psi.length][2];
        for (int i = 0; i < hal.length; i++) {
            hal[i][i][0] = param.solusi.eigen_[i];
            hal[i][i][1] = 0;
            sc[i][i][0] = 1;
            sc[i][i][1] = 0;
        }
        param.hal.put(ik, hal);
        param.sc.put(ik, sc);      
    }
}
