package energy;

import Jama.Matrix;
import main.parameter;
import java.util.HashMap;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class ewald {

   
    public void main(parameter param, int ngm) {
        double tpiba2 = Math.pow(param.tpiba, 2);
        double charge = param.tot_muatan;
        double alpha = 2.9;
        double tpi = 2.0 * Math.PI;
        alpha = alpha - 0.10;
        boolean k = true;
        while (k = true) {
            double upperbound = 2.0 * Math.pow(charge, 2) * Math.sqrt(2.0 * alpha / tpi) * special_function.e_erfc(Math.sqrt(tpiba2 * param.gcutm / 4.0 / alpha));
            if (upperbound <= Math.pow(10, -7)) {
                k = false;
                break;
            } else {
                alpha = alpha - 0.10;
            }
        }
        double ewalddr = 0;
        double ewaldg = 0.0;
        array_operation ao = new array_operation();
        if (param.gstart == 1) {
            ewaldg = -Math.pow(charge, 2) / alpha / 4.0;
        } else {
            ewaldg = 0.0;
        }
        for (int j = param.gstart; j < ngm; j++) {
            double rhon[] = {0, 0};
            for (int i = 0; i < param.atom.length; i++) {
                rhon = ao.adddot(rhon, ao.mdot(ao.conjugate(param.strf.get(i).get(j)), param.upf_data.get(param.atom_p[i]).zp));
            }
            ewaldg += (Math.pow(rhon[0], 2) + Math.pow(rhon[1], 2)) * Math.exp(-param.g.gg[j] * Math.pow(param.tpiba, 2) / alpha / 4.0) / param.g.gg[j] / Math.pow(param.tpiba, 2);
        }
        ewaldg = 2.0 * tpi / param.omega * ewaldg;
        if (param.gstart == 1) {
            for (int i = 0; i < param.nat; i++) {
                double val = param.upf_data.get(param.atom_p[i]).zp;
                ewaldg -= val * val * Math.sqrt(8.0 / tpi * alpha);
            }
        }
        double rmax = 4.0 / Math.sqrt(alpha) / param.celldm[0];
        for (int i = 0; i < param.pos.length; i++) {
            for (int j = 0; j < param.pos.length; j++) {
                double dtau[] = ao.subdot(param.pos[i], param.pos[j]);
                double datu[][] = {dtau};
                Matrix dtau_ = new Matrix(datu);
                Matrix bg = new Matrix(param.bg);
                Matrix ds = dtau_.times(bg.transpose());
                double ds_[] = ds.getArray()[0];
                ds_ = ao.subdot(ds_, ao.nint(ds_));
                Matrix at_ = new Matrix(param.at);
                double ds__[][] = {ds_};
                Matrix dtau0 = (new Matrix(ds__)).times(at_);
                double dtau0_[] = dtau0.getArray()[0];
                int nrm = 0;
                int nm1 = (int) (Math.sqrt(Math.pow(param.bg[0][0], 2) + Math.pow(param.bg[0][1], 2) + Math.pow(param.bg[0][2], 2)) * rmax) + 2;
                int nm2 = (int) (Math.sqrt(Math.pow(param.bg[1][0], 2) + Math.pow(param.bg[1][1], 2) + Math.pow(param.bg[1][2], 2)) * rmax) + 2;
                int nm3 = (int) (Math.sqrt(Math.pow(param.bg[2][0], 2) + Math.pow(param.bg[2][1], 2) + Math.pow(param.bg[2][2], 2)) * rmax) + 2;
                double tt = 0;
                HashMap<Integer, Double> r2 = new HashMap<>();
                for (int i_ = -nm1; i_ <= nm1; i_++) {
                    for (int j_ = -nm2; j_ <= nm2; j_++) {
                        for (int k_ = -nm3; k_ <= nm3; k_++) {
                            tt = 0.0;
                            double t[] = new double[3];
                            for (int ipol = 0; ipol < 3; ipol++) {
                                t[ipol] = i_ * param.at[ipol][0] + j_ * param.at[ipol][1] + k_ * param.at[ipol][2] - dtau0_[ipol];
                                tt = tt + t[ipol] * t[ipol];
                            }
                            if (tt <= Math.pow(rmax, 2) & Math.abs(tt) > Math.pow(10, -10)) {
                                r2.put(nrm, tt);
                                nrm += 1;
                            }

                        }
                    }
                }

                for (int l = 0; l < nrm; l++) {
                    double rr = Math.sqrt(r2.get(l)) * param.celldm[0];
                    double val = param.upf_data.get(param.atom_p[i]).zp;
                    double val1 = param.upf_data.get(param.atom_p[i]).zp;
                    ewalddr += val * val1 * special_function.e_erfc(Math.sqrt(alpha) * rr) / rr;
                }
            }
        }
        double energi_ewal = 0.5 * 2 * (ewaldg + ewalddr);
        param.ewald = energi_ewal;
    }
}
