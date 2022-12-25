package main;

import energy.diag_a;
import energy.diag_b;
import energy.diag_c;
import energy.h_psi_a;
import energy.h_psi_b;
import energy.h_psi_c;
import energy.init_make_wave;
import energy.make_wave;
import energy.non_local;
import energy.v_hartree;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import init_calc.driver_deeq;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import mixer.init_calbec;
import mixer.interpolate;
import mixer.mix_rho;
import mixer.sum_band_rho;
import mixer.sum_band_utama;
import mixer.weight;
import tools.array_operation;

public class utama_gen {

    public void main(init_data init) throws InterruptedException, FileNotFoundException, IOException {
        double weig[] = init.weig;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
        System.out.println(formatter.format(new Date()) + " tanggal 1_0");
        array_operation ao = new array_operation();
        parameter params[] = new parameter[weig.length];
        ObjectOutputStream outputStream;
        double total = 9999999;
        HashMap<Integer, Double> total_list = new HashMap<>();
        for (int kloop = 0; kloop < weig.length; kloop++) {
            params[kloop] = new init_AntDFT_gen().main(kloop, weig[kloop], init);
            params[kloop].scf = -1;
            params[kloop].smar = init.smar;
            params[kloop].max_base = 8;
            params[kloop].wk = ao.copy(weig);
            params[kloop].wg = new double[params[kloop].iband];

        }
        int total_iter = 0;
        for (int scf = 0; scf < 100; scf++) {//electron//scf
            total_iter = scf;
            double rho[][] = new double[(int) (params[0].nr1s * params[0].nr2s * params[0].nr3s)][2];;

            for (int kloop = 0; kloop < weig.length; kloop++) {
                
                int ik = kloop;
                if (scf > 0) {
                    double con = 0.10 * params[kloop].dr / Math.max(1.0, params[kloop].tot_muatan);
                    params[kloop].con = con;
                }
                //  params[kloop].con = 0.000000000001;//Kalo kecil begini iterasi jadi lama// meding ini dibesarkan, bandnya diperbanyak pada pencarian band

                
                params[kloop].scf += 1;
                if (params[kloop].scf != 0) {
                    new dft.dft_driver().driver(params[kloop]);
                    new v_hartree().main(params[kloop]);
                    new interpolate().main(params[kloop], ik);
                }

                for (int j = 0; j < 5; j++) {//c_band//david_llop
                    params[kloop].nbase = params[kloop].iband;
                    params[kloop].notcnv = params[kloop].iband;
                    params[kloop].tannn = 0;
                    new driver_deeq().set_Deeq(params[kloop]);

                    params[kloop].iter_big = j;

                    new init_calbec().main(params[kloop], ik);

                    params[kloop].set_cal = 0;

                    new non_local().main(params[kloop], ik);
                    new h_psi_a().main(params[kloop], ik);
                    if (params[kloop].scf != 0) {
                        new diag_a().main(params[kloop], ik);
                    }
                    new init_make_wave().main(params[kloop], ik);
                    params[kloop].non_conv = params[kloop].iband + 10;
                    params[kloop].pan = params[kloop].iband;
                    for (int i = 0; i < 20; i++) {//cegter
                        if (params[kloop].non_conv != 0) {
                            params[kloop].iter = i;
                            new make_wave().main(params[kloop], ik);
                            new init_calbec().main(params[kloop], ik);
                            new non_local().main(params[kloop], ik);
                            if (params[kloop].non_conv + params[kloop].pan > 2 * params[kloop].iband) {
                                new h_psi_b().main(params[kloop], ik);
                                new diag_b().main(params[kloop], ik);
                            } else {
                                new h_psi_c().main(params[kloop], ik);
                                new diag_c().main(params[kloop], ik);
                                params[kloop].pan += params[kloop].non_conv_0;
                            }
                        } else {
                            i = 40;
                        }
                    }
                    if (params[kloop].non_conv == 0) {
                        j = 50;
                    }
                }

            }
            new weight().main(params, 0, params[0].wk.length, init.degauss_);
            for (int kloop = 0; kloop < weig.length; kloop++) {
                double vrs[] = ao.adddot(params[kloop].v_dft, params[kloop].v_h);
                double enrgi = 0;
                for (int i = 0; i < params[kloop].rhocr[0].length; i++) {
                    double rho_ = Math.abs(params[kloop].rhocr[0][i]);
                    enrgi += rho_ * vrs[i];
                }
                params[kloop].deband = -enrgi * params[kloop].omega / (params[kloop].nr1 * params[kloop].nr2 * params[kloop].nr3);
                
                int ik = kloop;
                rho = new sum_band_rho().main(params[kloop], ik);
                outputStream = new ObjectOutputStream(new FileOutputStream("rhog"));
                outputStream.writeObject(params[kloop].rhocg);
                outputStream = new ObjectOutputStream(new FileOutputStream("rhor"));
                outputStream.writeObject(params[kloop].rhocr);

                new sum_band_utama().main(params[kloop], ik, rho);
                mix_rho mr = new mix_rho();
                mr.main_g(params[kloop], ik);
                mr.main_r(params[kloop], ik);
                //System.out.println((params[kloop].ewald + " " + params[kloop].etxc + " " + params[kloop].ehart + " " + params[kloop].eband + " " + params[kloop].deband + " " + params[kloop].demet) + " total energy");
                double total_e = (params[kloop].ewald + params[kloop].etxc + params[kloop].ehart + params[kloop].eband + params[kloop].deband + params[kloop].demet);
                total_list.put(scf, total_e);
                //System.out.println((params[kloop].ewald + params[kloop].etxc + params[kloop].ehart + params[kloop].eband + params[kloop].deband + params[kloop].demet) + " total energy" + Math.abs(total - total_e));
                if (Math.abs(total - total_e) < Math.pow(10, -8)) {
                    scf = 999999999;
                } else {
                    total = total_e;
                }
            }

            
        }

        
        for (int i = 0; i < total_list.size(); i++) {
            System.out.println("energi scf: " + total_list.get(i));
        }
        for (int kloop = 0; kloop < weig.length; kloop++) {
            ao.disp(params[kloop].solusi.eigen_);
        }
        System.out.println(formatter.format(new Date()) + " tanggal 1_1" + " total scf" + total_iter);

    }

}
