package init_calc;

import main.parameter;
import psudo.param_upf;
import tools.integral;

/**
 *
 * @author agung
 */
public class init_at extends Thread {

    param_upf param;
    parameter gp;

    public init_at(param_upf param, parameter pg) {
        this.param = param;
        this.gp = pg;
    }

    /**
     * @param param
     */
    public void run() {
        if (param.name.equals("O")) {
            double r = param.PP_R.get(1);
        }
        double ecutwfc = gp.ecutwfc;
        double cell_factor = gp.cell_factor;
        double dq = gp.dq;
        double pref = 4 * Math.PI / Math.sqrt(gp.omega);
        double nqx = ((Math.sqrt(ecutwfc) / dq + 4) * cell_factor);
        double tab_at[][] = new double[(int) nqx][param.PP_CHI.size()];
        beselj bj = new beselj();
        for (int nb = 0; nb < param.PP_CHI.size(); nb++) {
            if (param.PP_CHI_Param.get(nb).get("occupation") >= 0) {
                double l = param.PP_CHI_Param.get(nb).get("l");
                for (int iq = 1; iq <= nqx; iq++) {
                    double q = dq * (iq - 1);
                    double vchi[] = new double[(int) param.msh];
                    double aux_rab[] = new double[(int) param.msh];
                    for (int i = 0; i < param.msh; i++) {
                        double r = param.PP_R.get(i);
                        double aux = bj.main(param.PP_R.get(i), q, l);
                        vchi[i] = param.PP_CHI.get(nb).get(i) * aux * param.PP_R.get(i);
                        aux_rab[i] = param.PP_RAB.get(i);
                    }

                    double vqint = new integral().simpson((int) param.msh, vchi, aux_rab);
                    vqint *= pref;
                    tab_at[iq - 1][nb] = vqint;
                }
            }
        }
        param.tab_at = tab_at;
    }

}
