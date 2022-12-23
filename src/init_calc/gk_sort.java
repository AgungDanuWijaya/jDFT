package init_calc;

import main.parameter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import init_grid.order;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class gk_sort {

    /**
     * @param param
     */
    public void main(parameter param) {
        HashMap<Integer, HashMap<Integer, Double>> gks = new HashMap<>();
        HashMap<Integer, HashMap<Integer, Double>> igks = new HashMap<>();
        HashMap<Integer, Integer> ngk_h = new HashMap<>();

        for (int k_p = 0; k_p < param.k_point.length; k_p++) {
            double k[] = param.k_point[k_p];
            array_operation ao = new array_operation();
            double eps8 = 1.0E-8;
            int ngk = 0;
            HashMap<Integer, Double> gk = new HashMap<>();
            HashMap<Integer, Double> igk = new HashMap<>();
            for (int ng = 0; ng < param.g.gg.length; ng++) {
                double q = ao.sum(ao.powdot(ao.adddot(k, param.g.g[ng]), 2));
                if (q <= eps8) {
                    q = 0.0;
                }
                if (q <= param.gcutw) {
                    gk.put(ngk, q);
                    igk.put(ngk, (double) ng);
                    ngk = ngk + 1;
                }
            }

            List<order> list = new ArrayList<>();
            gk.keySet().forEach(name -> {
                list.add(new order(String.valueOf(name), gk.get(name)));
            });
            Collections.sort(list,
                    Comparator.comparingDouble(order::getValue));
            HashMap<Integer, Double> igk_ = new HashMap<>();
            int in = 0;
            for (order order : list) {
                igk_.put(in, igk.get(Integer.parseInt(order.orderNo)));
                int im=Integer.parseInt(order.orderNo);
                in = in + 1;
            }
            igks.put(k_p, igk_);
            ngk_h.put(k_p, ngk);
        }
        
        param.ngk = ngk_h;
        param.igk = igks;
    }

}
