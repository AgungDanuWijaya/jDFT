
package tools;

import com.sun.jna.Native;
import jna.CInterface;
import jna.config;

/**
 *
 * @author agung
 */
public class integral {

    String libName = new config().url_lib;

    CInterface demo = (CInterface) Native.load(libName, CInterface.class);

    public double simpson_(int msh, double func[], double rab[]) {
        return demo.simpson(msh, func, rab);
        
    }

    public double simpson(int msh, double func[], double rab[]) {
        double r12 = 1.0 / 3.0;
        double f3 = func[0] * rab[0] * r12;
        double asum = 0;
        for (int i = 1; i < msh - 1; i = i + 2) {
            double f1 = f3;
            double f2 = func[i] * rab[i] * r12;
            f3 = func[i + 1] * rab[i + 1] * r12;
            asum += f1 + (4.0 * f2) + f3;
        }
        return asum;
    }

}
