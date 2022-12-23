
package jna;

import com.sun.jna.Library;

public interface CInterface extends Library {

    public double besel(double n, double x);

    void besel_all(double r[], double out[], int pan, double n, double q);

    public void sum(double[] n1, double[] n3, int n2);

    public void mdot(double a[], double b, int i, int j, int k);

    public void time_complex(double a[], double b[], double c[], int a_i, int a_j, int a_k, int b_i, int b_j, int b_k, int c_i, int c_j, int c_k);

    public double simpson(int msh, double func[], double rab[]);
}
