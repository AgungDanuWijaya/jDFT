package tools;

/**
 *
 * @author agung
 */
public class randy {

    int m = 714025;
    int ia = 1366;
    int ic = 150889;
    int ntab = 97;
    double rm = 1.0 / m;

    double ir[];
    double iy;
    double idum;
    boolean first;

    public void init() {

        this.ir = new double[this.ntab];
        this.iy = 0;
        this.idum = 0;
        this.first = true;

        if (this.first == true) {
            this.first = false;
            this.idum = (ic - this.idum) % m;
            for (int i = 0; i < ntab; i++) {
                this.idum = (ia * this.idum + ic) % m;
                this.ir[i] = this.idum;
            }
            this.idum = (ia * this.idum + ic) % m;
            this.iy = this.idum;
        }

    }

    public double rand() {
        int j = (int) (1 + (this.ntab * this.iy) / this.m);
        this.iy = this.ir[j - 1];
        double randy = iy * rm;
        this.idum = (this.ia * this.idum + this.ic) % this.m;
        this.ir[j - 1] = this.idum;
        return randy;
    }

}
