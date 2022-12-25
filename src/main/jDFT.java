/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author agung
 */
public class jDFT {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException, ClassNotFoundException {
        String fileName = args[0];
        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = "";
        String data = "";
        while ((line = br.readLine()) != null) {
            data += line;
        }

        Gson gson = new Gson();
        init_data init = gson.fromJson(data, init_data.class);
                if (init.status.equals("scf")) {
            utama_gen ug = new utama_gen();
            ug.main(init);
        } else if (init.status.equals("bands")) {
            utama_gen_band_ ug = new utama_gen_band_();
            ug.main(init);
        }

    }

}
