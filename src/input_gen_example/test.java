/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package input_gen_example;

import com.google.gson.Gson;
import java.io.IOException;
import main.init_data;
import main.utama_gen;

/**
 *
 * @author agung
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        String input = "{\"status\":\"scf\",\"smar\":0,\"random\":0,\"usp\":0.0,\"celldm\":[10.2,0.0,0.0,0.0],\"ecutwfc\":18.0,\"ecutrho\":72.0,\"ibrav\":0,\"iband\":6,\"num_atom\":1,\"nat\":3,\"mix\":0.7,\"atom\":[\"Si\"],\"upf_url\":[\"/home/agung/Documents/kkk/q-e-qe-6.6/pseudo/Si.pz-vbc.UPF\"],\"usp_\":[0,0,0],\"term\":10.2,\"lattice\":[[10.2,0.0,0.0],[0.0,10.2,0.0],[0.0,0.0,10.2]],\"degauss_\":0.02,\"pos\":[[0.0,0.0,0.0],[0.25,0.25,0.25],[0.3,0.3,0.3]],\"atom_pos\":[0,0,0],\"weig\":[2.0],\"k_point\":[[0.0,0.0,0.0]]}\n"
                + "";
        Gson gson = new Gson();
        init_data init = gson.fromJson(input, init_data.class);
        utama_gen ug = new utama_gen();
        ug.main(init);
    }

}
