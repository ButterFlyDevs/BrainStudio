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

    //Por si queremos obtener depuración de los algoritmos por terminal.
    static boolean depuracion =false;
    
    /**
     * Esta es la función que se copiará en el código de AndroidStudio
     * @param numCeldas El número de celdas a con las que jugar.
     * @param numFilas El número de filas que tiene el grid de la jugada.
     * @param numColumnas El número de columnas que tiene el grid de la jugada.
     * @return 
     */
    static public boolean[] obtenerMatrizJugada(int numCeldas, int numFilas, int numColumnas){

        int tamGrid=numFilas*numColumnas;
        //Creamos el vector que vamos a devolver:
        boolean [] matrizBooleanos = new boolean[tamGrid];

        //Inicializamos el vector:
        for(int i=0; i<tamGrid; i++)
            matrizBooleanos[i]=false;

        //Rellenamos la matriz con el número de celdas positivas que nos indique el parámetro:


            //Random que hace imposible repetir dos veces un número:
                List<Integer> enteros = new ArrayList<>();
                for(int i=0; i<tamGrid; i++)
                    enteros.add(i);
                
                if(depuracion){
                for(int elemento: enteros)
                    System.out.print(elemento);
                System.out.println("");
                }
                    

        ArrayList finales = new ArrayList();

            //Sacamos cuantos numeros nos sean necesarios como celdas tengamos que rellenar.
            int elegido;
            int numero;
            int numFinal=tamGrid;
            Random rnd = new Random();
            for(int i=0; i<numCeldas; i++) {
                elegido = (int)(rnd.nextDouble() * numFinal); //Random de números entre el 0 y el numFinal
                if(depuracion) System.out.println("elegido: "+elegido);
                
                if(depuracion){
                System.out.println("Vector de enteros antes");
                for(int elemento: enteros)
                    System.out.print(elemento);
                System.out.println("");
                
                          
                System.out.println("Vector de enteros después");                                
                for(int elemento: enteros)
                    System.out.print(elemento);
                System.out.println("");
                }
                
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
    
    /**
     * Para mostrar la matriz en el terminal
     * @param matriz Matriz de booleanos a leer
     * @param fil //Número de filas de la misma
     * @param col  //Número de columnas de la misma
     */
    static public void leerMatriz(boolean matriz[], int fil, int col){
        int columnas=1;
        for(int i=0; i<fil*col; i++){
            if(matriz[i]==true)
                System.out.print(1+" ");
            else
                System.out.print(0+" ");
            if(columnas==col){
                System.out.print("\n");
                columnas=1;
            }else
                columnas++;
                
        }
        System.out.println("\n");
                
        
    }
    
    /**
     * Función para comparar la matriz original y la que crea el usuario
     * @param matrizOriginal La matriz de referencia (la correcta)
     * @param matrizJugador  La matriz que crea el jugador al pulsar los botones
     * @param numFilas Número de filas de las matrices 
     * @param numColumnas Número de columnas de las matrices
     * @return true si las matrices son iguales y false si no lo son
     */
    static public boolean compruebaMatrices(boolean matrizOriginal[], boolean matrizJugador[], int numFilas, int numColumnas){
        
        /*
        El único objetivo es compara si dos matrices son iguales. Para eso lo vamos a realizar celda a celda.
        */
        
        boolean resultado=true;
        int tamGrid=numFilas*numColumnas;
      
        /*Forma más pesada: se recore todo el vector
        for(int i=0; i<tamGrid; i++)
                if(matrizOriginal[i]!=matrizJugador[i]) resultado=false;               
        */
        
               
        //De esta forma si el error está al principio no tiene que recorrer toda la matriz
        int pos=0;
        while(resultado==true && pos<tamGrid){
            if(matrizOriginal[pos]!=matrizJugador[pos]) resultado=false;
            pos++;
        }
            
        
        return resultado;
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int numFilas=4;
        int numColumnas=3;
        int numCeldas=6;
        
        //num de celdas a obtener, numFilas, numColumnas (de la matriz)
        boolean matriz[] = obtenerMatrizJugada(numCeldas,numFilas,numColumnas);
       // leerMatriz(matriz, numFilas, numColumnas);
        
        boolean matrizJugadorA[] = obtenerMatrizJugada(numCeldas,numFilas,numColumnas);
        boolean matrizJugadorB[] = obtenerMatrizJugada(numCeldas,numFilas,numColumnas);
        
        leerMatriz(matrizJugadorA, numFilas, numColumnas);
        leerMatriz(matrizJugadorB, numFilas, numColumnas);
        
        if(compruebaMatrices(matrizJugadorA, matrizJugadorB, numFilas, numColumnas))
            System.out.println("Matrices iguales");
        else
            System.out.println("Matrices distintas");
        
    }
    
}
