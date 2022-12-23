package energy;

import main.parameter;
import java.util.HashMap;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class s_psi {

    public double[][][] main(parameter param, int ik) {
        double bec[][][][] = new double[param.nat][][][];
        int index[] = new int[param.nat];
        for (int j = 0; j < param.nat; j++) {
            for (int i = 0; i < j; i++) {
                int in = param.upf_data.get(param.atom_p[i]).qq_at_.length;
                index[j] += in;
            }
        }
        for (int j = 0; j < param.nat; j++) {
            int in = param.upf_data.get(param.atom_p[j]).qq_at_.length;
            double bec_t[][][] = new double[in][][];
            for (int i = 0; i < in; i++) {
                int yt = ((index[j]) + i);
                bec_t[i] = param.bec.get(ik)[yt];
            }
            bec[j] = bec_t;
        }
        array_operation ao = new array_operation();
        for (int i = 0; i < bec.length; i++) {
            bec[i] = ao.time_complex(param.upf_data.get(param.atom_p[i]).qq_at_, bec[i]);
        }

     
        double bec_[][][] = new double[param.bec.get(ik).length][][];

        for (int j = 0; j < param.nat; j++) {
            int in = param.upf_data.get(param.atom_p[j]).qq_at_.length;
            for (int i = 0; i < in; i++) {
                int yt = ((index[j]) + i);
                bec_[yt] = bec[j][i];
            }

        }
     
        HashMap<Integer, HashMap<Integer, double[]>> vkb = param.vkb.get(ik);
        double vkb_[][][] = new double[vkb.size()][vkb.get(0).size()][2];

        for (int i = 0; i < vkb.size(); i++) {
            for (int j = 0; j < vkb.get(0).size(); j++) {
                double tem[] = {vkb.get(i).get(j)[0], vkb.get(i).get(j)[1]};
                vkb_[i][j] = tem;
            }
        }
        double non_loc[][][] = ao.time_complex(ao.conjugate(ao.complex_transpose(bec_)), vkb_);
        return non_loc;
     
    }

}
