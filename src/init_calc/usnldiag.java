package init_calc;

import main.parameter;
import java.util.HashMap;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class usnldiag {

    public void main(parameter param, int kpoint) {
        int indv_ijkb0[] = new int[param.nat];
        int ijkb0 = 0;
        array_operation ao = new array_operation();
        for (int ik = 0; ik < param.atom.length; ik++) {
            for (int j = 0; j < param.nat; j++) {
                if (param.atom[ik].equals(param.atom_p[j])) {
                    indv_ijkb0[j] = ijkb0;
                    ijkb0 += param.upf_data.get(param.atom[ik]).deeq[j].length;
                    for (int ih = 0; ih < param.upf_data.get(param.atom[ik]).deeq[j].length; ih++) {
                        int ikb = indv_ijkb0[j] + ih;
                        double h_diag[] = param.h_diag.get(kpoint);
                        double s_diag[] = param.s_diag.get(kpoint);

                        for (int ig = 0; ig < param.vkb.get(kpoint).get(ikb).size(); ig++) {
                            double ar_[] = {param.vkb.get(kpoint).get(ikb).get(ig)[0], param.vkb.get(kpoint).get(ikb).get(ig)[1]};
                            double ar = Math.pow(ar_[0], 2) + Math.pow(ar_[1], 2);
                            double tenp[] = ao.mdot(param.upf_data.get(param.atom[ik]).deeq[j][ih][ih], ar);
                            double tenp_[] = ao.mdot(param.upf_data.get(param.atom[ik]).qq_at_[ih][ih], ar);
                            h_diag[ig] += tenp[0];
                            s_diag[ig] += tenp_[0];

                        }
                        for (int jh = 0; jh < param.upf_data.get(param.atom[ik]).deeq[j].length; jh++) {
                            if (ih != jh) {
                                int jkb = indv_ijkb0[j] + jh;

                                for (int ig = 0; ig < param.vkb.get(kpoint).get(jkb).size(); ig++) {
                                    double ar_[] = {param.vkb.get(kpoint).get(ikb).get(ig)[0], param.vkb.get(kpoint).get(ikb).get(ig)[1]};
                                    double ar__[] = {param.vkb.get(kpoint).get(jkb).get(ig)[0], param.vkb.get(kpoint).get(jkb).get(ig)[1]};
                                    double hasl[] = ao.complex_dot(ar_, ao.conjugate(ar__));
                                    double ar = hasl[0] + hasl[1];
                                    double tenp[] = ao.mdot(param.upf_data.get(param.atom[ik]).deeq[j][ih][jh], ar);
                                    double tenp_[] = ao.mdot(param.upf_data.get(param.atom[ik]).qq_at_[ih][jh], ar);
                                    h_diag[ig] += tenp[0];
                                    s_diag[ig] += tenp_[0];
                                }
                            }
                        }
                        param.s_diag.replace(kpoint, s_diag);
                        param.h_diag.replace(kpoint, h_diag);
                    }
                }
            }
        }
    }
}
