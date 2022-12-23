
package init_grid;

import main.parameter;

/**
 *
 * @author agung
 */
public class cryst_to_cart {

    /**
     * @param args the command line arguments
     */
    public double[][] main(parameter param, double vec[][]) {
        double vec1[][] = new double[vec.length][vec[0].length];
        for (int j = 0; j < vec.length; j++) {
            double vau[] = new double[3];
            for (int i = 0; i < 3; i++) {
                vau[i] = param.at[i][0] * vec[j][0]
                        + param.at[i][1] * vec[j][1]
                        + param.at[i][2] * vec[j][2];
            }
            for (int i = 0; i < 3; i++) {
                vec1[j][i] = vau[i];
            }
        }
        return vec1;
    }

}
