package init_calc;

import main.parameter;

/**
 *
 * @author agung
 */
public class driver_init_us {

    public void main(parameter param) throws InterruptedException {
        init_us it_[]=new init_us[ param.upf_data.size()];
        for (int i = 0; i < param.upf_data.size(); i++) {               
        it_[i]=new init_us(param.upf_data.get(param.atom[i]), param);       
        it_[i].start();
        }
        for (int i = 0; i < param.upf_data.size(); i++) {
        it_[i].join();
        }       
    }
}
