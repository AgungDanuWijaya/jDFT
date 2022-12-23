
package mixer;

import main.parameter;

/**
 *
 * @author agung
 */
public class init_calbec {

    /**
     * @param param
     */
    public void main(parameter param,int ik) {
        if(param.set_cal==0){
       double bec[][][]=new calbec().main(param, ik);
       param.bec.put(ik, bec);
        }
    }

}
