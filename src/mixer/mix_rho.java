
package mixer;

import main.parameter;
import fftw.fftw;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class mix_rho {

    double[][] rhocg;
    double[][] rhocr;
    double[][] rhocg_new;

    /**
     * @param args the command line arguments
     */
    public void main_g(parameter param, int ik) {
        array_operation ao = new array_operation();
        this.rhocg = new double[param.rhocg.length][2];
        this.rhocg_new = new double[param.rhocg.length][2];
        for (int i = 0; i < param.rhocg.length; i++) {
            this.rhocg[i] = param.rhocg[i];
            this.rhocg_new[i] = param.rhocg_new[i];
        }

        double rho_g_baru[][] = new double[param.ngm_s][2];
        double rho_g_lama[][] = new double[param.ngm_s][2];
        for (int i = 0; i < param.ngm_s; i++) {
            rho_g_baru[i] = param.rhocg_new[i];
            rho_g_lama[i] = param.rhocg[i];
        }

        double[][] rho_g_new = ao.adddot(rho_g_baru, ao.mdot(rho_g_lama, -1));
       
        double dr2 = new rho_ddot().main(rho_g_new, rho_g_new, param);
        param.dr = dr2;
       
        int base = param.base;
        int pos = param.ipos;
        int mas = param.max_base;
        if (param.scf != 0) {
            param.rho_back_df_1.put(param.scf, rho_g_new);
            param.rho_back_dv_1.put(param.scf, rho_g_lama);
            if (param.base < mas) {
                param.base += 1;
            }
            if (param.ipos < mas - 1) {
                param.ipos += 1;
            } else {
                param.ipos = 0;
            }
            double df_[][][] = new double[base][][];
            double dv_[][][] = new double[base][][];

            df_[pos] = ao.adddot(param.rho_back_df_1.get(param.scf - 1), ao.mdot(rho_g_new, -1));
            dv_[pos] = ao.adddot(param.rho_back_dv_1.get(param.scf - 1), ao.mdot(rho_g_lama, -1));
            param.rho_back_df_2.put(pos, df_[pos]);
            param.rho_back_dv_2.put(pos, dv_[pos]);
            double betamix[][] = new double[base][base];
            for (int i = 0; i < base; i++) {
                df_[i] = param.rho_back_df_2.get(i);
                dv_[i] = param.rho_back_dv_2.get(i);
            }
            //System.out.println("");
            for (int i = 0; i < betamix.length; i++) {
                for (int j = i; j < betamix.length; j++) {
                    betamix[i][j] = new rho_ddot().main(df_[i], df_[j], param);
                    betamix[j][i] = betamix[i][j];
                }
            }
            betamix = new dsytri().main(betamix);
            double work[] = new double[base];

            for (int i = 0; i < work.length; i++) {
                work[i] = new rho_ddot().main(df_[i], rho_g_new, param);
            }

            for (int i = 0; i < work.length; i++) {
                double gamma = ao.sum(ao.mdot(work, betamix[i]));
                rho_g_lama = ao.adddot(rho_g_lama, ao.mdot(dv_[i], -gamma));
                rho_g_new = ao.adddot(rho_g_new, ao.mdot(df_[i], -gamma));
            }
        }
        double rho_new_[][] = ao.adddot(rho_g_lama, ao.mdot(rho_g_new, param.mix));

        double rho_g_baru_[][] = new double[param.rhocg.length][2];
        double rho_g_lama_[][] = new double[param.rhocg.length][2];

        for (int i = param.ngm_s; i < param.rhocg.length; i++) {
            rho_g_baru_[i] = param.rhocg_new[i];
            rho_g_lama_[i] = param.rhocg[i];
        }

        double[][] rho_g_new_ = ao.adddot(rho_g_baru_, ao.mdot(rho_g_lama_, -1));
        double rho_new__[][] = ao.adddot(rho_g_lama_, ao.mdot(rho_g_new_, param.mix));
        for (int i = 0; i < param.ngm_s; i++) {
            rho_new__[i] = rho_new_[i];
        }

        double rho_g_[][] = new double[(int) (param.nr1 * param.nr2 * param.nr3)][2];
        double df[][] = new double[param.ngm_s][2];
        double dv[][] = new double[param.ngm_s][2];
        for (int i = 0; i < param.ngm_s; i++) {
            df[i] = rho_g_new[i];
            dv[i] = rho_g_lama[i];
        }
        for (int i = 0; i < rho_new__.length; i++) {
            rho_g_[i] = rho_new__[i];

        }
        if (param.scf == 0) {
            param.rho_back_df_1.put(param.scf, df);
            param.rho_back_dv_1.put(param.scf, dv);
        }

        param.rhocg = rho_g_;

    }

    public void mix_frequensi(parameter param, int ik) {
        array_operation ao = new array_operation();
        double psic_[][] = new double[(int) (param.nr1 * param.nr2 * param.nr3)][2];

        double rho_new[][] = ao.adddot(this.rhocg_new, ao.mdot(this.rhocg, -1));
        rho_new = ao.adddot(this.rhocg, ao.mdot(rho_new, param.mix));
        for (int i = 0; i < param.ngm_s; i++) {
            rho_new[i][0] = 0;
            rho_new[i][1] = 0;
        }
        for (int j = 0; j < param.nl.size(); j++) {
            double tem1[] = {rho_new[j][0], rho_new[j][1]};
            psic_[param.nl.get(j) - 1] = tem1;
        }
        this.rhocg = psic_;
        int n_[] = {(int) param.nr1, (int) param.nr2, (int) param.nr3, -1};
        double[][] psic_r = fftw.fftw(n_, psic_);
        this.rhocr = psic_r;

    }

    public void main_r(parameter param, int ik) {
        mix_frequensi(param, ik);
        array_operation ao = new array_operation();
        double psic_[][] = this.rhocg;

        for (int j = 0; j < param.ngm_s; j++) {
            double tem1[] = {param.rhocg[j][0], param.rhocg[j][1]};
            psic_[param.nl.get(j) - 1] = tem1;
        }

        int n_[] = {(int) param.nr1, (int) param.nr2, (int) param.nr3, -1};
        double[][] psic_r = fftw.fftw(n_, psic_);
        param.rhocr = psic_r;
    }

}
