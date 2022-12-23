package energy;

import main.parameter;
import java.util.HashMap;
import tools.integral;
import tools.array_operation;
import org.apache.commons.math3.special.Erf;
import psudo.param_upf;

/**
 *
 * @author agung
 */
public class v_local {

    public void main(param_upf param, parameter p, int ik) {
        array_operation mo = new array_operation();
        HashMap<Integer, Double> vloc = param.PP_LOCAL;
        HashMap<Integer, Double> grid_rab = param.PP_RAB;
        HashMap<Integer, Double> grid_r = param.PP_R;
        HashMap<Integer, Double> hasil = new HashMap<>();
        double batas = 10;
        int msh = 0;
        integral a = new integral();

        for (Integer key : grid_r.keySet()) {
            if (grid_r.get(key) > batas) {
                msh = key;
                break;
            }
        }
        msh += 1;
        param.msh = msh;
        double aux[] = new double[msh];
        double aux1[] = new double[msh];
        double aux_rab[] = new double[msh];
        int gstart = 0;
        if (p.gl.get(0) < p.batas_kecil) {
            gstart = 1;
            for (int i = 0; i < msh; i++) {
                aux[i] = grid_r.get(i) * (grid_r.get(i) * vloc.get(i) + p.e2 * param.zp);
                aux_rab[i] = grid_rab.get(i);
            }
            hasil.put(0, a.simpson(msh, aux, aux_rab));
        }
        for (int i = 0; i < msh; i++) {
            aux1[i] = grid_r.get(i) * vloc.get(i) + p.e2 * param.zp * Erf.erf(grid_r.get(i));
        }
           
        double fac = param.zp * p.e2 / (p.tpiba * p.tpiba);
        aux = new double[msh];
        for (int igl = gstart; igl < p.gl.size(); igl++) {
            double gx = Math.sqrt(p.gl.get(igl)) * p.tpiba;
            for (int i = 0; i < msh; i++) {
                aux[i] = aux1[i] * Math.sin(gx * grid_r.get(i)) / gx;
            }
            double rf = a.simpson(msh, aux, aux_rab);
            rf = rf - fac * Math.exp(-p.gl.get(igl) * Math.pow(p.tpiba, 2) * 0.25) / p.gl.get(igl);
            hasil.put(igl, rf);
        }

        double[][] vloc_ = new double[p.g.gg.length][2];
        for (int igl = 0; igl < p.gl.size(); igl++) {
            double kon = hasil.get(igl) * 4.0 * Math.PI / p.omega;
            double hj[] = {kon, 0};
            vloc_[igl] = hj;
        }
        param.v_loc = vloc_;
        
    }

}
