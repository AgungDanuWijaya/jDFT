package mixer;

import main.parameter;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class rho_ddot {

    /**
     * @param args the command line arguments
     */
    public double main(double[][] rhog1, double[][] rhog2, parameter param) {
        int start = 1;
        double rho_ddot[] = {0, 0};
        array_operation ao = new array_operation();
        for (int i = start; i < param.ngm_s; i++) {
            rho_ddot = ao.adddot(rho_ddot, ao.mdot(ao.complex_dot(ao.conjugate(rhog1[i]), rhog2[i]), 1.0 / param.g.gg[i]));
        }
        double fac = param.e2 * 4 * Math.PI / Math.pow(param.tpiba, 2);
        rho_ddot = ao.mdot(rho_ddot, fac);
        rho_ddot = ao.mdot(rho_ddot, param.omega * 0.5);
        return rho_ddot[0];
    }

}
