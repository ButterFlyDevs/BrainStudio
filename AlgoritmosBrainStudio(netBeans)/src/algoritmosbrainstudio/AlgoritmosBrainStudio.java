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
     * Para generar de forma simple un número aleatorio entre dos valores inclusives.
     * @param min Cota inferior
     * @param max Cota superior
     * @return  Un valor aleatorio entre las cotas inclusives.
     */
     static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();


        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
    
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
    
    
    // ## JUEGO 5 ## //
    
    /**
     * Generadora de secuencia de números aleatorios en posiciones aleatorias.
     * Esta función genera una matriz (doble vector) con posiciones aleatorias y valores aleatorios
     * para el 5º Juego.
     * @param numElems Numero de elementos queremos generar (pareja posicion y valor)
     * @param tamMatriz Tamaño de la matriz (para calcular posiciones de esta)
     * @param rangoMin //Rango minimo de valores a generar.
     * @param rangoMax //Rango máximo de valores a generar.
     * @return El doble vector (matriz) con la información del valor en la posición que corresponde.
     */
    static public int[][] generaSecuencia(int numElems, int tamMatriz, int rangoMin, int rangoMax){
        
        final int numFilas=2;
        
        /*
        Creamos una matriz siempre de dos filas con tantas columnas como numElementos queramos rellenar.
        Ojo!: No podremos construir un resultado con más elementos que el propio tamaño de la matriz, controlamos
        esto y lanzamos una excepción abortando el proceso antes de nada. Por ahora no lo hacemos.
        
        if(numElems>tamMatriz)
            deten programa y lanza excepción
        */
        int valores[][]= new int[numFilas][numElems];
        
        
            // ## Algoritmo ## //
        
        
           //Creamos y rellenamos un array con las posicones de la matriz:
            List<Integer> posiciones = new ArrayList();                    
            for(int i=0; i<tamMatriz; i++)
                posiciones.add(i);
            
           //Creamos y rellenamos un array con los posible valores:
            List<Integer> numeros = new ArrayList();
            for(int i=rangoMin; i<=rangoMax; i++) //Queremos que ambos valores estén dentro
                numeros.add(i);
            
        
        
        
            //1º Un bucle de tantos pasos como elementos queramos generar:
            
                for(int i=0; i<numElems; i++){

                    //En la primera fila introducimos la POSICION en la matriz
                        //A la vez que introducimos sacamos ese valor de los posibles.
                        valores[0][i]=posiciones.remove(randInt(0,posiciones.size()-1)); 

                    //En la segunda fila introducimos el VALOR en esa posición de la matriz.
                        valores[1][i]=numeros.remove(randInt(0,numeros.size()-1));

                }
            
            //2º La ordenación del vector por los números (esto nos vendrá bien para aliviar de procesamiento al juego)
        
            
             leeSecuencia(valores, numElems);
             burbuja(valores, numElems);
             leeSecuencia(valores, 5);
            
            
            
        return valores;
        
    }
    /**
     * Adaptación del algoritmo de ordenación burbuja para matriz de dos filas donde se ordena por la fila 1.
     * @param matriz Matriz a ordenar por la segunda fila
     * @param size Tamaño de la matriz.
     */
    public static void burbuja(int [][] matriz, int size){
                 
        
        int i, j, aux, aux2;
         
         for(i=0;i<size-1;i++)            
              for(j=0;j<size-i-1;j++)                  
                   if(matriz[1][j+1]<matriz[1][j]){
                      
                      aux=matriz[1][j+1];                      
                      matriz[1][j+1]=matriz[1][j];                      
                      matriz[1][j]=aux; 
                       
                      aux2=matriz[0][j+1];
                      matriz[0][j+1]=matriz[0][j];          
                      matriz[0][j]=aux2;
                   }
         
    }
    static public void leeSecuencia(int[][] paresPosicionValor, int numElementos){
        
        System.out.println(" ### Secuencia  ### ");
        for(int i=0; i<numElementos; i++){                        
            System.out.print("["+paresPosicionValor[0][i]+"]");                       
        }
        System.out.println("");
        for(int i=0; i<numElementos; i++){
            System.out.print("["+paresPosicionValor[1][i]+"]");
        }
        System.out.println("");
        
    }
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
       // int colores[] = generaColores(20, 6);
       // leeColores(colores);
        
        int prueba[][] = generaSecuencia(5,9,1,9);
        
        
        /*
        int elements[] = {50,26,7,9,15,27};
        burbuja(elements);
        for(int a: elements)
            System.out.print(a+" ");
        */
        
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
