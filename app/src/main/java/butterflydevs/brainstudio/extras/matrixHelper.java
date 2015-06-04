package butterflydevs.brainstudio.extras;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by juan on 4/06/15.
 */
public class matrixHelper {


    static boolean depuracion=false; //Para on/off de la depuración por consola


    /**
     * Esta es la función que se copiará en el código de AndroidStudio
     * @param numCeldas El número de celdas a con las que jugar.
     * @param numFilas El número de filas que tiene el grid de la jugada.
     * @param numColumnas El número de columnas que tiene el grid de la jugada.
     * @return La matriz (construida de forma aleatoria) que tendrá que adivinar el jugador.
     */
    public static boolean[] obtenerMatrizJugada(int numCeldas, int numFilas, int numColumnas){



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
     * Función para comparar la matriz original y la que crea el usuario
     * @param matrizOriginal La matriz de referencia (la correcta)
     * @param matrizJugador  La matriz que crea el jugador al pulsar los botones
     * @param numFilas Número de filas de las matrices
     * @param numColumnas Número de columnas de las matrices
     * @return true si las matrices son iguales y false si no lo son
     */
    public static boolean compruebaMatrices(boolean matrizOriginal[], boolean matrizJugador[], int numFilas, int numColumnas){

        /*
        El único objetivo es compara si dos matrices son iguales, para eso comprobaremos celda a celda.
        */


        for(int i=0; i<numFilas*numColumnas; i++) {
            if(matrizOriginal[i]==false)
                System.out.print("0");
            else
                System.out.print("1");
        }
        System.out.println("");
        for(int i=0; i<numFilas*numColumnas; i++) {
            if(matrizJugador[i]==false)
                System.out.print("0");
            else
                System.out.print("1");
        }


        boolean resultado=true;
        int tamGrid=numFilas*numColumnas;

        //De esta forma si el error está al principio no tiene que recorrer toda la matriz
        int pos=0;
        while(resultado==true && pos<tamGrid){
            if(matrizOriginal[pos]!=matrizJugador[pos]) resultado=false;
            pos++;
        }

        return resultado;
    }


}
