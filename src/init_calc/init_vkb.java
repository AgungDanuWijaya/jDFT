
package init_calc;

import main.parameter;
import java.util.HashMap;


/**
 *
 * @author agung
 */
public class init_vkb {

    public void main(parameter param,int ik) {
        HashMap<Integer, HashMap<Integer, double[]>> vkb = new vkb().main(param, ik);
        param.vkb.put(ik, vkb);
    }

}
