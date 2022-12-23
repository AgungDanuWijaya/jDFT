package init_calc;

import main.parameter;

/**
 *
 * @author agung
 */
public class driver_init_at {

    public void main(parameter param) throws InterruptedException {
         init_at it_[]=new init_at[ param.upf_data.size()];
        for (int i = 0; i < param.upf_data.size(); i++) {   
             if(param.upf_data.get(param.atom[i]).name.equals("O")){
              double r = param.upf_data.get(param.atom[i]).PP_R.get(0);                
             }
        it_[i]=new init_at(param.upf_data.get(param.atom[i]), param);       
        it_[i].start();
        }
        for (int i = 0; i < param.upf_data.size(); i++) {
        it_[i].join();
        }

    }

}
