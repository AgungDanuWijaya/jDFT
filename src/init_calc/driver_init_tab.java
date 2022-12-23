package init_calc;

import main.parameter;

/**
 *
 * @author agung
 */
public class driver_init_tab {

    public void main(parameter param) {
        init_tab it_[] = new init_tab[param.upf_data.size()];
        for (int i = 0; i < param.upf_data.size(); i++) {
            it_[i] = new init_tab(param.upf_data.get(param.atom[i]), param);
            it_[i].start();
        }
        for (int i = 0; i < param.upf_data.size(); i++) {
            try {
                it_[i].join();
            } catch (InterruptedException ex) {

            }
        }

    }

}
