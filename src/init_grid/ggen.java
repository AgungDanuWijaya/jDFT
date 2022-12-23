package init_grid;

import main.parameter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class ggen {

    public void main(parameter param) {
        double ecutwfc = param.ecutwfc;
        array_operation mo = new array_operation();

        double[][] bg = param.bg;
        double[][] at = mo.rotate(param.at);
        double batas = param.batas_kecil;

        double gcutm = 0;
        double dual = param.dual;//ganti ini
        double tpiba = 2.0 * Math.PI / param.celldm[0];
        gcutm = dual * ecutwfc / Math.pow(tpiba, 2);
        double gcutw = ecutwfc / Math.pow(tpiba, 2);
        param.gcutw = gcutw;
        param.gcutm = gcutm;
        param.tpiba = tpiba;
        double ni2[] = {at[0][0], at[1][0], at[2][0]};
        double nj2[] = {at[0][1], at[1][1], at[2][1]};
        double nk2[] = {at[0][2], at[1][2], at[2][2]};
        ni2 = mo.powdot(ni2, 2);
        nj2 = mo.powdot(nj2, 2);
        nk2 = mo.powdot(nk2, 2);
        int ni_ = (int) (Math.pow(gcutm, 0.5) * Math.pow(mo.sum(ni2), 0.5)) + 1;
        int nj_ = (int) (Math.pow(gcutm, 0.5) * Math.pow(mo.sum(nj2), 0.5)) + 1;
        int nk_ = (int) (Math.pow(gcutm, 0.5) * Math.pow(mo.sum(nk2), 0.5)) + 1;
        int ni = 0;
        int nj = 0;
        int nk = 0;
        for (int k = -nk_; k <= nk_; k++) {
            for (int j = -nj_; j <= nj_; j++) {
                for (int i = -ni_; i <= ni_; i++) {
                    double g1[] = {i * at[0][0], j * at[0][1], k * at[0][2]};
                    double g2[] = {i * at[1][0], j * at[1][1], k * at[1][2]};
                    double g3[] = {i * at[2][0], j * at[2][1], k * at[2][2]};
                    g1 = mo.powdot(g1, 2);
                    g2 = mo.powdot(g2, 2);
                    g3 = mo.powdot(g3, 2);
                    double gsq = mo.sum(g1) + mo.sum(g2) + mo.sum(g3);
                    if (gsq < gcutm) {
                        ni = Math.max(i, Math.abs(ni));
                        nj = Math.max(j, Math.abs(nj));
                        nk = Math.max(k, Math.abs(nk));
                    }
                }
            }
        }
        ni = 2 * ni + 1;
        nj = 2 * nj + 1;
        nk = 2 * nk + 1;
        if (new good_fft().main(ni) == false) {
            ni += 1;
        }
        if (new good_fft().main(nj) == false) {
            nj += 1;
        }
        if (new good_fft().main(nk) == false) {
            nk += 1;
        }

        param.nr1 = ni;
        param.nr2 = nj;
        param.nr3 = nk;

        ni = (ni - 1) / 2;
        nj = (nj - 1) / 2;
        nk = (nk - 1) / 2;
        double tx[];
        double ty[];
        double t[] = new double[3];
        int ngm = 0;
        HashMap<Integer, Double> tt = new HashMap<>();
        HashMap<Integer, Double> g2sort_g = new HashMap<>();
        HashMap<Integer, int[]> mill_unsorted = new HashMap<>();
        for (int i = -ni; i <= ni; i++) {
            tx = mo.mdot(bg[0], i);
            for (int j = -nj; j <= nj; j++) {
                ty = mo.mdot(bg[1], j);
                ty = mo.adddot(tx, ty);
                for (int k = -nk; k <= nk; k++) {
                    t[0] = ty[0] + k * bg[2][0];
                    t[1] = ty[1] + k * bg[2][1];
                    t[2] = ty[2] + k * bg[2][2];
                    tt.put(k + nk + 1, mo.sum(mo.powdot(t, 2)));
                }
                for (int k = -nk; k <= nk; k++) {
                    if (tt.get(k + nk + 1) < gcutm) {
                        ngm += 1;
                        if (tt.get(k + nk + 1) > batas) {
                            double kk = (int) (tt.get(k + nk + 1) * 100000);
                            g2sort_g.put(ngm, kk);
                        } else {
                            g2sort_g.put(ngm, 0.0);
                        }
                        int d[] = {i, j, k};
                        mill_unsorted.put(ngm, d);
                    }
                }
            }
        }
        List<order> list = new ArrayList<>();
        g2sort_g.keySet().forEach(name -> {
            list.add(new order(String.valueOf(name), g2sort_g.get(name)));
        });
        Collections.sort(list,
                Comparator.comparingDouble(order::getValue));
        double[][] g = new double[ngm][3];
        double[] gg = new double[ngm];
        param.ngm = ngm;
        int in = 0;
        for (order order : list) {
            int miller[] = mill_unsorted.get(Integer.parseInt(order.orderNo));
            g[in] = mo.mdot(bg[0], miller[0]);
            g[in] = mo.adddot(g[in], mo.mdot(bg[1], miller[1]));
            g[in] = mo.adddot(g[in], mo.mdot(bg[2], miller[2]));
            gg[in] = mo.sum(mo.powdot(g[in], 2));
            in++;
        }
        HashMap<Integer, Integer> nl = new HashMap<>();
        double mill[][] = new double[gg.length][3];
        for (int i = 0; i < gg.length; i++) {
            double n1 = mo.sum(mo.mdot(g[i], param.at[0]));//posisi x
            double n2 = mo.sum(mo.mdot(g[i], param.at[1]));//posisi y
            double n3 = mo.sum(mo.mdot(g[i], param.at[2]));//posisi z

            double n_[] = {Math.round(n1), Math.round(n2), Math.round(n3)};
            mill[i] = n_;

            if (n1 < -param.batas_kecil) {
                n1 += param.nr1;
            }
            if (n2 < -param.batas_kecil) {
                n2 += param.nr2;
            }
            if (n3 < -param.batas_kecil) {
                n3 += param.nr3;
            }
            nl.put(i, (int) Math.round(1 + n1 + n2 * param.nr1 + n3 * param.nr1 * param.nr2));
        }

        param.nl = nl;
        HashMap<Integer, Double> gl = new HashMap<>();
        HashMap<Integer, Integer> index_gg = new HashMap<>();
        int ind = 0;
        for (int i = 1; i < gg.length; i++) {
            if (gg[i] > gg[i - 1] + batas) {
                ind += 1;
                gl.put(ind, gg[i]);
            }
            index_gg.put(i, ind);
        }

        gl.put(0, gg[0]);
        index_gg.put(0, 0);
        if (gg[0] < Math.pow(10, -8)) {
            param.gstart = 1;
        } else {
            param.gstart = 0;

        }
        param.mill = mill;
        param.gl = gl;
        param.index_gg = index_gg;
        ggen_object gg_o = new ggen_object();
        gg_o.g = g;
        gg_o.gg = gg;
        param.g = gg_o;
        main_s(param);
    }

    public void main_s(parameter param) {
        double ecutwfc = param.ecutwfc;
        array_operation mo = new array_operation();

        double[][] bg = param.bg;
        double[][] at = mo.rotate(param.at);
        double batas = param.batas_kecil;

        double gcutm = 0;
        double dual = param.dual;
        double tpiba = 2.0 * Math.PI / param.celldm[0];
        gcutm = 4.0 * ecutwfc / Math.pow(tpiba, 2);
        double ni2[] = {at[0][0], at[1][0], at[2][0]};
        double nj2[] = {at[0][1], at[1][1], at[2][1]};
        double nk2[] = {at[0][2], at[1][2], at[2][2]};
        ni2 = mo.powdot(ni2, 2);
        nj2 = mo.powdot(nj2, 2);
        nk2 = mo.powdot(nk2, 2);
        int ni_ = (int) (Math.pow(gcutm, 0.5) * Math.pow(mo.sum(ni2), 0.5)) + 1;
        int nj_ = (int) (Math.pow(gcutm, 0.5) * Math.pow(mo.sum(nj2), 0.5)) + 1;
        int nk_ = (int) (Math.pow(gcutm, 0.5) * Math.pow(mo.sum(nk2), 0.5)) + 1;
        int ni = 0;
        int nj = 0;
        int nk = 0;
        for (int k = -nk_; k <= nk_; k++) {
            for (int j = -nj_; j <= nj_; j++) {
                for (int i = -ni_; i <= ni_; i++) {
                    double g1[] = {i * bg[0][0], j * bg[0][1], k * bg[0][2]};
                    double g2[] = {i * bg[1][0], j * bg[1][1], k * bg[1][2]};
                    double g3[] = {i * bg[2][0], j * bg[2][1], k * bg[2][2]};
                    g1 = mo.powdot(g1, 2);
                    g2 = mo.powdot(g2, 2);
                    g3 = mo.powdot(g3, 2);
                    double gsq = mo.sum(g1) + mo.sum(g2) + mo.sum(g3);
                    if (gsq < gcutm) {
                        ni = Math.max(i, Math.abs(ni));
                        nj = Math.max(j, Math.abs(nj));
                        nk = Math.max(k, Math.abs(nk));
                    }
                }
            }
        }
        ni = 2 * ni + 1;
        nj = 2 * nj + 1;
        nk = 2 * nk + 1;
        ni += 1;
        nj += 1;
        nk += 1;
        System.err.println(ni + " ==" + gcutm);
        ni = (int) new fftw.good_fft_order().main(ni);
        nj = (int) new fftw.good_fft_order().main(nj);
        nk = (int) new fftw.good_fft_order().main(nk);
        param.nr1s = ni;
        param.nr2s = nj;
        param.nr3s = nk;

        ni = (ni - 1) / 2;
        nj = (nj - 1) / 2;
        nk = (nk - 1) / 2;
        double tx[];
        double ty[];
        double t[] = new double[3];
        int ngm = 0;
        HashMap<Integer, Double> tt = new HashMap<>();
        HashMap<Integer, Double> g2sort_g = new HashMap<>();
        HashMap<Integer, int[]> mill_unsorted = new HashMap<>();
        for (int i = -ni; i <= ni; i++) {
            tx = mo.mdot(bg[0], i);
            for (int j = -nj; j <= nj; j++) {
                ty = mo.mdot(bg[1], j);
                ty = mo.adddot(tx, ty);
                for (int k = -nk; k <= nk; k++) {
                    t[0] = ty[0] + k * bg[2][0];
                    t[1] = ty[1] + k * bg[2][1];
                    t[2] = ty[2] + k * bg[2][2];
                    tt.put(k + nk + 1, mo.sum(mo.powdot(t, 2)));
                }
                for (int k = -nk; k <= nk; k++) {
                    if (tt.get(k + nk + 1) < gcutm) {
                        ngm += 1;

                    }
                }
            }
        }
        param.ngm_s = ngm;
        HashMap<Integer, Integer> nl = new HashMap<>();
        double mill[][] = new double[param.g.gg.length][3];
        for (int i = 0; i < param.g.gg.length; i++) {
            double n1 = mo.sum(mo.mdot(param.g.g[i], param.at[0]));//posisi x
            double n2 = mo.sum(mo.mdot(param.g.g[i], param.at[1]));//posisi y
            double n3 = mo.sum(mo.mdot(param.g.g[i], param.at[2]));//posisi z
            double n_[] = {Math.round(n1), Math.round(n2), Math.round(n3)};
            mill[i] = n_;
            if (n1 < -param.batas_kecil) {
                n1 += param.nr1s;
            }
            if (n2 < -param.batas_kecil) {
                n2 += param.nr2s;
            }
            if (n3 < -param.batas_kecil) {
                n3 += param.nr3s;
            }

            nl.put(i, (int) Math.round(1 + n1 + n2 * param.nr1s + n3 * param.nr1s * param.nr2s));
        }

        param.nl_s = nl;

    }

}
