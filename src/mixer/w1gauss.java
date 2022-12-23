
package mixer;

import org.apache.commons.math3.special.Erf;

/**
 *
 * @author agung
 */
public class w1gauss {

    /**
     * @param args the command line arguments
     */
    public double main(double x) {
        double maxarg = 200.0;
        double xp = x - 1.00 / Math.sqrt(2.00);
        double arg = Math.min(maxarg, Math.pow(xp, 2));
        double wgauss =1.00 / Math.sqrt (2.00 * Math.PI) * xp * Math.exp ( - arg);
        return  wgauss;
    }

}
