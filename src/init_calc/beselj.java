package init_calc;

import com.sun.jna.Native;
import org.apache.commons.math3.special.BesselJ;
import jna.CInterface;
import jna.config;

/**
 *
 * @author agung
 */
public class beselj {

    public double main(double r, double q, double l) {
        double x = q * r;
        if (x == 0) {
            x = 0.000000000000000000000000000000001;
        }
        double n = l;
        double j = Math.sqrt(Math.PI / (2.0 * x));
        j = j * BesselJ.value(n + 0.5, x);       
        return j;
    }

   
}
