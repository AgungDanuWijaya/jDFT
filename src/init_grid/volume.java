package init_grid;

import main.parameter;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class volume {

    /**
     * @param args the command line arguments
     */
    public void main(parameter param) {
        array_operation mo = new array_operation();
        double a[][] = mo.mdot(param.at, param.celldm[0]);
        double omega = a[0][0] * (a[1][1] * a[2][2] - a[1][2] * a[2][1]);
        omega -= a[0][1] * (a[1][0] * a[2][2] - a[1][2] * a[2][0]);
        omega += a[0][2] * (a[1][0] * a[2][1] - a[1][1] * a[2][0]);
        param.omega = omega;
    }

}
