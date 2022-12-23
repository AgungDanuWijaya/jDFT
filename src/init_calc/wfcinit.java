package init_calc;

import main.parameter;
import java.util.HashMap;

import tools.array_operation;
import tools.randy;

/**
 *
 * @author agung
 */
public class wfcinit {

    /**
     * @param args the command line arguments
     */
    public void main(parameter param, int ik) {
        randy rnd = new randy();
        rnd.init();
        atomic_wfc aw = new atomic_wfc();
        HashMap<Integer, HashMap<Integer, double[]>> atomicwfc = aw.main(param, ik);
        array_operation ao = new array_operation();
        for (int iband = 0; iband < atomicwfc.size(); iband++) {
            for (int ig = 0; ig < param.ngk.get(ik); ig++) {
                double rr = rnd.rand();
                double arg = 2.0 * Math.PI * rnd.rand();
                double com[] = {rr * Math.cos(arg), rr * Math.sin(arg)};
                com = ao.mdot(com, 0.05);
                double wfc[] = ao.adddot(atomicwfc.get(iband).get(ig), ao.complex_dot(atomicwfc.get(iband).get(ig), com));
                if (param.random == 0) {
                    atomicwfc.get(iband).replace(ig, wfc);
                }
            }
        }
        randy rnd_ = new randy();
        rnd_.init();
        if (atomicwfc.size() < param.iband) {
            for (int iband = atomicwfc.size(); iband < param.iband; iband++) {
                HashMap<Integer, double[]> atomicwfc_ = new HashMap<>();

                for (int ig = 0; ig < param.ngk.get(ik); ig++) {
                    double rr = rnd_.rand();
                    double arg = 2.0 * Math.PI * rnd_.rand();

                    double com[] = {rr * Math.cos(arg), rr * Math.sin(arg)};
                    double rt = param.igk.get(ik).get(ig);
                    double ran = Math.pow(param.k_point[ik][0] + param.g.g[(int) rt][0], 2)
                            + Math.pow(param.k_point[ik][1] + param.g.g[(int) rt][1], 2)
                            + Math.pow(param.k_point[ik][2] + param.g.g[(int) rt][2], 2);
                    ran += 1;
                    double wfc[] = ao.mdot(com, 1.0 / ran);
                    atomicwfc_.put(ig, wfc);
                }
                atomicwfc.put(iband, atomicwfc_);
            }

        }
        param.atomicwfc.put(ik, atomicwfc);
    }

}
