package main;

import energy.diag_a;
import energy.non_local;
import java.util.Date;
import psudo.param_upf;
import psudo.read_upf;
import init_calc.driver_atomic_rho;
import init_calc.driver_init_at;
import init_calc.driver_init_tab;
import init_grid.ggen;
import init_grid.latgen;
import init_grid.recips;
import init_grid.structur_factor;
import init_grid.volume;
import init_calc.driver_deeq;
import energy.driver_v_loc;
import energy.ewald;
import energy.h_psi_a;
import energy.v_hartree;
import init_grid.sym_base;
import init_grid.symme;
import init_calc.aainit;
import init_calc.driver_init_us;
import init_calc.gk_sort;
import init_calc.init_vkb;
import init_calc.normalisasi;
import init_calc.usnldiag;
import init_calc.wfcinit;
import mixer.init_calbec;
import mixer.interpolate;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class init_AntDFT_gen {

    /**
     * @param args the command line arguments
     */
    public parameter main(int ik, double campur,init_data init) throws InterruptedException {
        Date date = new Date();
        long t1 = date.getTime();
        array_operation ao = new array_operation();
        parameter param = new parameter();
        
        param.usp=(int)init.usp;
        param.celldm = init.celldm;
         param.random = init.random;
        param.ecutwfc = init.ecutwfc;
        param.ecutrho = init.ecutrho;//4 * param.ecutwfc;
        param.ibrav = init.ibrav;
        param.iband = init.iband;// settingan banyak pita
        param.banyak_atom = init.num_atom;
        param.nat = init.nat;
        param.mix = init.mix;
        
        param.dual = param.ecutrho / param.ecutwfc;
         if (param.ibrav != 0) {
            param.at = new latgen().latgen(param);
        } else {
            param.at = init.lattice;
        }
        param.at = ao.mdot(param.at, 1.0 / param.celldm[0]);
        //ao.disp(param.at);
        param.bg = new recips().recips(param.at);
        //ao.disp(param.bg);
        new volume().main(param);
        param.campur = campur / param.omega;
        new ggen().main(param);
        
        if (param.nr1s > param.nr1) {
            param.nr1s = param.nr1;
            param.nr2s = param.nr2;
            param.nr3s = param.nr3;
            param.nl_s = param.nl;
        }
        
    
        String atom[] = init.atom;
        
        for (int i = 0; i < param.banyak_atom; i++) {
            param.tipe.put(atom[i], i);
            param.upf_data.put(atom[i], new param_upf());
        }
        
        param.atom = atom;
        
        
        for (int i = 0; i < param.banyak_atom; i++) {
             param.upf_data.get(atom[i]).name = atom[i];
        param.upf_data.get(atom[i]).usp = (int)init.usp;
        param.upf_data.get(atom[i]).url_upf = init.upf_url[i];
        }
       
        
        
        read_upf r_up = new read_upf();
        
        for (int i = 0; i < param.banyak_atom; i++) {
            r_up.main(param.upf_data.get(atom[i]));
            if (param.lmax < param.upf_data.get(atom[i]).lmax) {
                param.lmax = param.upf_data.get(atom[i]).lmax;
            }
        }
        param.lmaxkb = param.lmax;
        param.lmax = param.lmax * 2 + 1;
        new aainit().main(param);
        for (int i = 0; i < param.upf_data.size(); i++) {
            new normalisasi().main(param.upf_data.get(param.atom[i]), param);
        }
        
       String atom_pos[] = new String[init.atom_pos.length];
        for (int i = 0; i < atom_pos.length; i++) {
            atom_pos[i]=atom[init.atom_pos[i]];
        }
        param.atom_p = atom_pos;
        double pos[][] =init.pos;
        param.pos = pos;
        
            new sym_base().main(param);
              new sym_base().ft(param);
        new symme().main(param);
      
        
        double k_point[][] = init.k_point;
        
        param.k_point = k_point;
        new structur_factor().main(param);
        new driver_atomic_rho().main(param);
        param.awal_panjang = ((int) param.tot_muatan) / 2;

        new aainit().main(param);
        new driver_init_tab().main(param);
        new driver_init_us().main(param);
        new driver_init_at().main(param);
        new gk_sort().main(param);
        
        new driver_v_loc().main(param);
        
        new init_vkb().main(param, ik);
        
        new wfcinit().main(param, ik);
        
        new dft.dft_driver().driver(param);
        new v_hartree().main(param);
        new driver_deeq().main(param);
        new usnldiag().main(param, ik);
        new interpolate().main(param, ik);
        
        new init_calbec().main(param, ik);
        new non_local().main(param, ik);
        new h_psi_a().main(param, ik);
        new diag_a().main(param, ik);
new ewald().main(param, param.g.gg.length);
        return param;
    }
}
