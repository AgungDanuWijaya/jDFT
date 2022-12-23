package init_calc;

import main.parameter;
import java.util.HashMap;
import psudo.param_upf;
import tools.integral;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class atomic_rho {

    /**
     * @param param
     */
    public void main(param_upf param, parameter p_umum, int ik) {

        array_operation mo = new array_operation();
        HashMap<Integer, Double> rho_at = param.PP_RHOATOM;
        HashMap<Integer, Double> grid_rab = param.PP_RAB;
        HashMap<Integer, Double> grid_r = param.PP_R;

        HashMap<Integer, Double> hasil = new HashMap<>();
        double batas = 10.0;
        int msh = 0;
        double msh_ = 0;

        for (Integer key : grid_r.keySet()) {
            msh = key;
            msh_=key;
            if (grid_r.get(key) > batas) {
                msh = key;
                msh += 1;
                msh_ = 2 * (int) ((msh + 1.0) / 2.0) - 1.0;
                break;
            }
        }     
        param.msh = (int) msh_;
        msh = (int) msh_;
        double aux[] = new double[msh];
        double aux_rab[] = new double[msh];
        for (int i = 0; i < msh; i++) {
            aux[i] = rho_at.get(i);
            aux_rab[i] = grid_rab.get(i);
        }
        param.aux_rab = aux_rab;
        integral a = new integral();
        hasil.put(0, a.simpson(msh, aux, aux_rab));
        aux = new double[msh];
        int gstart = 1;
        for (int igl = gstart; igl < p_umum.gl.size(); igl++) {
            double gx = Math.sqrt(p_umum.gl.get(igl)) * p_umum.tpiba;
            for (int i = 0; i < msh; i++) {
                if (grid_r.get(i) < p_umum.batas_kecil) {
                    aux[i] = rho_at.get(i);
                } else {
                    aux[i] = rho_at.get(i) * Math.sin(gx * grid_r.get(i)) / (gx * grid_r.get(i));
                }
            }
            hasil.put(igl, a.simpson(msh, aux, aux_rab));

        }

        param.rhocg = new double[p_umum.g.gg.length][2];

        double mu = 0;
        for (int i = 0; i < p_umum.g.gg.length; i++) {
            double kon = p_umum.rhoscale * hasil.get(p_umum.index_gg.get(i)) / p_umum.omega;
            param.rhocg[i] = mo.adddot(param.rhocg[i], mo.mdot(p_umum.strf.get(ik).get(i), kon));
        }
    }

}
