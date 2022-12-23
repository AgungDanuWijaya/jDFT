/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dft;

/**
 *
 * @author agung
 */
public class exchange {

    public double [] slater( double rs) {
        double f = -9.0 / 8.0 * Math.pow(3.0 / (2.0 * Math.PI), 2.0 / 3.0);
        double alpha = 2.0 / 3.0;
        double ex = f * alpha / rs;
        double vx = (4.0 / 3.0) * f * (alpha / rs);
        double result[]={ex,vx};
        return result;
    }

   

}
