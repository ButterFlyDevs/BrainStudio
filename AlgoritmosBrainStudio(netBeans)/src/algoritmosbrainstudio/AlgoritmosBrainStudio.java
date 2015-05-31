/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmosbrainstudio;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author juan
 */
public class AlgoritmosBrainStudio {

    
    static public boolean[] obtenerMatrizJugada(int numCeldas, int tamGrid){

        //Creamos el vector que vamos a devolver:
        boolean [] matrizBooleanos = new boolean[12];

        //Inicializamos el vector:
        for(int i=0; i<tamGrid; i++)
            matrizBooleanos[i]=false;

        //Rellenamos la matriz con el número de celdas positivas que nos indique el parámetro:


            //Random que hace imposible repetir dos veces un número:
                List<Integer> enteros = new ArrayList<>();
                for(int i=0; i<tamGrid; i++)
                    enteros.add(i);
                
                for(int elemento: enteros)
                    System.out.print(elemento);
                System.out.println("");
                    

        ArrayList finales = new ArrayList();

            //Sacamos cuantos numeros nos sean necesarios como celdas tengamos que rellenar.
            int elegido;
            int numero;
            int numFinal=tamGrid;
            Random rnd = new Random();
            for(int i=0; i<numCeldas; i++) {
                elegido = (int)(rnd.nextDouble() * numFinal); //Random de números entre el 0 y el numFinal
                System.out.println("elegido: "+elegido);
                
                System.out.println("Vector de enteros antes");
                for(int elemento: enteros)
                    System.out.print(elemento);
                System.out.println("");
                
                          
                System.out.println("Vector de enteros después");                                
                for(int elemento: enteros)
                    System.out.print(elemento);
                System.out.println("");
                
                
                finales.add(enteros.get(elegido));
                enteros.remove(elegido);
                numFinal--;
            }

            for(int i=0; i<finales.size(); i++){
                int elemento = (int)finales.get(i);
                matrizBooleanos[elemento]=true;
            }




        return matrizBooleanos;
    }
    
    static public void leerMatriz(boolean matriz[], int fil, int col){
        int columnas=1;
        for(int i=0; i<fil*col; i++){
            if(matriz[i]==true)
                System.out.print(1);
            else
                System.out.print(0);
            if(columnas==col){
                System.out.print("\n");
                columnas=1;
            }else
                columnas++;
                
        }
        System.out.println("\n");
                
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        boolean matriz[] = obtenerMatrizJugada(2,12);
        leerMatriz(matriz, 3, 4);
        
        
    }
    
}
