
package init_grid;

import main.parameter;
import java.util.HashMap;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class structur_factor {

    /**
     * @param param
     */
    public void main(parameter param) {
        int nat = param.nat;
        double pos[][] = param.pos;
        double bg[][] = param.bg;
        HashMap<Integer, HashMap<Integer, double[]>> eigts1_ = new HashMap<>();
        HashMap<Integer, HashMap<Integer, double[]>> eigts2_ = new HashMap<>();
        HashMap<Integer, HashMap<Integer, double[]>> eigts3_ = new HashMap<>();

        array_operation mo = new array_operation();
        for (int i = 0; i < nat; i++) {
            double bgtau[] = new double[3];
            for (int ipol = 0; ipol < 3; ipol++) {
                bgtau[ipol] = bg[ipol][0] * pos[i][0]
                        + bg[ipol][1] * pos[i][1]
                        + bg[ipol][2] * pos[i][2];
            }

            HashMap<Integer, double[]> eigts1 = new HashMap<>();
            HashMap<Integer, double[]> eigts2 = new HashMap<>();
            HashMap<Integer, double[]> eigts3 = new HashMap<>();
            for (int j = -(int) param.nr1; j <= (int) param.nr1; j++) {
                double arg = 2 * Math.PI * j * bgtau[0];
                double com[] = {Math.cos(arg), -Math.sin(arg)};
                eigts1.put(j, com);
            }
            for (int j = -(int) param.nr2; j <= (int) param.nr2; j++) {
                double arg = 2 * Math.PI * j * bgtau[1];
                double com[] = {Math.cos(arg), -Math.sin(arg)};
                eigts2.put(j, com);
            }
            for (int j = -(int) param.nr3; j <= (int) param.nr3; j++) {
                double arg = 2 * Math.PI * j * bgtau[2];
                double com[] = {Math.cos(arg), -Math.sin(arg)};
                eigts3.put(j, com);
            }
            eigts1_.put(i, eigts1);
            eigts2_.put(i, eigts2);
            eigts3_.put(i, eigts3);
        }
        param.eigts1_ = eigts1_;
        param.eigts2_ = eigts2_;
        param.eigts3_ = eigts3_;

        for (int ik = 0; ik < param.atom.length; ik++) {
            HashMap<Integer, double[]> strf = new HashMap<>();
            for (int i = 0; i < param.g.gg.length; i++) {
                double z[] = {0, 0};
                double z_[];
                
                for (int j = 0; j < nat; j++) {
                    if (param.atom[ik].equals(param.atom_p[j])) {
                        z_ = eigts1_.get(j).get((int) param.mill[i][0]);
                        z_ = mo.complex_dot(z_, eigts2_.get(j).get((int) param.mill[i][1]));
                        z_ = mo.complex_dot(z_, eigts3_.get(j).get((int) param.mill[i][2]));
                        z = mo.adddot(z, z_);
                    }
                }
                strf.put(i, z);
                
            }
            param.strf.put(ik, strf);
        }
    }

}
