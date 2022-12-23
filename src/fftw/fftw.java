package fftw;
public class fftw {
    static {
        System.load(new config().url_lib);
    }
    public static native double[][] fftw(int nums[], double[][] data);   
}
