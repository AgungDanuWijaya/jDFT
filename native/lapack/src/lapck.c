/* cblas_example.c */

#include <stdio.h>
#include <stdlib.h>
#include "com_example_jni_JNIJava.h"
#include <math.h>

extern double* sumsquaredf_(int*, int[], double[], double[], double[], double[]);
JNIEXPORT jdoubleArray JNICALL Java_lapack_JNIJava_sumsquaredc(JNIEnv *env,
		jobject obj, jintArray param, jdoubleArray S, jdoubleArray H) {

	jsize n = (*env)->GetArrayLength(env, S);
	int r = sqrt(n / 2);
	jdouble *s = (*env)->GetDoubleArrayElements(env, S, 0);
	jdouble *h = (*env)->GetDoubleArrayElements(env, H, 0);
	jint *par = (*env)->GetIntArrayElements(env, param, 0);
	double *result;
	double *eigen;
	eigen = (double*) malloc(par[0] * sizeof(double));
	result = sumsquaredf_(&r, par, h, s, eigen, s);
	jdouble *img_isi ;
	img_isi=(jdouble*) malloc((par[0]*par[1]*2+par[0]) * sizeof(jdouble));
	for (int i = 0; i < par[0]*par[1]*2; i++) {
		img_isi[i]=s[i];
	}
	for (int i = par[0]*par[1]*2; i < par[0]*par[1]*2+par[0]; i++) {
			img_isi[i]=eigen[i-par[0]*par[1]*2];
		}
	jdoubleArray real = (*env)->NewDoubleArray(env,(par[0]*par[1]*2)+par[0]);
	(*env)->SetDoubleArrayRegion(env,real, 0,par[0]*par[1]*2+par[0]  , img_isi);
	return real;
}
