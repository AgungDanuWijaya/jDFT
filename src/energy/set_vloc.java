package energy;

import main.parameter;
import fftw.fftw;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class set_vloc {


    public void main(parameter param) throws InterruptedException {

        array_operation ao = new array_operation();
        double aux[][] = new double[(int) (param.nr1 * param.nr2 * param.nr3)][2];

        for (int i = 0; i < param.upf_data.size(); i++) {
            for (int ng = 0; ng < param.g.gg.length; ng++) {
                aux[param.nl.get(ng) - 1] = ao.adddot(aux[param.nl.get(ng) - 1],
                        ao.mdot(param.strf.get(i).get(ng), param.upf_data.get(param.atom[i]).v_loc[param.index_gg.get(ng)]));
            }
        }
        param.v_of_0 = 0;
        if (param.g.gg[0] < param.eps8) {
            param.v_of_0 = aux[0][0];

        }
        int n[] = {(int) param.nr1, (int) param.nr2, (int) param.nr3, -1};
        double[][] aux_ = fftw.fftw(n, aux);
        double[][] aux__ = ao.rotate(aux_);
    }

}
