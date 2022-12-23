
package init_grid;

/**
 *
 * @author agung
 */
public class recips {

    public double[][] recips(double a[][]) {
        double den = 0;
        int l = 0;
        int i = 0;
        int j = 1;
        int k = 2;
        double s = 1.0;
        int rem = 0;
        while (rem == 0) {
            for (int l_ = 0; l_ < 3; l_++) {
                den = den + s * a[0][i] * a[1][j] * a[2][k];
                l = i;
                i = j;
                j = k;
                k = l;
            }
            i = 1;
            j = 0;
            k = 2;
            s = -s;
            if (s > 0) {
                rem = 1;
            }
        }
        double b[][] = new double[3][3];
        i = 0;
        j = 1;
        k = 2;
        for (int ipol = 0; ipol < 3; ipol++) {
            b[0][ipol] = (a[1][j] * a[2][k] - a[1][k] * a[2][j]) / den;
            b[1][ipol] = (a[2][j] * a[0][k] - a[2][k] * a[0][j]) / den;
            b[2][ipol] = (a[0][j] * a[1][k] - a[0][k] * a[1][j]) / den;

            l = i;
            i = j;
            j = k;
            k = l;
        }

        return b;
    }

}
