package init_calc;

import main.parameter;
import psudo.param_upf;
import tools.integral;

/**
 *
 * @author agung
 */
public class init_tab extends Thread {

    param_upf param;
    parameter pg;

    public init_tab(param_upf param, parameter pg) {
        this.param = param;
        this.pg = pg;
    }

    public void run() {
        if (param.name.equals("O")) {
            double r = param.PP_R.get(1);
        }
        double ecutwfc = pg.ecutwfc;
        double cell_factor = pg.cell_factor;
        double dq = pg.dq;
        double pref = 4.0 * Math.PI / Math.sqrt(pg.omega);
        double nqx = ((Math.sqrt(ecutwfc) / dq + 4) * cell_factor);
        double tab[][] = new double[(int) nqx][param.PP_BETA.size()];
        beselj bj = new beselj();
        double k_beta = 0;
        for (int nb = 0; nb < param.PP_BETA.size(); nb++) {
            double k_beta_ = param.PP_BETA_Param.get(nb).get("cutoff_radius_index");
            if (k_beta_ > k_beta) {
                k_beta = k_beta_;
            }
        }
        for (int nb = 0; nb < param.PP_BETA.size(); nb++) {
            double l = param.PP_BETA_Param.get(nb).get("angular_momentum");
            for (int iq = 1; iq <= nqx; iq++) {
                double q = dq * (iq - 1);
                double vchi[] = new double[(int) k_beta];
                double aux_rab[] = new double[(int) k_beta];
                for (int i = 0; i < k_beta; i++) {
                    double r = param.PP_R.get(i);
                    double aux = bj.main(param.PP_R.get(i), q, l);
                    vchi[i] = param.PP_BETA.get(nb).get(i) * aux * param.PP_R.get(i);
                    aux_rab[i] = param.PP_RAB.get(i);
                }
                double vqint = new integral().simpson((int) k_beta, vchi, aux_rab);
                vqint *= pref;
                tab[iq - 1][nb] = vqint;
            }
        }
        param.tab = tab;
    }

}
