package init_grid;

import main.parameter;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class rho_symme {

    /**
     * @param args the command line arguments
     */
    public double[][] main(parameter param, double rhog[][]) {
        array_operation ao = new array_operation();
        boolean non_symmorphic[] = new boolean[param.s_.length];
        double ft[][] = new double[param.ft.length][3];
        for (int i = 0; i < param.s_.length; i++) {
            non_symmorphic[i] = false;
            if (param.ft[i][0] != 0 || param.ft[i][1] != 0 || param.ft[i][2] != 0) {
                non_symmorphic[i] = true;
            }
            if (non_symmorphic[i] == true) {
                ft[i] = ao.mdot(param.at[0], param.ft[i][0]);
                ft[i] = ao.adddot(ft[i], ao.mdot(param.at[1], param.ft[i][1]));
                ft[i] = ao.adddot(ft[i], ao.mdot(param.at[2], param.ft[i][2]));
            }
        }
        for (Integer key : param.vect.keySet()) {
            int ng = param.vect.get(key).size();
            double g0[][] = new double[ng][3];
            int in = 0;
            for (Integer ke : param.vect.get(key).keySet()) {
                g0[in] = param.g.g[param.vect.get(key).get(ke)];
                in = in + 1;
            }
            g0 = new cryst_to_cart().main(param, g0);
            in = 0;
            int doe[] = new int[param.vect.get(key).size()];
            for (Integer ke : param.vect.get(key).keySet()) {
                if (doe[in] == 0) {
                    double g0_[] = g0[in];
                    in = in + 1;
                    int irot[] = new int[param.s_.length];
                    int irot_[] = new int[param.s_.length];

                    double rho[] = {0, 0};
                    for (int i = 0; i < param.nsym; i++) {
                        double sg[] = new double[3];
                        for (int j = 0; j < sg.length; j++) {
                            sg[j] = param.s_[param.invs.get(i)][j][0] * g0_[0]
                                    + param.s_[param.invs.get(i)][j][1] * g0_[1]
                                    + param.s_[param.invs.get(i)][j][2] * g0_[2];

                        }
                        int j = 0;
                        int im = 0;

                        for (Integer kem : param.vect.get(key).keySet()) {
                            double g0__[] = g0[im];
                            im = im + 1;
                            if (Math.abs(sg[0] - g0__[0]) < 1.0E-5) {
                                if (Math.abs(sg[1] - g0__[1]) < 1.0E-5) {
                                    if (Math.abs(sg[2] - g0__[2]) < 1.0E-5) {
                                        if (j == 0) {
                                            irot[i] = kem;
                                            irot_[i] = im - 1;
                                            j = 1;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        int isg = param.vect.get(key).get(irot[i]);

                        if (non_symmorphic[i] == true) {
                            double arg = param.g.g[isg][0] * ft[i][0]
                                    + param.g.g[isg][1] * ft[i][1]
                                    + param.g.g[isg][2] * ft[i][2];
                            arg *= 2 * Math.PI;

                            double fact[] = {Math.cos(arg), -Math.sin(arg)};
                            rho = ao.adddot(rho, ao.complex_dot(rhog[isg], fact));
                        } else {
                            rho = ao.adddot(rho, rhog[isg]);
                        }
                    }
                    rho = ao.mdot(rho, 1.0 / param.nsym);
                    for (int i = 0; i < param.nsym; i++) {
                        int isg = param.vect.get(key).get(irot[i]);
                        if (non_symmorphic[i] == true) {
                            double arg = param.g.g[isg][0] * ft[i][0]
                                    + param.g.g[isg][1] * ft[i][1]
                                    + param.g.g[isg][2] * ft[i][2];
                            arg *= 2 * Math.PI;

                            double fact[] = {Math.cos(arg), Math.sin(arg)};
                            rhog[isg] = ao.complex_dot(rho, fact);

                        } else {
                            rhog[isg] = rho;
                        }
                        doe[irot_[i]] = 1;
                    }

                }
            }
        }

        return rhog;
    }

}
