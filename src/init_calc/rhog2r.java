
package init_calc;

import main.parameter;
import fftw.fftw;

/**
 *
 * @author agung
 */
public class rhog2r {

    public void main(parameter param) {
        int n[] = {(int) param.nr1, (int) param.nr2, (int) param.nr3, -1};
        param.rhocr = fftw.fftw(n, param.rhocg_3d);
    }

}
