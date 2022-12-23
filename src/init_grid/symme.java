package init_grid;

import main.parameter;
import java.util.HashMap;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class symme {

    public void main(parameter param) {
        double n[][] = new double[param.g.gg.length][3];
        double done[] = new double[param.g.gg.length];
        for (int i = 0; i < param.g.gg.length; i++) {
            for (int j = 0; j < 3; j++) {
                double r = param.at[j][0] * param.g.g[i][0] + param.at[j][1] * param.g.g[i][1] + param.at[j][2] * param.g.g[i][2];
                n[i][j] = Math.round(r);
            }
        }
        HashMap<Integer, double[]> gshel = new HashMap<>();
        HashMap<Integer, HashMap<Integer, Integer>> vect = new HashMap<>();
        int ngs = 0;
        for (int i = 0; i < param.g.gg.length; i++) {
            if (done[i] == 0) {
                int ng = 0;
                for (int j = 0; j < param.nsym; j++) {
                    double sn[] = new double[3];
                    for (int k = 0; k < 3; k++) {
                        sn[k] = param.s_[j][k][0] * n[i][0] + param.s_[j][k][1] * n[i][1] + param.s_[j][k][2] * n[i][2];
                    }
                    int found = 0;
                    for (int k = 0; k < ng; k++) {
                        try {
                            if (sn[0] == gshel.get(k)[0]
                                    & sn[1] == gshel.get(k)[1]
                                    & sn[2] == gshel.get(k)[2]) {
                                found = 1;
                                k = ng + 100;
                            }
                        } catch (Exception e) {
                        }
                    }
                    if (found == 0) {
                        gshel.put(ng, sn);
                        ng = ng + 1;
                    }
                }
                HashMap<Integer, Integer> as = new HashMap<>();
                for (int j = 0; j <= ng; j++) {
                    for (int k = i; k < param.g.gg.length; k++) {
                        if (done[k] == 0) {
                            try {
                                if (n[k][0] == gshel.get(j)[0]
                                        & n[k][1] == gshel.get(j)[1]
                                        & n[k][2] == gshel.get(j)[2]) {
                                    done[k] = 1;
                                    as.put(j, k);
                                    vect.put(ngs, as);
                                    k = param.g.gg.length + 100;
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                }
                ngs = ngs + 1;
            }
        }
        param.vect = vect;

    }

}
