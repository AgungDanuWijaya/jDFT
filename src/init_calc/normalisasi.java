package init_calc;

import main.parameter;
import java.util.HashMap;
import psudo.param_upf;
import tools.integral;

/**
 *
 * @author agung
 */
public class normalisasi {

    /**
     * @param args the command line arguments
     */
    public void main(param_upf param, parameter p) {
        HashMap<Integer, Double> rho_at = param.PP_RHOATOM;
        HashMap<Integer, Double> grid_rab = param.PP_RAB;
        HashMap<Integer, Double> grid_r = param.PP_R;

        HashMap<Integer, Double> hasil = new HashMap<>();
        double batas = 10.0;
        int msh = (int) param.mesh_size;
        int kkbeta = (int) param.kkbeta;

        double aux_rab[] = new double[msh];
        for (int i = 0; i < msh; i++) {
            aux_rab[i] = grid_rab.get(i);
        }
        param.aux_rab = aux_rab;
        integral a = new integral();
        for (int iwfc = 0; iwfc < param.PP_CHI.size(); iwfc++) {
            int l = (int) (Double.parseDouble(param.PP_CHI_Param.get(iwfc).get("l") + ""));
            double chi[] = new double[param.PP_CHI.get(iwfc).size()];
            for (int j = 0; j < param.PP_CHI.get(iwfc).size(); j++) {
                chi[j] = param.PP_CHI.get(iwfc).get(j) * param.PP_CHI.get(iwfc).get(j);
            }
            double r = a.simpson(msh, chi, param.aux_rab);
            if (param.usp == 1) {
                double r_[] = new double[param.PP_BETA.size()];

                for (int ibeta = 0; ibeta < param.PP_BETA.size(); ibeta++) {
                    int lll = (int) (Double.parseDouble(param.PP_BETA_Param.get(ibeta).get("angular_momentum") + ""));
                    if (lll == l) {
                        double gi[] = new double[kkbeta];
                        for (int j = 0; j < kkbeta; j++) {
                            gi[j] = param.PP_BETA.get(ibeta).get(j) * param.PP_CHI.get(iwfc).get(j);
                        }
                        r_[ibeta] = a.simpson(kkbeta, gi, param.aux_rab);
                    }
                }

                for (int ibeta1 = 0; ibeta1 < param.PP_BETA.size(); ibeta1++) {
                    for (int ibeta2 = 0; ibeta2 < param.PP_BETA.size(); ibeta2++) {
                        r += param.PP_Q_S[ibeta1][ibeta2] * r_[ibeta1] * r_[ibeta2];
                    }
                }
            }

            r = Math.sqrt(r);
            if (Math.abs(r - 1.0) > p.eps6) {
                HashMap<Integer, Double> temp = new HashMap<>();
                for (int j = 0; j < param.PP_CHI.get(iwfc).size(); j++) {
                    temp.put(j, param.PP_CHI.get(iwfc).get(j) / r);
                }
                param.PP_CHI.replace(iwfc, temp);
            }
        }
    }

}
