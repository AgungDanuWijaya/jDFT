package main;

import java.util.HashMap;
import lapack.gev_object;
import psudo.param_upf;
import init_grid.ggen_object;

/**
 *
 * @author agung
 */
public class parameter {
    public int nsym;
    public int tannn=0;
  public  int nbase=0;
    public int notcnv=0;
public double ewald=0;
public double eband=0;
public double ehart=0;
public double etxc=0;
public double deband=0;
public double demet=0;
    public int set_cal = 0;
    public int usp = 0;
    public double ap[][][];
    public double spsi[][][];
    public double spsi1[][][];
    public double spsi_c[][][];
    public double[][] ylm;
    public double mly[][];
    public double lpx[][];
    public double lpl[][][];
    public int lmaxkb = 0;
    public int random = 1;
    public int lmax = 1;
    public double wk[] = null;
    public double wg[];
    public int smar = 0;
    public int max_base = 0;
    public int ipos = 0;
    public int base = 1;
    public double dr = 0;
    public int scf = 0;
    public double v_of_0;
    public double campur;// iweight
    public double con = 0.01;
    public double eps8 = 1.0E-8;
    public double eps6 = 1.0E-6;
    public double eps1 = 1.0E-6;
    public int iband = 0;
    public double cell_factor = 1.0;
    public double dq = 0.01;
    public double celldm[];
    public double ecutwfc;
    public double ecutrho;
    public int ibrav;
    public double bg[][]; //recip
    public double at[][]; //latgen
    public double batas_kecil = Math.pow(10, -8);
    public double omega;
    public double gcutw;
    public double gcutm;
    public double dual;
    public double tpiba;
    public int gstart = 0;
    public ggen_object g;
    public double nr1;
    public double nr2;
    public double nr3;
    public double nr1s;
    public double nr2s;
    public double nr3s;
    public HashMap<Integer, Integer> nl;
    public HashMap<Integer, Integer> nl_s;
    public double mill[][];
    public HashMap<Integer, Double> gl;
    public HashMap<Integer, Integer> index_gg;//igtongl
    public double tot_muatan = 0.0;
    public String atom[];
    public String atom_p[];
    public int nat;
    public int banyak_atom;
    public HashMap<String, param_upf> upf_data = new HashMap<>();
    public HashMap<String, Integer> tipe = new HashMap<>();
    public HashMap<Integer, HashMap<Integer, double[]>> eigts1_;
    public HashMap<Integer, HashMap<Integer, double[]>> eigts2_;
    public HashMap<Integer, HashMap<Integer, double[]>> eigts3_;
    public HashMap<Integer, HashMap<Integer, double[]>> strf = new HashMap<>();
    public double pos[][];
    public double rhoscale = 1.0;
    public double rhocg[][];
    public double rhocg_3d[][];
    public double rhocr[][];
    public double rhocr_new[][];
    public double rhocg_new[][];
    public double k_point[][];
    public double awal_panjang;
    public HashMap<Integer, HashMap<Integer, Double>> igk = new HashMap<>();

    public HashMap<Integer, Integer> ngk = new HashMap<>();
    public HashMap<Integer, double[][][]> bec = new HashMap<>();
    public HashMap<Integer, HashMap<Integer, HashMap<Integer, double[]>>> atomicwfc = new HashMap<>();
    public HashMap<Integer, HashMap<Integer, HashMap<Integer, double[]>>> atomicwfc_1 = new HashMap<>();
    public double atomicwfc_l[][][];
    public HashMap<Integer, HashMap<Integer, HashMap<Integer, double[]>>> vkb = new HashMap<>();
    public double mix = 0.7;
    public double e2 = 2.0;
    public int top_cut = 0;
    public int non_conv = 0;
    public int non_conv_0 = 0;
    public HashMap<Integer, double[]> g2_kin = new HashMap<>();
    public HashMap<Integer, double[]> h_diag = new HashMap<>();
    public HashMap<Integer, double[]> s_diag = new HashMap<>();
    public double v_dft[];
    public double v_h[];
    public double vrs[];
    public double v_loc[];
    public HashMap<Integer, double[][][]> non_local = new HashMap<>();
    public HashMap<Integer, double[][][]> hal = new HashMap<>();
    public HashMap<Integer, double[][][]> sc = new HashMap<>();
    public HashMap<Integer, double[][][]> hal_1 = new HashMap<>();
    public HashMap<Integer, double[][][]> sc_1 = new HashMap<>();
    public HashMap<Integer, double[][][]> h_psi = new HashMap<>();
    public HashMap<Integer, double[][][]> h_psi_1 = new HashMap<>();
    public HashMap<Integer, double[][][]> h_psi_2 = new HashMap<>();

    public double h_psi_l[][][];
     
    public gev_object solusi;
    public double vec[][][];
    public gev_object solusi1;
    public int iter = 0;
    public int iter_big = 0;
    public int pan = 0;
    public int ngm_s;
       public int ngm;
    public double s_[][][];
    public HashMap<Integer, Integer> invs;
    public HashMap<Integer, HashMap<Integer, Integer>> vect = new HashMap<>();
    public HashMap<Integer, double[][]> rho_back_df_1 = new HashMap<>();
    public HashMap<Integer, double[][]> rho_back_dv_1 = new HashMap<>();
    public HashMap<Integer, double[][]> rho_back_df_2 = new HashMap<>();
    public HashMap<Integer, double[][]> rho_back_dv_2 = new HashMap<>();
    public double ft[][];

}
