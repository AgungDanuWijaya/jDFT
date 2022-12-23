package energy;

import main.parameter;
import fftw.fftw;

/**
 *
 * @author agung
 */
public class v_hartree {

    public void main(parameter param) {
        double fac;
        double rgtot_re;
        double rgtot_im;
        double e_hartre = 0;
        double e2 = 2.0;
        double[][] aux1 = new double[param.g.gg.length][2];
        double aux[][] = new double[(int) (param.nr1 * param.nr2 * param.nr3)][2];
        double v[] = new double[param.rhocr[0].length];
        double fac1 = e2 * 4.0 * Math.PI / Math.pow(param.tpiba, 2);
        for (int i = 1; i < param.g.gg.length; i++) {
            fac = 1.0 / param.g.gg[i];
            rgtot_re = param.rhocg[i][0];
            rgtot_im = param.rhocg[i][1];
            e_hartre += (Math.pow(rgtot_re, 2) + Math.pow(rgtot_im, 2)) * fac;
            aux1[i][0] = rgtot_re * fac * fac1;
            aux1[i][1] = rgtot_im * fac * fac1;          
        }
        e_hartre*=fac1;
        e_hartre*=0.5*param.omega;
        for (int i = 0; i < param.g.gg.length; i++) {
            double dt[] = {aux1[i][0], aux1[i][1]};
            aux[param.nl.get(i) - 1] = dt;            
        }
        int n[] = {(int) param.nr1, (int) param.nr2, (int) param.nr3, -1};
        double[][] aux_ = fftw.fftw(n, aux);
        for (int i = 0; i < aux_[0].length; i++) {
            v[i] = (aux_[0][i]);
        }
        param.ehart=e_hartre;        
        param.v_h = v;
    }

}
