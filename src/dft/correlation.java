/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dft;

/**
 *
 * @author agung
 */
public class correlation {

    public double[] pz(double rs, int iflag) {
        iflag -= 1;
        double a[] = {0.0311, 0.031091};
        double b[] = {-0.048, -0.046644};
        double c[] = {0.0020, 0.00419};
        double d[] = {-0.0116, -0.00983};
        double gc[] = {-0.1423, -0.103756};
        double b1[] = {1.0529, 0.56371};
        double b2[] = {0.3334, 0.27358};
        double ec = 0;
        double vc = 0;
        if (rs < 1.0) {
            double lnrs = Math.log(rs);
            ec = a[iflag] * lnrs + b[iflag] + c[iflag] * rs * lnrs + d[iflag] * rs;
            vc = a[iflag] * lnrs + (b[iflag] - a[iflag] / 3.0) + 2.0 / 3.0 * c[iflag] * rs * lnrs + (2.0 * d[iflag] - c[iflag]) / 3.0 * rs;
        } else {
            double rs12 = Math.sqrt(rs);
            double ox = 1.0 + b1[iflag] * rs12 + b2[iflag] * rs;
            double dox = 1.0 + 7.0 / 6.0 * b1[iflag] * rs12 + 4.0 / 3.0 * b2[iflag] * rs;
            ec = gc[iflag] / ox;
            vc = ec * dox / ox;
        }
        double result[] = {ec, vc};
        return result;
    }

}
