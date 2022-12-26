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
import java.io.FileWriter;
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

public class utama_gen_band_ {

    public void main(init_data init) throws InterruptedException, FileNotFoundException, IOException, ClassNotFoundException {
        double weig[] = init.weig;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
        System.out.println(formatter.format(new Date()) + " tanggal 1_0");
        array_operation ao = new array_operation();
        parameter params[] = new parameter[weig.length];
        ObjectOutputStream outputStream;
        double total = 9999999;
        HashMap<Integer, Double> total_list = new HashMap<>();

        for (int kloop = 0; kloop < weig.length; kloop++) {
            params[kloop] = new init_AntDFT_gen_band().main(kloop, weig[kloop], params[0], init);
            params[kloop].scf = -1;
            params[kloop].smar = init.smar;
            params[kloop].max_base = 8;
            params[kloop].wk = ao.copy(weig);
            params[kloop].wg = new double[params[kloop].iband];

        }

        for (int scf = 0; scf < 1; scf++) {//electron//scf
            double rho[][] = new double[(int) (params[0].nr1s * params[0].nr2s * params[0].nr3s)][2];;

            for (int kloop = 0; kloop < weig.length; kloop++) {
                int total_iter = 0;

                int ik = kloop;

                params[kloop].con = Math.pow(10, -16);
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
                            total_iter++;
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
        }
        System.out.println("Eigen Energy : ");
        for (int kloop = 0; kloop < weig.length; kloop++) {
            ao.disp(params[kloop].solusi.eigen_);
        }

        System.out.println(formatter.format(new Date()) + " tanggal 1_1" + " total scf");

    }

}
