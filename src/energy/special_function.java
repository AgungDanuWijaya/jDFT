package energy;

/**
 * Robert Sedgewick and Kevin Wayne Agung Danu Wijaya
 */
public class special_function {

    /*
	 * Error Function :
	 * https://introcs.cs.princeton.edu/java/21function/ErrorFunction.java.html
     */
    public static double erf(double x) {
        double qe_erf = 0;
        double p1[] = {2.426679552305318 * Math.pow(10, 2), 2.197926161829415 * Math.pow(10, 1),
            6.996383488619136, -3.560984370181538 * Math.pow(10, -2)};
        double q1[] = {2.150588758698612 * Math.pow(10, 2), 9.116490540451490 * Math.pow(10, 1),
            1.508279763040779 * Math.pow(10, 1), 1.000000000000000};
        if (Math.abs(x) > 6.0) {
            qe_erf = x / Math.abs(x);
        } else {
            if (Math.abs(x) > 0.47) {
                double x2 = x * x;
                qe_erf = x * (p1[0] + x2 * (p1[1] + x2 * (p1[2] + x2 * p1[3])))
                        / (q1[0] + x2 * (q1[1] + x2 * (q1[2] + x2 * q1[3])));
            } else {
                qe_erf = 1.0 - e_erfc(x);
            }
        }

        return qe_erf;
    }

    public static double e_erfc(double x) {
        double qe_erfc;
        double p2[] = {3.004592610201616E2, 4.519189537118719E2,
            3.393208167343437E2, 1.529892850469404E2,
            4.316222722205674E1, 7.211758250883094,
            5.641955174789740E-1, -1.368648573827167E-7};
        double q2[] = {3.004592609569833E2, 7.909509253278980E2,
            9.313540948506096E2, 6.389802644656312E2,
            2.775854447439876E2, 7.700015293522947E1,
            1.278272731962942E1, 1.000000000000000};
        double p3[] = {-2.996107077035422E-3, -4.947309106232507E-2,
            -2.269565935396869E-1, -2.786613086096478E-1,
            -2.231924597341847E-2};
        double q3[] = {1.062092305284679E-2, 1.913089261078298E-1,
            1.051675107067932, 1.987332018171353,
            1.000000000000000};

        double pim1 = 0.56418958354775629;
        double ax = Math.abs(x);
        if (ax > 26.0) {
            qe_erfc = 0;
        } else if (ax > 4.0) {
            double x2 = x * x;
            double xm2 = Math.pow(1.0 / ax, 2);
            qe_erfc = (1.0 / ax) * Math.exp(-x2) * (pim1 + xm2 * (p3[0]
                    + xm2 * (p3[1] + xm2 * (p3[2] + xm2 * (p3[3] + xm2 * p3[4])))) / (q3[0] + xm2 * (q3[1] + xm2 * (q3[2] + xm2
                    * (q3[3] + xm2 * q3[4])))));

        } else if (ax > 0.474) {
            double x2 = Math.pow(x, 2);
            qe_erfc = Math.exp(-x2) * (p2[0] + ax * (p2[1] + ax * (p2[2]
                    + ax * (p2[3] + ax * (p2[4] + ax * (p2[5] + ax * (p2[6]
                    + ax * p2[7]))))))) / (q2[0] + ax * (q2[1] + ax
                    * (q2[2] + ax * (q2[3] + ax * (q2[4] + ax * (q2[5] + ax
                    * (q2[6] + ax * q2[7])))))));
        } else {
            qe_erfc = 1.0 - erf(ax);
        }
        if (x < 0) {
            qe_erfc = 2.0 - qe_erfc;
        }
        return qe_erfc;
    }

}
