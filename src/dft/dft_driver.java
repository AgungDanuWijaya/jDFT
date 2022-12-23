/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dft;

import main.parameter;

/**
 *
 * @author agung
 */
public class dft_driver {

    public void driver(parameter param) {
        double rho_threshold = 1.E-10;
        double exc_result[][] = new double[param.rhocr[0].length][2];
        double cor_result[][] = new double[param.rhocr[0].length][2];
        double v[] = new double[param.rhocr[0].length];
        double rs;
        double energi = 0;

        exchange exc = new exchange();
        correlation cor = new correlation();
        for (int i = 0; i < param.rhocr[0].length; i++) {
            double rho = Math.abs(param.rhocr[0][i]);
            if (rho > rho_threshold) {
                rs = Math.pow(3.0 / (4.0 * Math.PI), 1.0 / 3.0) / Math.pow(rho, 1.0 / 3.0);
                exc_result[i] = exc.slater(rs);
                cor_result[i] = cor.pz(rs, 1);
                v[i] = param.e2 * (exc_result[i][1] + cor_result[i][1]);
                energi += param.e2 * (exc_result[i][0] + cor_result[i][0]) * rho;
            }
        }
        param.etxc = energi * param.omega / (param.nr1 * param.nr2 * param.nr3);
        param.v_dft = v;
    }

}
