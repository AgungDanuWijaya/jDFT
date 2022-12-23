package init_calc;

import main.parameter;
import java.util.HashMap;
import tools.array_operation;

public class init_us_1_1 {

    public void main(parameter p_g) {
        for (int nt = 0; nt < p_g.upf_data.size(); nt++) {
            double[][] ylm = new ymlr2().main(p_g.lmax * p_g.lmax, p_g.g.gg.length, p_g.g.g, p_g.g.gg);
            array_operation ao = new array_operation();
            int ijh = 0;
            int nh = p_g.upf_data.get(p_g.atom[nt]).indv.size();
            double qq_at_[][][] = new double[nh][nh][2];

            for (int ih = 0; ih < nh; ih++) {
                if (p_g.upf_data.get(p_g.atom[nt]).usp == 1) {
                    HashMap<Integer, double[]> mainn = new HashMap<>();
                    for (int jh = ih; jh < nh; jh++) {
                        double qg_[][] = new qvan2().main(p_g.upf_data.get(p_g.atom[nt]), p_g, 1, ih, jh, p_g.g.gg, ylm);
                        double com[] = {p_g.omega * qg_[0][0], 0};
                        qq_at_[ih][jh] = com;
                        qq_at_[jh][ih] = com;
                    }
                }
            }
            p_g.upf_data.get(p_g.atom[nt]).qq_at_ = qq_at_;
        }

    }

}
