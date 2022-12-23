package init_calc;

import main.parameter;
import fftw.fftw;
import java.util.HashMap;
import tools.array_operation;

public class driver_deeq {

    public void main(parameter p_g) {

        for (int i = 0; i < p_g.upf_data.size(); i++) {
            new deeq().main(p_g.upf_data.get(p_g.atom[i]), p_g);
        }
        new init_us_1_1().main(p_g);
        set_Deeq(p_g);
    }

    public void set_Deeq(parameter p_g) {
        for (int nt = 0; nt < p_g.upf_data.size(); nt++) {
            if (p_g.upf_data.get(p_g.atom[nt]).usp == 1) {
                double qmod[] = new double[p_g.g.gg.length];

                for (int i = 0; i < p_g.g.gg.length; i++) {
                    qmod[i] = Math.sqrt(p_g.g.gg[i]) * p_g.tpiba;
                }
                double[][] ylm = new ymlr2().main(p_g.lmax * p_g.lmax, p_g.g.gg.length, p_g.g.g, p_g.g.gg);
                array_operation ao = new array_operation();
                double vrs[] = ao.adddot(p_g.v_dft, p_g.v_h);
                vrs = ao.adddot(vrs, p_g.v_loc);

                double vrs_comp[][] = new double[vrs.length][2];
                for (int i = 0; i < vrs.length; i++) {
                    vrs_comp[i][0] = vrs[i];
                    vrs_comp[i][1] = 0;
                }
                int n[] = {(int) p_g.nr1, (int) p_g.nr2, (int) p_g.nr3, 1};
                double[][] aux_ = fftw.fftw(n, vrs_comp);
                aux_[0] = ao.mdot(aux_[0], 1.0 / (p_g.nr1 * p_g.nr2 * p_g.nr3));
                aux_[1] = ao.mdot(aux_[1], 1.0 / (p_g.nr1 * p_g.nr2 * p_g.nr3));
                double[][] vaux = new double[(int) (p_g.nr1 * p_g.nr2 * p_g.nr3)][2];
                for (int i = 0; i < p_g.g.gg.length; i++) {
                    vaux[i][0] = aux_[0][p_g.nl.get(i) - 1];
                    vaux[i][1] = aux_[1][p_g.nl.get(i) - 1];
                }
                double nij = p_g.upf_data.get(p_g.atom[nt]).indv.size() * (p_g.upf_data.get(p_g.atom[nt]).indv.size() + 1) / 2;
                int ijh = 0;
                int nh = p_g.upf_data.get(p_g.atom[nt]).indv.size();
                HashMap<Integer, double[][]> qgm = new HashMap<>();
                for (int ih = 0; ih < nh; ih++) {
                    for (int jh = ih; jh < nh; jh++) {
                        ijh = ijh + 1;
                        double qg_[][] = new qvan2().main(p_g.upf_data.get(p_g.atom[nt]), p_g, p_g.g.gg.length, ih, jh, qmod, ylm);
                        qgm.put(ijh, ao.rotate(qg_));
                    }
                }
                int npw = p_g.g.gg.length;
                int nb__ = 0;
                for (int na = 0; na < p_g.nat; na++) {
                    if (p_g.upf_data.get(p_g.atom_p[nt]).name.equals(p_g.upf_data.get(p_g.atom_p[na]).name)) {
                        nb__++;
                    }
                }

                double aux[][][] = new double[nb__][npw][];
                int nb = -1;
                for (int na = 0; na < p_g.nat; na++) {
                    if (p_g.upf_data.get(p_g.atom_p[nt]).name.equals(p_g.upf_data.get(p_g.atom_p[na]).name)) {
                        nb++;
                        for (int ig = 0; ig < npw; ig++) {
                            aux[nb][ig] = ao.mdot(p_g.eigts1_.get(na).get((int) p_g.mill[ig][0]), 1);
                            aux[nb][ig] = ao.complex_dot(aux[nb][ig], p_g.eigts2_.get(na).get((int) p_g.mill[ig][1]));
                            aux[nb][ig] = ao.complex_dot(aux[nb][ig], p_g.eigts3_.get(na).get((int) p_g.mill[ig][2]));
                            aux[nb][ig] = ao.complex_dot(ao.conjugate(aux[nb][ig]), vaux[ig]);
                        }
                    }

                }

                double qgm_c[][][] = new double[qgm.size()][p_g.g.gg.length][2];
                for (int i = 0; i < qgm.size(); i++) {
                    double da[][] = new double[p_g.g.gg.length][2];
                    for (int j = 0; j < qgm.get(i + 1).length; j++) {
                        da[j][0] = qgm.get(i + 1)[j][0];
                        da[j][1] = qgm.get(i + 1)[j][1];

                    }
                    qgm_c[i] = da;
                }

                double deeaux[][][] = ao.time_complex(ao.conjugate(qgm_c), ao.rotate(aux));
                double[][][][] deeq = new double[p_g.nat][p_g.upf_data.get(p_g.atom[nt]).indv.size()][p_g.upf_data.get(p_g.atom[nt]).indv.size()][2];
                int nb_ = 0;

                int ijh_ = 0;
                for (int na = 0; na < p_g.nat; na++) {
                    if (p_g.upf_data.get(p_g.atom[nt]).name.equals(p_g.upf_data.get(p_g.atom_p[na]).name)) {
                        nb_ = nb_ + 1;
                        ijh_ = 0;
                        for (int ih = 0; ih < p_g.upf_data.get(p_g.atom[nt]).indv.size(); ih++) {
                            for (int jh = ih; jh < p_g.upf_data.get(p_g.atom[nt]).indv.size(); jh++) {
                                ijh_ += 1;
                                deeq[na][ih][jh][0] = p_g.omega * deeaux[ijh_ - 1][nb_ - 1][0];
                                deeq[na][ih][jh][1] = 0;
                                if (jh > ih) {
                                    deeq[na][jh][ih][0] = deeq[na][ih][jh][0];
                                    deeq[na][jh][ih][1] = deeq[na][ih][jh][1];
                                }
                            }
                        }

                    }

                }
             
                double[][][][] deeq__ = new double[p_g.nat][][][];
                for (int i = 0; i < p_g.nat; i++) {
                    double[][][] deeq_ = new deeq_usp().main(p_g.upf_data.get(p_g.atom[nt]), p_g, deeq[i]);
                    deeq__[i] = deeq_;
                }

                if (p_g.upf_data.get(p_g.atom[nt]).usp == 1) {
                    p_g.upf_data.get(p_g.atom[nt]).deeq = deeq__;
                }
            }
        }
    }
}
