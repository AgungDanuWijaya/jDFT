
package init_grid;

/**
 *
 * @author agung
 */
public class good_fft {

    /**
     * @param args the command line arguments
     */
    public boolean main(double mr) {

        double pwr[] = new double[5];
        double factors[] = {2, 3, 5, 7, 11};
        for (int i = 0; i < factors.length; i++) {

            double fac = factors[i];
            int maxpwr = (int) (Math.log(mr) / Math.log(fac)) + 1;
            for (int j = 0; j < maxpwr; j++) {

                if (mr == 1) {
                    i = 100;
                }
                if ((mr % fac) == 0) {
                    mr = mr / fac;
                    //System.out.println(mr);
                    pwr[i] = pwr[i] + 1;
                }
            }
        }

        if (mr != 1) {
            return false;
        } else {
            return true;
        }
    }

}
