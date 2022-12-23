package tools;

import Jama.Matrix;
import com.sun.jna.Native;
import java.util.Date;
import jna.CInterface;
import jna.config;

/**
 *
 * @author Hai jhgyjg
 */
public class array_operation {

    public double[][] rotate(double in[][]) {
        double l[][] = new double[in[0].length][in.length];
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[i].length; j++) {
                l[j][i] = in[i][j];
            }
        }
        return l;
    }

    public double[][][] rotate(double in[][][]) {
        double l[][][] = new double[in[0].length][in.length][];
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[i].length; j++) {
                l[j][i] = in[i][j];
            }
        }
        return l;
    }

    public double[][] mdot(double a[][], double b) {
        double c[][] = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                c[i][j] = a[i][j] * b;
            }
        }
        return c;
    }

    public double[][][] complex_transpose(double a[][][]) {
        double res[][][] = new double[a[0].length][a.length][2];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                res[j][i][0] = a[i][j][0];
                res[j][i][1] = -a[i][j][1];
            }
        }
        return res;
    }

    public double[][][] conjugate(double a[][][]) {
        double res[][][] = new double[a.length][a[0].length][2];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                res[i][j][0] = a[i][j][0];
                res[i][j][1] = -a[i][j][1];
            }
        }
        return res;
    }

    public double[] conjugate(double a[]) {
        double res[] = {a[0], -a[1]};
        return res;
    }

    public double[][][] time_complex_(double a[][][], double b[][][]) {
        double res[][][] = new double[a.length][b[0].length][2];
        for (int i = 0; i < a.length; i++) {
            double a_i[][] = a[i];
            for (int j_s = 0; j_s < b[0].length; j_s++) {
                double re[] = {0, 0};
                for (int j = 0; j < b.length; j++) {
                    double rt[] = complex_dot(b[j][j_s], a_i[j]);
                    re = adddot(re, rt);
                }
                res[i][j_s] = re;
            }
        }
        return res;
    }

    double[] c_3_1(double[][][] rp) {
        double r[] = new double[rp.length * rp[0].length * rp[0][0].length];
        for (int i = 0; i < rp.length; i++) {
            for (int j = 0; j < rp[0].length; j++) {
                for (int k = 0; k < rp[0][0].length; k++) {

                    r[i * rp[0].length * rp[0][0].length + j * rp[0][0].length + k] = rp[i][j][k];

                }
            }
        }
        return r;
    }

    double[][][] c_1_3(double[] r, int i_, int j_, int k_) {
        double rp[][][] = new double[i_][j_][k_];
        for (int i = 0; i < i_; i++) {
            for (int j = 0; j < j_; j++) {
                for (int k = 0; k < k_; k++) {

                    rp[i][j][k] = r[i * j_ * k_ + j * k_ + k];
                }
            }
        }
        return rp;
    }

    public double[][][] time_complex(double a[][][], double b[][][]) {
        String libName = new config().url_lib;
       
        // Loading dynamically the library
        CInterface demo = (CInterface) Native.load(libName, CInterface.class);
        double a_[] = c_3_1(a);
        double b_[] = c_3_1(b);
        double c_[] = new double[a.length * b[0].length * 2];

        demo.time_complex(a_, b_, c_, a.length, a[0].length, 2, b.length, b[0].length, 2, a.length, b[0].length, 2);
        double hasi[][][] = c_1_3(c_, a.length, b[0].length, 2);
        return hasi;
    }

    public double[] complex_dot(double a[], double b[]) {
        double c[] = {a[0] * b[0] + (-(a[1] * b[1])), a[0] * b[1] + a[1] * b[0]};

        return c;
    }

    public double[] mdot(double a[], double b) {
        double c[] = new double[a.length];
        for (int j = 0; j < a.length; j++) {
            c[j] = a[j] * b;
        }
        return c;
    }

    public double[] nint(double a[]) {
        double c[] = new double[a.length];
        for (int j = 0; j < a.length; j++) {
            c[j] = (int) Math.round(a[j]);
        }
        return c;
    }

    public double[] mdot(double a[], double b[]) {
        double c[] = new double[a.length];
        for (int j = 0; j < a.length; j++) {
            c[j] = a[j] * b[j];
        }
        return c;
    }

    public double[][] mdot(double a[][], double b[][]) {
        double c[][] = new double[a.length][a[0].length];
        for (int j = 0; j < a.length; j++) {
            for (int i = 0; i < a[j].length; i++) {
                c[j][i] = a[j][i] * b[j][i];
            }
        }
        return c;
    }

    public double[][] copy(double a[][]) {
        double b[][] = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                b[i][j] = a[i][j];
            }
        }
        return b;
    }

    public double[][][] copy(double a[][][]) {
        double b[][][] = new double[a.length][a[0].length][a[0][0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                for (int k = 0; k < a[0][0].length; k++) {
                    b[i][j][k] = a[i][j][k];
                }
            }
        }
        return b;
    }

    public double[] copy(double a[]) {
        double b[] = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            b[i] = a[i];
        }
        return b;
    }

    public double[][] adddot(double a[][], double b) {
        double c[][] = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                c[i][j] = a[i][j] + b;
            }
        }
        return c;
    }

    public double[] adddot(double a[], double b) {
        double c[] = new double[a.length];
        for (int j = 0; j < a.length; j++) {
            c[j] = a[j] + b;
        }
        return c;
    }

    public double[] adddot(double a[], double b[]) {
        double c[] = new double[a.length];
        for (int j = 0; j < a.length; j++) {
            c[j] = a[j] + b[j];
        }
        return c;
    }

    public double[] subdot(double a[], double b[]) {
        double c[] = new double[a.length];
        for (int j = 0; j < a.length; j++) {
            c[j] = a[j] - b[j];
        }
        return c;
    }

    public double[] mindotabs(double a[], double b[]) {
        double c[] = new double[a.length];
        for (int j = 0; j < a.length; j++) {
            c[j] = Math.abs(a[j] - b[j]);
        }
        return c;
    }

    public double[][] adddot(double a[][], double b[][]) {
        double c[][] = new double[a.length][a[0].length];
        for (int j = 0; j < a.length; j++) {
            for (int i = 0; i < a[0].length; i++) {
                c[j][i] = a[j][i] + b[j][i];
            }
        }
        return c;
    }

    public double[][][] adddot(double a[][][], double b[][][]) {
        double c[][][] = new double[a.length][a[0].length][a[0][0].length];
        for (int j = 0; j < a.length; j++) {
            c[j] = adddot(a[j], b[j]);
        }
        return c;
    }

    public double[] adddotabs(double a[], double b[]) {
        double c[] = new double[a.length];
        for (int j = 0; j < a.length; j++) {
            c[j] = Math.abs(a[j] + b[j]);
        }
        return c;
    }

    public double[] adddotabs(double a[][], double b[][]) {
        double c[] = new double[a.length];
        for (int j = 0; j < a.length; j++) {
            for (int i = 0; i < a[j].length; i++) {
                c[j] = Math.abs(a[j][i] + b[j][i]);
            }

        }
        return c;
    }

    public double[][] powdot(double a[][], double b) {
        double c[][] = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                c[i][j] = Math.pow(a[i][j], b);
            }
        }
        return c;
    }

    public double[] powdot(double a[], double b) {
        double c[] = new double[a.length];
        for (int j = 0; j < a.length; j++) {
            c[j] = Math.pow(a[j], b);
        }
        return c;
    }

    public void disp(double a[][]) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.format(a[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public void disp(double a[][][], int ik) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.format(a[i][j][ik] + " ");
            }
            System.out.println("");
        }
    }
    public void disp_imag(double a[][][]) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.format(a[i][j][0] + "+ "+a[i][j][1]+"#");
            }
            System.out.println("");
        }
    }
    public void disp_(double a[][][]) {
        for (int i = 0; i < a.length; i++) {
            System.out.println("{");
            for (int j = 0; j < a[i].length; j++) {
                System.out.format("{" + a[i][j][0] + ", " + a[i][j][1] + "},");
            }
            System.out.println("},");
        }
    }

    public void disp(double a[]) {
        for (int j = 0; j < a.length; j++) {
            System.out.format(a[j] + " ");
        }
        System.out.println("");
    }

    public void disp(double a[], String kl) {
        System.out.print(kl + " ");

        for (int j = 0; j < a.length; j++) {
            System.out.format(a[j] + ", ");
        }
        System.out.println("");
    }

    public void disp_t(double a[]) {
        for (int j = 0; j < a.length; j++) {
            System.out.println(a[j]);
        }
        System.out.println("");
    }

    public void disp(String a[]) {
        for (int j = 0; j < a.length; j++) {
            System.out.format(a[j] + " ");
        }
        System.out.println("");
    }

    public void dispa(double a[]) {
        System.out.print("{");
        for (int j = 0; j < a.length - 1; j++) {
            System.out.format(a[j] + " , ");
        }
        System.out.println(a[a.length - 1] + " },");
    }

    public String geta(double a[]) {
        String h = "";
        for (int j = 0; j < a.length; j++) {
            h += ("'" + a[j] + "' , ");
        }
        return h;
    }

    public void disp(int a[]) {
        for (int j = 0; j < a.length; j++) {
            System.out.format(a[j] + " ");
        }
        System.out.println("");
    }

    public void disp(String a) {
        System.out.println(a);
    }

    public double sum(double a[]) {
        double r = 0;
        for (int j = 0; j < a.length; j++) {
            r += a[j];
        }
        return r;
    }

    public double sum(int a[]) {
        double r = 0;
        for (int j = 0; j < a.length; j++) {
            r += a[j];
        }
        return r;
    }

    public double sum(double a[][]) {
        double r = 0;
        for (int j = 0; j < a.length; j++) {
            for (int i = 0; i < a[j].length; i++) {
                r += a[j][i];
            }
        }
        return r;
    }

    public double sumabs(double a[][]) {
        double r = 0;
        for (int j = 0; j < a.length; j++) {
            for (int i = 0; i < a[j].length; i++) {
                r += Math.abs(a[j][i]);
            }
        }
        return r;
    }
}
