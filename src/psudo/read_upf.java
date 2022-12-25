package psudo;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.HashMap;

public class read_upf {

    public void main(param_upf param) {

        try {
            File file = new File(param.url_upf);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("PP_RHOATOM");
            Node node = nodeList.item(0);
            param.PP_RHOATOM = main(node);
            nodeList = doc.getElementsByTagName("PP_RAB");
            node = nodeList.item(0);
            param.PP_RAB = main(node);
            nodeList = doc.getElementsByTagName("PP_R");
            node = nodeList.item(0);
            param.PP_R = main(node);

            double[] PP_R = new double[param.PP_R.size()];

            for (int i = 0; i < PP_R.length; i++) {
                PP_R[i] = param.PP_R.get(i);
            }
            param.PP_R_ = PP_R;
            double[] PP_RAB = new double[param.PP_RAB.size()];

            for (int i = 0; i < PP_RAB.length; i++) {
                PP_RAB[i] = param.PP_RAB.get(i);
            }
            param.PP_RAB_ = PP_RAB;
            
            nodeList = doc.getElementsByTagName("PP_DIJ");
            node = nodeList.item(0);
            param.PP_DIJ = main(node);

            nodeList = doc.getElementsByTagName("PP_Q");
            node = nodeList.item(0);
            param.PP_Q = main(node);

            nodeList = doc.getElementsByTagName("PP_LOCAL");
            node = nodeList.item(0);
            param.PP_LOCAL = main(node);

            nodeList = doc.getElementsByTagName("PP_HEADER");
            node = nodeList.item(0);

            String nw = main(node, "number_of_wfc");
            int nwfc = Integer.parseInt(nw);
            nw = main(node, "number_of_proj");
            int npfc = Integer.parseInt(nw);
            param.number_projector = npfc;
            nw = main(node, "z_valence");
            double zp = Double.parseDouble(nw);
            nw = main(node, "mesh_size");
            double mesh_size = Double.parseDouble(nw);
            param.mesh_size = mesh_size;
            HashMap<Integer, HashMap<Integer, Double>> PP_CHI = new HashMap<>();
            HashMap<Integer, HashMap<String, Double>> PP_CHI_Param = new HashMap<>();
            for (int i = 0; i < nwfc; i++) {
                nodeList = doc.getElementsByTagName("PP_CHI." + (i + 1));
                node = nodeList.item(0);
                PP_CHI.put(i, main(node));
                HashMap<String, Double> param_chi = new HashMap<>();
                String pr[] = {"occupation", "l"};
                for (int j = 0; j < pr.length; j++) {
                    String arr = main(node, pr[j]);
                    double arr_i = Double.parseDouble(arr);
                    param_chi.put(pr[j], arr_i);
                }
                PP_CHI_Param.put(i, param_chi);
            }

            HashMap<Integer, HashMap<Integer, Double>> PP_BETA = new HashMap<>();
            HashMap<Integer, HashMap<String, Double>> PP_BETA_Param = new HashMap<>();
            for (int i = 0; i < npfc; i++) {
                nodeList = doc.getElementsByTagName("PP_BETA." + (i + 1));
                node = nodeList.item(0);
                PP_BETA.put(i, main(node));
                HashMap<String, Double> param_beta = new HashMap<>();
                String pr[] = {"angular_momentum", "cutoff_radius_index"};
                for (int j = 0; j < pr.length; j++) {
                    String arr = main(node, pr[j]);
                    double arr_i = Double.parseDouble(arr);
                    param_beta.put(pr[j], arr_i);
                    if (j == 1) {
                        if (param.kkbeta < arr_i) {
                            param.kkbeta = (int) arr_i;
                        }
                    }
                    if (j == 0) {
                        if (param.lmax < arr_i) {
                            param.lmax = (int) arr_i;
                        }
                    }
                }
                PP_BETA_Param.put(i, param_beta);

            }

            param.PP_BETA = PP_BETA;

            param.PP_Q_S = new double[param.PP_BETA.size()][param.PP_BETA.size()];

            int in = 0;
            int tan = 0;
            for (int i = 0; i < param.PP_Q.size(); i++) {
                //System.out.println("ppq"  +param.PP_Q.get(i));
                if (tan < param.PP_BETA.size()) {
                    param.PP_Q_S[in][tan] = param.PP_Q.get(i);
                    //System.out.println("ppq1" + in + " " + tan + param.PP_Q.get(i));
                    tan++;
                } else {
                    tan = 0;
                    in++;
                    param.PP_Q_S[in][tan] = param.PP_Q.get(i);
                    //System.out.println("ppq2" + in + " " + tan + param.PP_Q.get(i));
                    tan++;
                }
            }
            nodeList = doc.getElementsByTagName("PP_AUGMENTATION");
            node = nodeList.item(0);
            if (param.usp == 1) {

                nw = main(node, "nqlc");
                int nqlc = Integer.parseInt(nw);
                param.nqlc = nqlc;

                Element docEle = doc.getDocumentElement();
                NodeList nl = docEle.getChildNodes();

                Element dat_PP_NONLOCAL = read_node(nl, "PP_NONLOCAL");
                nl = dat_PP_NONLOCAL.getChildNodes();

                Element dat_PP_AUGMENTATION = read_node(nl, "PP_AUGMENTATION");
                nl = dat_PP_AUGMENTATION.getChildNodes();

                dat_PP_AUGMENTATION = read_node_value(nl, "PP_QIJL", param);
            }

            param.PP_BETA_Param = PP_BETA_Param;
            param.n_wcf = nwfc;
            param.PP_CHI = PP_CHI;
            param.PP_CHI_Param = PP_CHI_Param;
            param.zp = zp;
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public Element read_node(NodeList nl, String text) {
        Element dat = null;
        if (nl != null) {
            int length = nl.getLength();
            for (int i = 0; i < length; i++) {
                if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) nl.item(i);
                    if (el.getNodeName().contains(text)) {
                        dat = el;
                    }
                    //       System.out.println(text + "el()" + el.getNodeName());
                }
            }
        }
        return dat;
    }

