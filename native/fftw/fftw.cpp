#include <iostream>
#include "fftw.hpp"
#include <fftw3.h>
#define REAL 0
#define IMAG 1
using namespace std;

JNIEXPORT jobjectArray JNICALL Java_fftw_fftw_fftw(JNIEnv *env, jclass,
		jintArray n, jobjectArray data) {
	jsize x = env->GetArrayLength(data);
	fftw_complex *x_in;
	x_in = (fftw_complex*) fftw_malloc(x * sizeof(fftw_complex));
	fftw_complex *y_out;
	y_out = (fftw_complex*) fftw_malloc(x * sizeof(fftw_complex));
	for (int i = 0; i < x; i++) {
		jobject dat = env->GetObjectArrayElement(data, i);
		jdoubleArray dat_1 = (jdoubleArray) dat;
		jdouble *intArrayInC = env->GetDoubleArrayElements(dat_1, NULL);
		x_in[i][REAL] = intArrayInC[0];
		x_in[i][IMAG] = intArrayInC[1];
		delete intArrayInC;
	}

	jint *intArrayInC = env->GetIntArrayElements(n, NULL);
	fftw_plan plan;
	if (intArrayInC[3] == 1) {

		plan = fftw_plan_dft_3d(intArrayInC[0], intArrayInC[1], intArrayInC[2],
				x_in, y_out, FFTW_FORWARD, FFTW_ESTIMATE);
	}

	else if (intArrayInC[3] == -1) {
		//std::cout << "Hello World!=" << intArrayInC[3] << "\n";
		plan = fftw_plan_dft_3d(intArrayInC[0], intArrayInC[1], intArrayInC[2],
				x_in, y_out, FFTW_BACKWARD, FFTW_ESTIMATE);
	}
	fftw_execute(plan);
	fftw_destroy_plan(plan);
	fftw_cleanup();
	jdoubleArray real = env->NewDoubleArray(x);
	jdoubleArray imag = env->NewDoubleArray(x);
	jdouble *real_isi = new jdouble[x];
	jdouble *img_isi = new jdouble[x];
	for (int i = 0; i < x; i++) {
		real_isi[i] = y_out[i][REAL];
		img_isi[i] = y_out[i][IMAG];

	}
	env->SetDoubleArrayRegion(real, 0, x, real_isi);
	env->SetDoubleArrayRegion(imag, 0, x, img_isi);
	jclass intClass = env->FindClass("[D");
	jobjectArray terrain = env->NewObjectArray(2, intClass, NULL);
	env->SetObjectArrayElement(terrain, REAL, real);
	env->SetObjectArrayElement(terrain, IMAG, imag);
delete x_in;
delete y_out;
	delete intArrayInC;
	delete real_isi;
	delete img_isi;
	return terrain;
}
