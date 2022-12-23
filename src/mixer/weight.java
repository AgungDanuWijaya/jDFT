package mixer;

import main.parameter;

/**
 *
 * @author agung
 */
public class weight {

    /**
     * @param args the command line arguments
     */
    public void main(parameter param[], double e, double nks, double degauss) {
        double ef = new efermig().efermig(param, e, nks, degauss);
        wgauss wg = new wgauss();

        w1gauss wg1 = new w1gauss();

        for (int ik = 0; ik < nks; ik++) {
            double energi = 0;
            double sum1 = 0.0;
            for (int ibnd = 0; ibnd < param[0].iband; ibnd++) {
                param[ik].wg[ibnd] = param[ik].wk[ik] * wg.main((ef - param[ik].solusi.eigen_[ibnd]) / degauss);
                energi += degauss * param[ik].wk[ik] * wg1.main((ef - param[ik].solusi.eigen_[ibnd]) / degauss);
            }
            param[ik].demet = energi;
        }

    }

}
