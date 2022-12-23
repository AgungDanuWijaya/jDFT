package psudo;

import java.util.HashMap;

/**
 *
 * @author agung
 */
public class param_upf {
    public double PP_QIJL_[][][];
    public double[] PP_R_;
    public double[] PP_RAB_;
public double qq_at_[][][];
    public int usp;
    public int nqlc;
    public int kkbeta;
    public int lmax;
    public double msh;
    public  HashMap<Integer, Double> nhtolm;
    public HashMap<Integer, HashMap<Integer, Double>> PP_CHI;//gelombang
    public HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>> PP_QIJL;
    public HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>> QRAD;
    public HashMap<Integer, HashMap<String, Double>> PP_CHI_Param;
    public HashMap<Integer, HashMap<Integer, Double>> PP_BETA;
    public HashMap<Integer, HashMap<String, Double>> PP_BETA_Param;
    public HashMap<Integer, Double> PP_R;
    public HashMap<Integer, Double> PP_RAB;
    public HashMap<Integer, Double> PP_LOCAL;
    public HashMap<Integer, Double> PP_DIJ;
    public HashMap<Integer, Double> PP_Q;
    public double PP_Q_S[][];
    public HashMap<Integer, Double> PP_RHOATOM;//density atom
    public double n_wcf;
    public String url_upf;
    public double zp;
    public String name;

    public double rhocg[][];
    public double rhocg_3d[][];

    public double tab_at[][];
    public double tab[][];
    public HashMap<Integer, Integer> indv;
    public double deeq[][][][];

    public int number_projector;
    public double aux_rab[];
    public double mesh_size;

    public double v_loc[][];

}
