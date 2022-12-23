/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mixer;

import Jama.Matrix;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class dsytri {

    /**
     * @param args the command line arguments
     */
    public double[][]  main(double [][] A) {
        Matrix B=new Matrix(A);
    return B.inverse().getArray();
    }
    
}
