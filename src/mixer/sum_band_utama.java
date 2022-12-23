package mixer;

import main.parameter;
import fftw.fftw;
import java.util.HashMap;
import init_grid.rho_symme;
import init_calc.qvan2;
import init_calc.ymlr2;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class sum_band_utama {

    /**
     * @param args the command line arguments
     */
    public void main(parameter param, int ik, double rho[][]) {
        array_operation ao = new array_operation();
        HashMap<Integer, HashMap<Integer, double[]>> atomicwfc = param.atomicwfc.get(ik);
        double efc[][][] = new double[atomicwfc.size()][(int) (param.nr1s * param.nr2s * param.nr3s)][2];
        for (int i = 0; i < atomicwfc.size(); i++) {
            for (int j = 0; j < atomicwfc.get(0).size(); j++) {
                double tem1[] = {atomicwfc.get(i).get(j)[0], atomicwfc.get(i).get(j)[1]};
                double in = param.igk.get(ik).get(j);
                efc[i][param.nl_s.get((int) in) - 1] = tem1;
            }
        }

        double psic[][] = new double[(int) (param.nr1s * param.nr2s * param.nr3s)][2];

        for (int j = 0; j < psic.length; j++) {
            double tem1[] = {rho[j][0], 0};
            psic[j] = tem1;
        }

        int n[] = {(int) param.nr1s, (int) param.nr2s, (int) param.nr3s, 1};
        double[][] psic_g = ao.rotate(fftw.fftw(n, psic));
        psic_g = ao.mdot(psic_g, 1.0 / (double) (n[0] * n[1] * n[2]));
        double rho_g[][] = new double[param.ngm_s][2];
        for (int i = 0; i < rho_g.length; i++) {
            rho_g[i] = psic_g[param.nl_s.get(i) - 1];
        }

        double rho_g_[][] = new double[(int) (param.nr1 * param.nr2 * param.nr3)][2];
        for (int i = 0; i < rho_g.length; i++) {
            rho_g_[i] = rho_g[i];
        }

        if (param.usp == 1) {
            double becsum[][][] = null;
            new init_calbec().main(param, ik);
            param.set_cal = 1;
            int indv_ijkb0[] = new int[param.nat];
            int ijkb0 = 0;
            for (int np = 0; np < param.atom.length; np++) {
                double aux1[][][] = new double[param.bec.get(ik)[0].length][param.upf_data.get(param.atom[np]).deeq[0].length][2];
                double aux2[][][] = new double[param.bec.get(ik)[0].length][param.upf_data.get(param.atom[np]).deeq[0].length][2];

                for (int na = 0; na < param.nat; na++) {
                    if (param.atom[np].equals(param.atom_p[na])) {

                        for (int ih = 0; ih < param.upf_data.get(param.atom[np]).deeq[na].length; ih++) {

                            indv_ijkb0[np] = ijkb0;
                            ijkb0 = ih;
                            for (int kbnd = 0; kbnd < param.bec.get(ik)[0].length; kbnd++) {
                                double ca = param.campur;

                                if (kbnd > param.awal_panjang - 1) {
                                    ca = 0;
                                }
                                if (param.smar == 1) {
                                    ca = param.wg[kbnd];
                                }
                                aux1[kbnd][ih][0] = param.bec.get(ik)[ijkb0][kbnd][0];
                                aux1[kbnd][ih][1] = param.bec.get(ik)[ijkb0][kbnd][1];

                                aux2[kbnd][ih][0] = param.bec.get(ik)[ijkb0][kbnd][0] * ca;
                                aux2[kbnd][ih][1] = param.bec.get(ik)[ijkb0][kbnd][1] * ca;
                            }
                        }

                        double aux_gk[][][] = ao.time_complex(ao.complex_transpose(aux1), aux2);
                        for (int i = 0; i < aux_gk.length; i++) {
                            for (int j = 0; j < aux_gk.length; j++) {
                            }
                        }
                        int ijh_ = 0;
                        for (int ih = 0; ih < param.upf_data.get(param.atom[np]).deeq[na].length; ih++) {

                            for (int jh = ih; jh < param.upf_data.get(param.atom[np]).deeq[na].length; jh++) {
                                ijh_++;
                            }
                        }
                        int ijh = -1;
                        becsum = new double[ijh_][param.nat][2];
                        for (int ih = 0; ih < param.upf_data.get(param.atom[np]).deeq[na].length; ih++) {

                            for (int jh = ih; jh < param.upf_data.get(param.atom[np]).deeq[na].length; jh++) {
                                ijh++;

                                if (ih == jh) {
                                    becsum[ijh][na] = ao.adddot(becsum[ijh][na], aux_gk[ih][jh]);
                                } else {
                                    becsum[ijh][na] = ao.adddot(becsum[ijh][na], ao.mdot(aux_gk[ih][jh], 2));
                                }

                            }
                        }

                    }
                }

            }
            double aux[][] = new double[param.g.gg.length][2];

            for (int nt = 0; nt < param.upf_data.size(); nt++) {

                double qmod[] = new double[param.g.gg.length];
                double[][] ylm = new ymlr2().main(param.lmax * param.lmax, param.g.gg.length, param.g.g, param.g.gg);

                for (int i = 0; i < param.g.gg.length; i++) {
                    qmod[i] = Math.sqrt(param.g.gg[i]) * param.tpiba;

                }

                if (param.upf_data.get(param.atom[nt]).usp == 1) {

                    double nij = param.upf_data.get(param.atom[nt]).indv.size()
                            * (param.upf_data.get(param.atom[nt]).indv.size() + 1) / 2;

                    int nh = param.upf_data.get(param.atom[nt]).indv.size();
                    int npw = param.g.gg.length;

                    int nb__ = 0;
                    for (int na = 0; na < param.nat; na++) {
                        if (param.upf_data.get(param.atom_p[nt]).name.equals(param.upf_data.get(param.atom_p[na]).name)) {
                            nb__++;
                        }
                    }
                    double skk[][][] = new double[nb__][npw][];
                    double tbecsum[][][] = new double[nb__][(int) nij][];
                    nb__ = -1;
                    for (int na = 0; na < param.nat; na++) {
                        if (param.upf_data.get(param.atom[nt]).name.equals(param.upf_data.get(param.atom_p[na]).name)) {
                            nb__++;
                            for (int i = 0; i < nij; i++) {
                                tbecsum[nb__][i] = becsum[i][na];
                            }
                            for (int ig = 0; ig < npw; ig++) {

                                skk[nb__][ig] = param.eigts1_.get(na).get((int) param.mill[ig][0]);
                                skk[nb__][ig] = ao.complex_dot(skk[na][ig], param.eigts2_.get(na).get((int) param.mill[ig][1]));
                                skk[nb__][ig] = ao.complex_dot(skk[na][ig], param.eigts3_.get(na).get((int) param.mill[ig][2]));

                            }
                        }

                    }
                    double aux2_[][][] = ao.time_complex(ao.complex_transpose(skk), tbecsum);
                    int nb_ = 0;
                    int ijh_ = -1;

                    for (int ih = 0; ih < nh; ih++) {
                        for (int jh = ih; jh < nh; jh++) {
                            ijh_ = ijh_ + 1;
                            double qg_[][] = new qvan2().main(param.upf_data.get(param.atom[nt]), param, param.g.gg.length, ih, jh, qmod, ylm);
                            double qg__[][] = ao.rotate(qg_);
                            for (int i = 0; i < param.g.gg.length; i++) {
                                aux[i] = ao.adddot(aux[i], ao.mdot(aux2_[i][ijh_], qg__[i]));
                            }
                        }

                    }

                }
            }
            

            for (int i = 0; i < aux.length; i++) {
            
                rho_g_[i][0] = rho_g_[i][0] + aux[i][0];
                rho_g_[i][1] = rho_g_[i][1] + aux[i][1];
            }

        }

        rho_g_ = new rho_symme().main(param, rho_g_);
       
        double psic_[][] = new double[(int) (param.nr1 * param.nr2 * param.nr3)][2];

        for (int j = 0; j < rho_g.length; j++) {
            double tem1[] = {rho_g_[j][0], rho_g_[j][1]};
            psic_[param.nl.get(j) - 1] = tem1;
        }

        int n_[] = {(int) param.nr1, (int) param.nr2, (int) param.nr3, -1};
        double[][] psic_r = fftw.fftw(n_, psic_);
        param.rhocg_new = rho_g_;
        param.rhocr_new = psic_r;
    }

}