    public Element read_node_value(NodeList nl, String text, param_upf param) {
        NodeList nodeList;
        Node node;
        HashMap<Integer, HashMap<Integer, Double>> PP_QIJL_1 = new HashMap<>();
        param.PP_QIJL = new HashMap<>();
        Element dat = (Element) nl;
        int am = 9999;
        int ci = 9999;
        if (nl != null) {
            int length = nl.getLength();
            for (int i = 0; i < length; i++) {
                if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) nl.item(i);
                    if (el.getNodeName().contains(text)) {
                        nodeList = dat.getElementsByTagName(el.getNodeName());
                        node = nodeList.item(0);
                        HashMap<Integer, Double> mainn = main(node);
                        // System.out.println(mainn.size());

                        HashMap<String, Double> param_beta = new HashMap<>();
                        String pr[] = {"angular_momentum", "composite_index"};
                        int in[] = {0, 0};
                        for (int j = 0; j < pr.length; j++) {
                            String arr = main(node, pr[j]);
                            double arr_i = Double.parseDouble(arr);
                            in[j] = (int) arr_i;
                        }
                        if (ci != in[1]) {

                            if (ci != 9999) {
                                param.PP_QIJL.put(ci, PP_QIJL_1);
                            }
                            PP_QIJL_1 = new HashMap<>();
                            PP_QIJL_1.put(in[0], mainn);
                            ci = in[1];
                        } else {
                            PP_QIJL_1.put(in[0], mainn);

                        }

                    }

                }
            }
        }
               param.PP_QIJL.put(ci, PP_QIJL_1);
/*
        double PP_QIJL[][][] = new double[param.PP_QIJL.size()][param.PP_QIJL.get(0).size()][param.PP_QIJL.get(0).get(0).size()];
        for (int i = 0; i < param.PP_QIJL.size(); i++) {
            for (int j = 0; j < param.PP_QIJL.get(0).size(); j++) {
                for (int k = 0; k < param.PP_QIJL.get(0).get(0).size(); k++) {
                    PP_QIJL[i][j][k] = param.PP_QIJL.get(i).get(j).get(k);
                }
            }
        }
param.PP_QIJL_=PP_QIJL;
         */
        return dat;
    }

    public HashMap<Integer, Double> main(Node node) {
        HashMap<Integer, Double> temp = new HashMap<>();
        try {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                String data = eElement.getTextContent();
                String datas[] = data.replace("\n", " ").split(" ");
                int y = 0;
                for (String data1 : datas) {
                    try {
                        temp.put(y, Double.parseDouble(data1));
                        y += 1;
                    } catch (Exception e) {
                    }

                }
            }
        } catch (Exception e) {
        }
        return temp;
    }

    public String main(Node node, String atr) {

        HashMap<Integer, Double> temp = new HashMap<>();
        String data = "0";
        try {

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                data = eElement.getAttribute(atr);
            }
        } catch (Exception e) {
        }
        return data;
    }

}
