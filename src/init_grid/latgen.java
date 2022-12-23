
package init_grid;

import main.parameter;

/**
 *
 * @author agung
 */
public class latgen {

    public double[][] latgen(parameter p) {
        double sr2 = 1.414213562373;
        double sr3 = 1.732050807569;
        double a[][] = new double[3][3];
        if (p.ibrav == 2) {
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < a[i].length; j++) {
                    a[i][j] = 0;
                }
            }
            double term = p.celldm[0] / 2.0;
            a[0][0] = -term;
            a[0][2] = term;
            a[1][1] = term;
            a[1][2] = term;
            a[2][0] = -term;
            a[2][1] = term;
        } else if (p.ibrav == 5) {
            double term1 = Math.sqrt(1.0 + 2.0 * p.celldm[3]);
            double term2 = Math.sqrt(1.0 - p.celldm[3]);
            a[1][1] = sr2 * p.celldm[0] * term2 / sr3;
            a[1][2] = p.celldm[0] * term1 / sr3;
            a[0][0] = p.celldm[0] * term2 / sr2;
            a[0][1] = -a[0][0] / sr3;
            a[0][2] = a[1][2];
            a[2][0] = -a[0][0];
            a[2][1] = a[0][1];
            a[2][2] = a[1][2];
        }
        return a;
    }

}
