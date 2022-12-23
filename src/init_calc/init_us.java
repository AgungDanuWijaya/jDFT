package init_calc;

import com.sun.jna.Native;
import main.parameter;
import java.util.HashMap;
import psudo.param_upf;
import jna.CInterface;
import jna.config;
import tools.integral;

/**
 *
 * @author agung
 */
public class init_us extends Thread {

    param_upf param;
    parameter pg;

    public init_us(param_upf param, parameter pg) {
        this.param = param;
        this.pg = pg;
    }

    /**
     * @param param
     */
    public void run() {

        String libName = new config().url_lib;
        CInterface demo = (CInterface) Native.load(libName, CInterface.class);

        double cell_factor = pg.cell_factor;
        double dq = pg.dq;
        double pref = 4.0 * Math.PI / (pg.omega);

        double nqx = ((Math.sqrt(pg.ecutrho) / dq + 4) * cell_factor);
        double tab[][] = new double[(int) nqx][param.PP_BETA.size()];
        beselj bj = new beselj();
        HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>> qrad = new HashMap<>();

        double tr[] = new double[param.number_projector];
        for (int nb = 1; nb <= param.number_projector; nb++) {
            tr[nb - 1] = (int) (Double.parseDouble(param.PP_BETA_Param.get(nb - 1).get("angular_momentum") + ""));

        }
        double r_[] = new double[param.kkbeta];
        double aux_rab[] = new double[(int) param.kkbeta];
        for (int i = 0; i < r_.length; i++) {
            r_[i] = param.PP_R.get(i);
            aux_rab[i] = param.PP_RAB.get(i);
        }

        int max_ijv = 0;
        for (int l = 0; l < param.nqlc; l++) {
            HashMap<Integer, HashMap<Integer, Double>> qrad_1 = new HashMap<>();
            for (int iq = 1; iq <= nqx; iq++) {
                double q = dq * (iq - 1);

                double vchi[] = new double[(int) param.kkbeta];
                HashMap<Integer, Double> mainn = new HashMap<>();
                for (int nb = 1; nb <= param.number_projector; nb++) {
                    for (int mb = nb; mb <= param.number_projector; mb++) {
                        int ijv = mb * (mb - 1) / 2 + nb;
                        if (max_ijv < ijv) {
                            max_ijv = ijv;
                        }
                        int lll_nb = (int) tr[nb - 1];
                        int lll_mb = (int) tr[mb - 1];
                        if ((l >= Math.abs(lll_nb - lll_mb))
                                & (l <= lll_nb + lll_mb)
                                & (((l + lll_nb + lll_mb) % 2) == 0)) {

                            // perhitungan menggunakan c
                            /*double out[] = new double[r_.length];
                            demo.besel_all(r_, out, r_.length, l, q);

                            for (int i = 0; i < param.kkbeta; i++) {
                                vchi[i] = param.PP_QIJL.get(ijv).get(l).get(i) * out[i];
                            }
                             */
                            //[erhitungan menggunakan java
                            for (int i = 0; i < param.kkbeta; i++) {

                                double aux = bj.main(r_[i], q, l);

                                vchi[i] = param.PP_QIJL.get(ijv).get(l).get(i) * aux;

                            }

                            double vqint = new integral().simpson((int) param.kkbeta, vchi, aux_rab);
                            vqint *= pref;
                            mainn.put(ijv, vqint);
                        }
                    }
                }
                qrad_1.put(iq, mainn);

            }
            qrad.put(l + 1, qrad_1);

        }
        param.QRAD = qrad;
    }

}
