package lapack;

import tools.array_operation;

public class JNIJava {

    static {
        System.load(new config().url_lib);
    }

    private native double[] sumsquaredc(int param[], double S[], double H[]);

    public gev_object main(double H[][][], double S[][][], int iband) {
        double h[] = new double[H.length * H[0].length * 2];
        double s[] = new double[S.length * S[0].length * 2];

        for (int i = 0; i < H.length; i++) {
            for (int j = 0; j < H[i].length; j++) {
                h[(i * H.length) + j] = H[i][j][0];
                h[(H.length * H[0].length) + ((i * H.length) + j)] = H[i][j][1];
                s[(i * H.length) + j] = S[i][j][0];
                s[(H.length * H[0].length) + ((i * H.length) + j)] = S[i][j][1];
            }
        }
        int r[] = {H.length, H[0].length, iband};

        double res[] = sumsquaredc(r, s, h);
        double data[][] = new double[2][H.length * H[0].length];
        int in = 0;
        for (int i = 0; i < H.length * H[0].length * 2; i = i + 2) {
            data[0][in] = res[i];
            data[1][in] = res[i + 1];
            in += 1;
        }

        double vec[][][] = new double[H.length][H[0].length][2];
        for (int i = 0; i < H.length; i++) {
            for (int j = 0; j < H[i].length; j++) {
                vec[i][j][0] = data[0][(i * H.length) + j];
                vec[i][j][1] = data[1][(i * H.length) + j];
                //System.out.print(vec[i][j][0]+"  "+vec[i][j][1]);
            }
            //System.out.println("");
        }
        System.out.println("vector0");
        for (int j = 0; j < iband; j++) {
            System.out.print(vec[0][j][0] + " , " + vec[0][j][1] + ",");
        }
        System.out.println("");
        System.out.println("vector1");
        for (int j = 0; j < iband; j++) {
            System.out.print(vec[1][j][0] + " , " + vec[1][j][1] + ",");
        }
        double eigen[] = new double[H.length];
        double eigen_[] = new double[H.length];
        in = 0;
        System.out.println("");
        System.out.println("Eigen ");
        for (int i = H.length * H[0].length * 2; i < H.length * H[0].length * 2 + H.length; i++) {
            eigen[in] = res[i];
            eigen_[in] = res[i];
            System.out.print(eigen[in] + " , ");
            in += 1;
        }
        gev_object a = new gev_object();
        a.eigen = eigen;
        a.eigen_ = eigen_;
        a.vec = vec;
        return a;
    }

}
