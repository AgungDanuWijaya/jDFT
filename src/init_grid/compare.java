/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package init_grid;

/**
 *
 * @author agung
 */
public class compare {

    /**
     * @param args the command line arguments
     */
    public int main(double a[][],double b[][]) {

         int sama=1;
         for (int i = 0; i < a.length; i++) {
             for (int j = 0; j < a[i].length; j++) {
                 if(a[i][j]!=b[i][j]){
                 sama=0;
                 }
             }
        }
         return sama;
    }
    
}
