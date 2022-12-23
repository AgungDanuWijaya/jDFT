package init_calc;

import main.parameter;
import java.util.HashMap;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class vkb {

    public HashMap<Integer, HashMap<Integer, double[]>> main(parameter param, int ik) {

        array_operation ao = new array_operation();
        int npw = param.ngk.get(ik);
        double gk[][] = new double[npw][3];
        double qg[] = new double[npw];
        for (int i = 0; i < npw; i++) {
            double r = param.igk.get(ik).get(i);
            int iig = (int) r;
            gk[i][0] = param.k_point[ik][0] + param.g.g[iig][0];
            gk[i][1] = param.k_point[ik][1] + param.g.g[iig][1];
            gk[i][2] = param.k_point[ik][2] + param.g.g[iig][2];
            double gk_[] = {gk[i][0], gk[i][1], gk[i][2]};
            qg[i] = ao.sum(ao.powdot(gk_, 2));
        }
        param.g2_kin.put(ik, ao.mdot(qg, Math.pow(param.tpiba, 2)));
        param.h_diag.put(ik, ao.adddot(ao.mdot(qg, Math.pow(param.tpiba, 2)), param.v_of_0));
        double s_diag[] = new double[param.h_diag.get(ik).length];
        s_diag = ao.adddot(s_diag, 1.0);
        param.s_diag.put(ik, s_diag);

        double max = 0;
        for (int i = 0; i < param.upf_data.size(); i++) {
            for (int nb = 0; nb < param.upf_data.get(param.atom[i]).PP_BETA.size(); nb++) {
                double l = param.upf_data.get(param.atom[i]).PP_BETA_Param.get(nb).get("angular_momentum");
                if (l > max) {
                    max = l;
                }
            }
        }
        max = Math.pow(max + 1, 2);
        double[][] ylm = new ymlr2().main((int) max, npw, gk, qg);
        for (int i = 0; i < npw; i++) {
            qg[i] = Math.sqrt(qg[i]) * param.tpiba;
        }
        int in_w = 0;
        double[][][] vq = new double[param.upf_data.size()][npw][];
        for (int ip = 0; ip < param.upf_data.size(); ip++) {
            double[][] vq_ = new double[npw][param.upf_data.get(param.atom[ip]).PP_BETA.size()];
            for (int i = 0; i < param.upf_data.get(param.atom[ip]).PP_BETA.size(); i++) {
                for (int ig = 0; ig < npw; ig++) {
                    double px = qg[ig] / param.dq - (int) (qg[ig] / param.dq);
                    double ux = 1.0 - px;
                    double vx = 2.0 - px;
                    double wx = 3.0 - px;
                    int i0 = (int) (qg[ig] / param.dq) + 1;
                    int i1 = i0 + 1;
                    int i2 = i0 + 2;
                    int i3 = i0 + 3;
                    vq_[ig][i] = param.upf_data.get(param.atom[ip]).tab[i0 - 1][i] * ux * vx * wx / 6.0
                            + param.upf_data.get(param.atom[ip]).tab[i1 - 1][i] * px * vx * wx / 2.0
                            - param.upf_data.get(param.atom[ip]).tab[i2 - 1][i] * px * ux * wx / 2.0
                            + param.upf_data.get(param.atom[ip]).tab[i3 - 1][i] * px * ux * vx / 6.0;
                }
            }
            vq[ip] = vq_;
        }
        HashMap<Integer, HashMap<Integer, double[]>> vkb_ = new HashMap<>();

        for (int na = 0; na < param.nat; na++) {
            double arg = (param.k_point[ik][0] * param.pos[na][0] + param.k_point[ik][1] * param.pos[na][1] + param.k_point[ik][2] * param.pos[na][2]) * 2.0 * Math.PI;
            double kphase[] = {Math.cos(arg), Math.sin(arg) * -1};
            double sk[][] = new double[npw][];
            for (int ig = 0; ig < npw; ig++) {
                double r = param.igk.get(ik).get(ig);
                int iig = (int) r;
                sk[ig] = ao.complex_dot(kphase, param.eigts1_.get(na).get((int) param.mill[iig][0]));
                sk[ig] = ao.complex_dot(sk[ig], param.eigts2_.get(na).get((int) param.mill[iig][1]));
                sk[ig] = ao.complex_dot(sk[ig], param.eigts3_.get(na).get((int) param.mill[iig][2]));
            }
            for (int nb = 0; nb < param.upf_data.get(param.atom_p[na]).PP_BETA.size(); nb++) {
                double l = param.upf_data.get(param.atom_p[na]).PP_BETA_Param.get(nb).get("angular_momentum");
                double lphase[] = {0.0, -1.0};
                for (int i = 1; i < l; i++) {
                    lphase = ao.complex_dot(lphase, lphase);
                }
                if (l == 0) {
                    lphase[0] = 1;
                    lphase[1] = 0;
                }
                for (int m = 1; m <= 2 * l + 1; m++) {
                    int lm = (int) Math.pow(l, 2) + m;
                    HashMap<Integer, double[]> vkb_t = new HashMap<>();
                    for (int ig = 0; ig < npw; ig++) {
                        double wfc[] = ao.complex_dot(lphase, sk[ig]);
                        wfc = ao.mdot(wfc, ylm[ig][lm - 1]);
                        wfc = ao.mdot(wfc, vq[param.tipe.get(param.atom_p[na])][ig][nb]);
                        vkb_t.put(ig, wfc);
                    }

                    vkb_.put(in_w, vkb_t);
                    in_w = in_w + 1;
                }
            }
        }

        return vkb_;
    }

}
