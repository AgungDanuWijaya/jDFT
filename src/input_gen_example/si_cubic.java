package input_gen_example;

import com.google.gson.Gson;
import java.io.IOException;
import main.init_data;
import main.utama_gen;

/**
 *
 * @author agung
 */
public class si_cubic {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        init_data init = new init_data();
        init.status = "nscf";
        init.smar = 0;
        init.random = 0;
        init.usp = 0;
        double celldm[] = {10.2, 0, 0, 0};
        init.ecutwfc = 18;
        init.ecutrho = 4 * 18;
        init.ibrav = 0;
        init.iband = 6;
        init.num_atom = 1;
        init.nat = 3;
        init.mix = 0.7;
        String atom[] = {"Si"};
        init.atom = atom;
        String upf_url[] = {"/home/agung/Documents/kkk/q-e-qe-6.6/pseudo/Si.pz-vbc.UPF"};
        int usp_[] = {0, 0, 0};
        init.term = celldm[0];
        double lattice[][] = {
            {init.term, 0.0000000000, 0},
            {0.0, init.term, 0},
            {0, 0, init.term}
        };
        double pos[][] = {
            {0.0000000000, 0.0000000000, 0.0000000000},
            {0.25, 0.25, 0.25},
            {0.3, 0.3, 0.3}};
        int atom_pos[] = {0, 0, 0};
      
        double k_point[][] = {{-1, 0.5, 0},
    {-0.9667, 0.4833, 0},
    {-0.9333, 0.4667, 0},
    {-0.9, 0.45, 0},
    {-0.8667, 0.4333, 0},
    {-0.8333, 0.4167, 0},
    {-0.8, 0.4, 0},
    {-0.7667, 0.3833, 0},
    {-0.7333, 0.3667, 0},
    {-0.7, 0.35, 0},
    {-0.6667, 0.3333, 0},
    {-0.6333, 0.3167, 0},
    {-0.6, 0.3, 0},
    {-0.5667, 0.2833, 0},
    {-0.5333, 0.2667, 0},
    {-0.5, 0.25, 0},
    {-0.4667, 0.2333, 0},
    {-0.4333, 0.2167, 0},
    {-0.4, 0.2, 0},
    {-0.3667, 0.1833, 0},
    {-0.3333, 0.1667, 0},
    {-0.3, 0.15, 0},
    {-0.2667, 0.1333, 0},
    {-0.2333, 0.1167, 0},
    {-0.2, 0.1, 0},
    {-0.1667, 0.0833, 0},
    {-0.1333, 0.0667, 0},    
    };
          double weig[] = new double[k_point.length];
        for (int i = 0; i < weig.length; i++) {
             weig[i]=2.0/k_point.length;
            
        }
        init.upf_url = upf_url;
        init.k_point = k_point;
        init.weig = weig;
        init.atom_pos = atom_pos;
        init.pos = pos;
        init.lattice = lattice;

        init.celldm = celldm;
        Gson gson = new Gson();

        String json = gson.toJson(init);
        System.out.println(json);

        // utama_gen ug = new utama_gen();
        // ug.main(init);
        //https://jsonlint.com/
        /* java -jar "/home/agung/Documents/solid/terpan/JavaQsolid-old (1)/JavaQsolid-old/dist/jDFT.jar" input.dat > out.dat*/
    }

}
