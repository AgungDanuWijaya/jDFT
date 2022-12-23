package energy;

import main.parameter;
import fftw.fftw;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class driver_v_loc {

    public void main(parameter param) throws InterruptedException {
        for (int i = 0; i < param.upf_data.size(); i++) {
            new v_local().main(param.upf_data.get(param.atom[i]), param, i);
        }

        array_operation ao = new array_operation();
        double v_loc[][] = new double[param.upf_data.get(param.atom[0]).v_loc.length][2];
        double[][] vloc3d = new double[(int) (param.nr1 * param.nr2 * param.nr3)][2];
        for (int i = 0; i < param.upf_data.size(); i++) {
            for (int ng = 0; ng < param.g.gg.length; ng++) {
                vloc3d[param.nl.get(ng) - 1] = ao.adddot(vloc3d[param.nl.get(ng) - 1],
                        ao.mdot(param.strf.get(i).get(ng), param.upf_data.get(param.atom[i]).v_loc[param.index_gg.get(ng)][0]));
            }
        }
        param.v_of_0 = 0;
        if (param.g.gg[0] < param.eps8) {
            param.v_of_0 = vloc3d[0][0];
        }
        int n[] = {(int) param.nr1, (int) param.nr2, (int) param.nr3, -1};
        double[][] aux_ = fftw.fftw(n, vloc3d);
        double[][] aux__ = ao.rotate(aux_);
        param.v_loc = aux_[0];

    }

}
