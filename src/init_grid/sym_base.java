package init_grid;

import Jama.Matrix;
import main.parameter;
import java.util.HashMap;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class sym_base {

    String data = "1.d0,  0.d0,  0.d0,  0.d0,  1.d0,  0.d0,  0.d0,  0.d0,  1.d0, &\n"
            + "             -1.d0,  0.d0,  0.d0,  0.d0, -1.d0,  0.d0,  0.d0,  0.d0,  1.d0, &\n"
            + "             -1.d0,  0.d0,  0.d0,  0.d0,  1.d0,  0.d0,  0.d0,  0.d0, -1.d0, &\n"
            + "              1.d0,  0.d0,  0.d0,  0.d0, -1.d0,  0.d0,  0.d0,  0.d0, -1.d0, &\n"
            + "              0.d0,  1.d0,  0.d0,  1.d0,  0.d0,  0.d0,  0.d0,  0.d0, -1.d0, &\n"
            + "              0.d0, -1.d0,  0.d0, -1.d0,  0.d0,  0.d0,  0.d0,  0.d0, -1.d0, &\n"
            + "              0.d0, -1.d0,  0.d0,  1.d0,  0.d0,  0.d0,  0.d0,  0.d0,  1.d0, &\n"
            + "              0.d0,  1.d0,  0.d0, -1.d0,  0.d0,  0.d0,  0.d0,  0.d0,  1.d0, &\n"
            + "              0.d0,  0.d0,  1.d0,  0.d0, -1.d0,  0.d0,  1.d0,  0.d0,  0.d0, &\n"
            + "              0.d0,  0.d0, -1.d0,  0.d0, -1.d0,  0.d0, -1.d0,  0.d0,  0.d0, &\n"
            + "              0.d0,  0.d0, -1.d0,  0.d0,  1.d0,  0.d0,  1.d0,  0.d0,  0.d0, &\n"
            + "              0.d0,  0.d0,  1.d0,  0.d0,  1.d0,  0.d0, -1.d0,  0.d0,  0.d0, &\n"
            + "             -1.d0,  0.d0,  0.d0,  0.d0,  0.d0,  1.d0,  0.d0,  1.d0,  0.d0, &\n"
            + "             -1.d0,  0.d0,  0.d0,  0.d0,  0.d0, -1.d0,  0.d0, -1.d0,  0.d0, &\n"
            + "              1.d0,  0.d0,  0.d0,  0.d0,  0.d0, -1.d0,  0.d0,  1.d0,  0.d0, &\n"
            + "              1.d0,  0.d0,  0.d0,  0.d0,  0.d0,  1.d0,  0.d0, -1.d0,  0.d0, &\n"
            + "              0.d0,  0.d0,  1.d0,  1.d0,  0.d0,  0.d0,  0.d0,  1.d0,  0.d0, &\n"
            + "              0.d0,  0.d0, -1.d0, -1.d0,  0.d0,  0.d0,  0.d0,  1.d0,  0.d0, &\n"
            + "              0.d0,  0.d0, -1.d0,  1.d0,  0.d0,  0.d0,  0.d0, -1.d0,  0.d0, &\n"
            + "              0.d0,  0.d0,  1.d0, -1.d0,  0.d0,  0.d0,  0.d0, -1.d0,  0.d0, &\n"
            + "              0.d0,  1.d0,  0.d0,  0.d0,  0.d0,  1.d0,  1.d0,  0.d0,  0.d0, &\n"
            + "              0.d0, -1.d0,  0.d0,  0.d0,  0.d0, -1.d0,  1.d0,  0.d0,  0.d0, &\n"
            + "              0.d0, -1.d0,  0.d0,  0.d0,  0.d0,  1.d0, -1.d0,  0.d0,  0.d0, &\n"
            + "              0.d0,  1.d0,  0.d0,  0.d0,  0.d0, -1.d0, -1.d0,  0.d0,  0.d0, &\n"
            + "              cos3,  sin3, 0.d0, msin3,  cos3, 0.d0, 0.d0, 0.d0,  1.d0, &\n"
            + "              cos3, msin3, 0.d0,  sin3,  cos3, 0.d0, 0.d0, 0.d0,  1.d0, &\n"
            + "             mcos3,  sin3, 0.d0, msin3, mcos3, 0.d0, 0.d0, 0.d0,  1.d0, &\n"
            + "             mcos3, msin3, 0.d0,  sin3, mcos3, 0.d0, 0.d0, 0.d0,  1.d0, &\n"
            + "              cos3, msin3, 0.d0, msin3, mcos3, 0.d0, 0.d0, 0.d0, -1.d0, &\n"
            + "              cos3,  sin3, 0.d0,  sin3, mcos3, 0.d0, 0.d0, 0.d0, -1.d0, &\n"
            + "             mcos3, msin3, 0.d0, msin3,  cos3, 0.d0, 0.d0, 0.d0, -1.d0, &\n"
            + "             mcos3,  sin3, 0.d0,  sin3,  cos3, 0.d0, 0.d0, 0.d0, -1.d0 &\n";

    public void main(parameter param) {
        double sin3 = 0.8660254037844385970;
        double cos3 = 0.50;
        double msin3 = -0.8660254037844385970;
        double mcos3 = -0.50;
        String da = new sym_base().data.replace("\n", "").replace("d", "").replace("msin3", msin3 + "").replace("mcos3", mcos3 + "").replace("sin3", sin3 + "").replace("cos3", cos3 + "");
        String data_[] = da.split("&");
        HashMap<Integer, double[][]> dat = new HashMap<>();
        for (int i = 0; i < data_.length; i++) {
            String data__[] = data_[i].replace(" ", "").split(",");
            double dat_[][] = new double[3][3];
            for (int j = 0; j < 3; j++) {
                int bas = 3;
                for (int k = 0; k < 3; k++) {
                    double kl = Double.parseDouble(data__[(int) (j * bas + k)]);
                    dat_[j][k] = kl;
                }
            }
            dat.put(i, dat_);
        }
        double rot[][] = new double[3][3];
        for (int jpol = 0; jpol < 3; jpol++) {
            for (int kpol = 0; kpol < 3; kpol++) {
                rot[kpol][jpol] = param.at[kpol][0] * param.at[jpol][0]
                        + param.at[kpol][1] * param.at[jpol][1]
                        + param.at[kpol][2] * param.at[jpol][2];
            }
        }
        Matrix overlap_ = new Matrix(rot);
        //overlap_.print(3, 3);
        overlap_ = overlap_.inverse();
        //overlap_.print(3, 3);
        double overlap[][] = overlap_.getArray();

        array_operation ao = new array_operation();
        int nrot = 0;
        double eps1 = param.eps1;
        HashMap<Integer, HashMap<Integer, HashMap<Integer, Integer>>> s = new HashMap<>();
        for (int i = 0; i < 32; i++) {
            double rat[] = new double[3];
            for (int jpol = 0; jpol < 3; jpol++) {
                for (int mpol = 0; mpol < 3; mpol++) {
                    rat[mpol] = dat.get(i)[0][mpol] * param.at[jpol][0]
                            + dat.get(i)[1][mpol] * param.at[jpol][1]
                            + dat.get(i)[2][mpol] * param.at[jpol][2];
                }
                for (int kpol = 0; kpol < 3; kpol++) {
                    rot[kpol][jpol] = param.at[kpol][0] * rat[0]
                            + param.at[kpol][1] * rat[1]
                            + param.at[kpol][2] * rat[2];
                }
            }

            int y = 0;
            HashMap<Integer, HashMap<Integer, Integer>> s_jpol = new HashMap<>();
            for (int jpol = 0; jpol < 3; jpol++) {
                HashMap<Integer, Integer> s_kpol = new HashMap<>();
                for (int kpol = 0; kpol < 3; kpol++) {
                    double value = overlap[jpol][0] * rot[0][kpol]
                            + overlap[jpol][1] * rot[1][kpol]
                            + overlap[jpol][2] * rot[2][kpol];
                    if (Math.abs(value - (int) Math.round(value)) > eps1) {
                        jpol = 100;
                        break;
                    } else {
                        y += 1;
                        s_kpol.put(kpol, (int) Math.round(value));
                    }
                }
                s_jpol.put(jpol, s_kpol);
            }
            if (y > 0) {
                s.put(nrot, s_jpol);
                nrot += 1;
            }
        }
        double s_[][][] = new double[s.size() * 2][3][3];
        for (int i = 0; i < s.size(); i++) {

            for (int jpol = 0; jpol < 3; jpol++) {
                for (int kpol = 0; kpol < 3; kpol++) {
                    try {
                        s_[i][jpol][kpol] = s.get(i).get(kpol).get(jpol);// perbaiki ini,diputar/sudah oke
                        s_[i + s.size()][jpol][kpol] = -s.get(i).get(kpol).get(jpol);
                    } catch (Exception e) {
                    }

                }
            }
        }

        param.s_ = s_;
    }

    public void ft(parameter param) {
        double eps2 = 1.0E-5;
        double ftaux[] = new double[3];
        array_operation ao = new array_operation();
        double xau[][] = new double[param.pos.length][];
        double bg[][] = ao.rotate(param.bg);
        for (int i = 0; i < param.pos.length; i++) {
            xau[i] = ao.mdot(bg[0], param.pos[i][0]);
            xau[i] = ao.adddot(xau[i], ao.mdot(bg[1], param.pos[i][1]));
            xau[i] = ao.adddot(xau[i], ao.mdot(bg[2], param.pos[i][2]));
        }
        int nb = 0;
        int irot = 0;
        boolean fractional_translations = true;
        double ft_[] = new double[3];
        if (fractional_translations) {
            for (int na = 1; na < param.pos.length; na++) {
                if (param.atom_p[nb].equals(param.atom_p[na])) {
                    ft_ = ao.adddot(xau[na], ao.mdot(xau[nb], -1));
                    ft_ = ao.adddot(ft_, ao.mdot(ao.nint(ft_), -1));
                    boolean sy = checksym(xau, xau, ft_, param);
                    if (sy) {
                        fractional_translations = false;
                        break;
                    }
                }
            }
        }
        double ft[][] = new double[param.s_.length][3];
        double nsym[] = new double[param.s_.length];
        for (irot = 0; irot < param.s_.length; irot++) {

            double rau[][] = new double[param.pos.length][3];
            for (int i = 0; i < param.pos.length; i++) {
                rau[i] = ao.mdot(param.s_[irot][0], xau[i][0]);
                rau[i] = ao.adddot(rau[i], ao.mdot(param.s_[irot][1], xau[i][1]));
                rau[i] = ao.adddot(rau[i], ao.mdot(param.s_[irot][2], xau[i][2]));

            }
            ft_ = ao.mdot(ft_, 0);
            boolean sy = checksym(xau, rau, ft_, param);
            if (sy) {
                nsym[irot] = 1;
            }
            if (sy == false & fractional_translations == true) {
                nb = 0;
                for (int na = 0; na < param.pos.length; na++) {
                    if (param.atom_p[nb].equals(param.atom_p[na])) {
                        ft_ = ao.adddot(rau[na], ao.mdot(xau[nb], -1));
                        ft_ = ao.adddot(ft_, ao.mdot(ao.nint(ft_), -1));
                        for (int i = 0; i < ft_.length; i++) {
                            if (Math.abs(ft_[i]) > eps2) {
                                ftaux[i] = Math.abs((1.0 / ft_[i]) - (int) (Math.round(1.0 / ft_[i])));
                                double nfrac = (int) (Math.round(1.0 / Math.abs(ft_[i])));
                                if (ftaux[i] < eps2 & nfrac != 2 & nfrac != 3 & nfrac != 4 & nfrac != 6) {
                                    ftaux[i] = 2 * eps2;
                                }
                            } else {
                                ftaux[i] = 0.0;
                            }
                        }

                        int rt = 0;
                        for (int i = 0; i < ft_.length; i++) {
                            if (ftaux[i] > eps2) {
                                rt = 1;
                            }
                        }
                        if (rt == 0) {

                            boolean sym = checksym(xau, rau, ft_, param);
                            if (sym) {
                                nsym[irot] = 1;
                                ft[irot] = ft_;
                            }
                        }
                    }

                }
            }

        }
        int jrot = -1;
        for (int ir = 0; ir < nsym.length; ir++) {
            if (nsym[ir] == 1) {
                jrot++;
                if (ir > jrot) {
                    double stemp[][] = ao.copy(param.s_[jrot]);
                    param.s_[jrot] = ao.copy(param.s_[ir]);
                    param.s_[ir] = ao.copy(stemp);
                }
            }
        }
        HashMap<Integer, Integer> invs = new HashMap<>();
        compare banding = new compare();
        for (int isym = 0; isym < param.s_.length; isym++) {
            int found = 0;
            for (int jsym = 0; jsym < param.s_.length; jsym++) {
                Matrix i = new Matrix(param.s_[isym]);
                Matrix j = new Matrix(param.s_[jsym]);
                Matrix r = j.times(i);
                Matrix lk = new Matrix(param.s_[0]);
                if (banding.main(r.getArray(), param.s_[0]) == 1) {
                    if (found == 0) {
                        invs.put(isym, jsym);
                        found = 1;
                    }
                }
            }
        }
        param.invs = invs;
        param.nsym = (int) ao.sum(nsym);
        param.ft = ft;
    }

    public boolean eqvect(double xau[], double rau[], double ft_[], double accep) {
        array_operation ao = new array_operation();
        boolean k = true;
        for (int i = 0; i < rau.length; i++) {
            double a = Math.abs(rau[i] - xau[i] - ft_[i] - (int) Math.round(rau[i] - xau[i] - ft_[i]));
            if (a > accep) {
                k = false;
            }
        }

        return k;
    }

    public boolean checksym(double xau[][], double rau[][], double ft_[], parameter param) {
        double accep = 1.0E-5;
        int m = 0;
        boolean ceksum = true;
        for (int na = 0; na < param.pos.length; na++) {
            m = 0;
            for (int nb = 0; nb < param.pos.length; nb++) {
                if (param.atom_p[nb].equals(param.atom_p[na])) {
                    ceksum = eqvect(xau[na], rau[nb], ft_, accep);
                    if (ceksum) {
                        m = 1;
                        break;
                    }
                }
            }
            if (m == 0) {
                ceksum = false;
                break;
            }

        }
        return ceksum;
    }

}
