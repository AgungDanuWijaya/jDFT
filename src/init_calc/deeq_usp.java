package init_calc;

import main.parameter;
import fftw.fftw;
import java.util.HashMap;
import java.util.Objects;
import psudo.param_upf;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class deeq_usp {


    public double[][][] main(param_upf param, parameter p_g, double[][][] deeq) {

        int in = 0;
        HashMap<Integer, Integer> indv = new HashMap<>();
        HashMap<Integer, Double> nhtol = new HashMap<>();
        HashMap<Integer, Double> nhtolm = new HashMap<>();
        for (int nb = 0; nb < param.PP_BETA.size(); nb++) {
            double l = param.PP_BETA_Param.get(nb).get("angular_momentum");
            for (int m = 1; m <= 2 * l + 1; m++) {
                indv.put(in, nb);
                nhtol.put(in, l);
                nhtolm.put(in, l * l + m);
                in = in + 1;
            }
        }
        
        in = 0;
        double dij_[][] = new double[param.number_projector][param.number_projector];
        for (int i = 0; i < param.number_projector; i++) {
            for (int j = 0; j < param.number_projector; j++) {
                dij_[i][j] = param.PP_DIJ.get(in);
                in = in + 1;
            }
        }
        double[][][] dvan = new double[indv.size()][indv.size()][2];
        param.indv = indv;
        param.nhtolm = nhtolm;
        
        for (int ih = 0; ih < indv.size(); ih++) {
            for (int jh = 0; jh < indv.size(); jh++) {
                if (Objects.equals(nhtol.get(jh), nhtol.get(ih))) {
                    if (Objects.equals(nhtolm.get(jh), nhtolm.get(ih))) {
                        dvan[ih][jh][0] = dij_[indv.get(ih)][indv.get(jh)];
                        dvan[ih][jh][1] = 0;
                    
                    }
                }
            }
        }
        
        for (int ih = 0; ih < indv.size(); ih++) {
            for (int jh = ih; jh < indv.size(); jh++) {
            
                deeq[ih][jh][0] += dvan[ih][jh][0];
                deeq[jh][ih][0]  = deeq[ih][jh][0];
                deeq[ih][jh][1] += dvan[ih][jh][1];
                deeq[jh][ih][1]  = deeq[ih][jh][1];
           
            }
        }

        return deeq;

    }

}
