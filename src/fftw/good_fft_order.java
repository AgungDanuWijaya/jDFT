/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fftw;

/**
 *
 * @author agung
 */
public class good_fft_order {

    /**
     * @param args the command line arguments
     */
    public double main(double n) {
        double log2n = Math.log(n) / Math.log(2.0);
        double nx = 0;
        if (Math.abs(Math.round(log2n) - log2n) < 1.0e-8) {
            nx = n + 1;
        }
        if ((n % 2) == 0) {
            nx = n + 1;

        }
       return nx;
    }

}
