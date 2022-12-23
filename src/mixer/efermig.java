
package mixer;

import main.parameter;
import org.apache.commons.math3.special.Erf;

/**
 *
 * @author agung
 */
public class efermig {

    /**
     * @param args the command line arguments
     */
    public double sumkg(parameter param[], double e, double nks, double degauss) {
        double sumkg = 0.0;
        wgauss wg = new wgauss();

        for (int ik = 0; ik < nks; ik++) {
            double sum1 = 0.0;
            for (int ibnd = 0; ibnd < param[0].iband; ibnd++) {
                sum1 = sum1 + wg.main((e - param[ik].solusi.eigen_[ibnd]) / degauss);
           }
            sumkg = sumkg + param[0].wk[ik] * sum1;

        }
        
        return sumkg;
    }

    public double efermig(parameter param[], double e, double nks, double degauss) {
        double Elw = Math.pow(10, 8);
        double Eup = -Math.pow(10, 8);
        double efermig=0;
        for (int ik = 0; ik < nks; ik++) {
            Elw = Math.min(Elw, param[ik].solusi.eigen_[0]);
            Eup = Math.max(Eup, param[ik].solusi.eigen_[param[ik].iband - 1]);
        }
        Eup = Eup + 2 * degauss;
        Elw = Elw - 2 * degauss;
        double sumkup = sumkg(param, Eup, nks, degauss);
        double sumklw = sumkg(param, Elw, nks, degauss);
        double eps = Math.pow(10, -10);
        if ((sumkup - param[0].tot_muatan) < -eps || (sumklw - param[0].tot_muatan) > eps) {
            System.err.println("erorr efermig" + sumkup + " " + sumkup + " " + param[0].tot_muatan);
        } else {
            for (int i = 0; i < 300; i++) {
                double Ef = (Eup + Elw) / 2.0;
                double sumkmid = sumkg(param, Ef, nks, degauss);
                if (Math.abs(sumkmid - param[0].tot_muatan) < eps) {
                    efermig = Ef;
                    return efermig;
                    
                } else if ((sumkmid - param[0].tot_muatan) < -eps) {
                    Elw = Ef;
                } else {
                    Eup = Ef;
                }

            }
        }
        return efermig;
    }

}
