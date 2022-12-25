#include <stdio.h>
#include <stdlib.h>
#include "functions.h"
#include <stdio.h>
#include <math.h>
#include <gsl/gsl_sf_bessel.h>
#include "xc.h"






double besel (double n,double x) {
    double y = gsl_sf_bessel_Jnu (n,x);
    return y;
}
void besel_all (double *r,double *out,int pan,double n,double q) {

for (int i = 0; i < pan; ++i)
    {
double r_=*(r+i); 
    double x = q * r_;
        if (x == 0) {
            x = 0.000000000000000000000000000000001;
        }
        double j = pow((22.0/7.0) / (2.0 * x),0.5);
         *(out + i) = j * gsl_sf_bessel_Jnu(n + 0.5, x);

 
    }

}
void sum (double *data, double *out, int n)
{

  for (int i = 0; i < n; ++i)
    {
      *(out + i) = *(data + i) + 10;
    }

}

void mdot (double *a, double b, int i_, int j_, int k_)
{

  for (int i = 0; i < i_; i++)
    {
      for (int j = 0; j < j_; j++)
	{
	  for (int k = 0; k < k_; k++)
	    {
	      int in = i * j_ * k_ + j * k_ + k;
	      *(a + in) = *(a + in) * b;
	    }
	}
    }
}

double simpson (int msh, double *func, double *rab)
{
  double r12 = 1.0 / 3.0;

  double f3 = (*func) * (*rab) * r12;
  double asum = 0;
  for (int i = 1; i < msh - 1; i = i + 2)
    {
      double f1 = f3;
      double f2 = (*(func + i)) * (*(rab + i)) * r12;
      f3 = *(func + i + 1) * (*(rab + i + 1)) * r12;
      asum += f1 + (4.0 * f2) + f3;
    }
  return asum;
}


void
time_complex (double *a, double *b, double *c, int a_i, int a_j, int a_k,
	      int b_i, int b_j, int b_k, int c_i, int c_j, int c_k)
{

  for (int i = 0; i < a_i; i++)
    {
      for (int j_s = 0; j_s < b_j; j_s++)
	{
	  double re[2] = { 0, 0 };
	  for (int j = 0; j < b_i; j++)
	    {
	      int in_2 = j * (b_j * b_k) + j_s * b_k;
	      int in_1 = i * (a_j * a_k) + j * a_k;
	      double a_0 = *(a + in_1);
	      double a_1 = *(a + in_1 + 1);
	      double b_0 = *(b + in_2);
	      double b_1 = *(b + in_2 + 1);
	      double a[2] = { a_0, a_1 };
	      double b[2] = { b_0, b_1 };
	      double rt[] =
		{ a[0] * b[0] + (-(a[1] * b[1])), a[0] * b[1] + a[1] * b[0] };
	      re[0] = re[0] + rt[0];
	      re[1] = re[1] + rt[1];
	    }

	  int in_3 = i * (c_j * c_k) + j_s * c_k;
	  *(c + in_3) = re[0];
	  *(c + in_3 + 1) = re[1];
	}
    }
}



