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
     * Función para obtener una matriz que tenga parejas dentro de ella.
     * Tendrá tanatas parejas (de números) como la mitad del número de celdas.
     * @param numFilas Numero de filas de la matriz que queremos generar
     * @param numColumnas Numero de columnas de l matriz que queremos generar
     * @return Un vector que simula la matriz.
     */
    static public int[] obtenerMatrizParejas(int numFilas, int numColumnas){
        
        int tamMatriz=numFilas*numColumnas;
        int numParejas=tamMatriz/2;
        
        int [] matrizResultado = new int[tamMatriz];       
        
        
        
        //Montamos esto con dos arrayList ya que es mas facil para no entrar en bucles del random
        
                //Creamos una lista con los número que tendremos que hacer las parejas
                List<Integer> numeros = new ArrayList<>();
                System.out.println("## NUMEROS ##");
                for(int i=0; i<numParejas; i++){
                    numeros.add(i);
                    System.out.print(numeros.get(i)+" ");
                }
                System.out.println("");


                //Creamos una lista con las posiciones que se puede tener dentro de la matriz:        
                List<Integer> posiciones = new ArrayList<>();
                System.out.println("## POSICIONES ##");
                for(int i=0; i<tamMatriz; i++){
                    posiciones.add(i);
                    System.out.print(posiciones.get(i)+" ");
                }
                System.out.println("");
        
        
        //  ### ALGORITMO ### 

        Random rnd = new Random(); 
        int elegido, posA, posB;
                
        for(int i=0; i<numParejas; i++){
            
            //1º. Sacamos un numero al azar del array de numeros. El numero dejara de estar en el array. 
      
                //Lo sacamos mediante un random con los indices del vector.
                elegido = numeros.remove((int)(rnd.nextDouble() * numeros.size()));
                //System.out.print(elegido+" ");
      
            //2º Sacamos dos numeros de array de posiciones, que sera donde ira en la matriz resultado el numero elegido.
                posA = posiciones.remove((int)(rnd.nextDouble()*posiciones.size()));
                posB = posiciones.remove((int)(rnd.nextDouble()*posiciones.size()));
                //System.out.print("A:"+posA+"  B:"+posB);

                
            //3º Introducimos esos valores en la matriz resultado
                matrizResultado[posA]=elegido;
                matrizResultado[posB]=elegido;
                
                
                //System.out.println("");

            
        }                                                
        return matrizResultado;
    
    }
    
    static public void leerMatriz(int []valores, int filas, int columnas){
        
        System.out.println("## Matriz de parejas ##");
        
        int col=1;
        
        for(int i=0; i<filas*columnas; i++){
                System.out.print(valores[i]+" ");
                if(col==columnas){
                        System.out.print("\n");
                        col=1;
                    }else
                        col++;
        }
                    
    }
    
    static public int[] generaColores(int numColoresBase, int numColoresNecesarios){
        
        int [] coloresDevueltos = new int[numColoresNecesarios];       
        
        //Creamos un array con los numeros.
        List<Integer> numeros = new ArrayList<>();
                System.out.println("## "+numColoresBase+" colores base ##");
                for(int i=0; i<numColoresBase; i++){
                    numeros.add(i);
                    System.out.print(numeros.get(i)+" ");
                }
                System.out.println("");
        
        //Rellenamos el vector a devolver con números extraidos de este:
        
         //Inicializamos la semilla
         Random rnd = new Random(); 
         
         for(int i=0; i<numColoresNecesarios; i++){         
            coloresDevueltos[i]=numeros.remove((int)(rnd.nextDouble() * numeros.size()));        
         }
        return coloresDevueltos;
        
    }
    
    static public void leeColores(int[] colores){
        System.out.println("## "+colores.length+" colores elegidos ##");
        for(int i=0; i<colores.length; i++)
            System.out.print(colores[i]+" ");
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int colores[] = generaColores(20, 6);
        leeColores(colores);
        
     //   int matrizResultado[] = obtenerMatrizParejas(4,3);
        
      //  leerMatriz(matrizResultado, 4, 3 );
        
        /* PRUEBAS A
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
        */
        
        
        
    }
    
}
