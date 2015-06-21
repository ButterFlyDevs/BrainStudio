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
        El único objetivo es comparar si dos matrices son iguales, para eso comprobaremos celda a celda.
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


    /**
     * ####### FUNCIÓN RE-ADAPTADA PARA EL JUEGO2
     *
     * Esta es la función que se copiará en el código de AndroidStudio
     * @param numCeldas El número de celdas a con las que jugar.
     * @param numFilas El número de filas que tiene el grid de la jugada.
     * @param numColumnas El número de columnas que tiene el grid de la jugada.
     * @return La matriz (construida de forma aleatoria) que tendrá que adivinar el jugador.
     */
    public static int[] obtenerMatrizJugada_juego2(int numCeldas, int numFilas, int numColumnas) {


        int tamGrid = numFilas * numColumnas;
        //Creamos el vector que vamos a devolver:
        int[] matrizEnteros = new int[tamGrid];

        //Inicializamos el vector:
        for (int i = 0; i < tamGrid; i++)
            matrizEnteros[i] = 0;

        //Array de enteros para evitar que se repitan números aleatorios
        List<Integer> enteros = new ArrayList<>();
        for (int i = 0; i < tamGrid; i++)
            enteros.add(i);

        ArrayList<Integer> finales_magenta = new ArrayList<>();
        ArrayList<Integer> finales_verde = new ArrayList<>();
        ArrayList<Integer> finales_rojo = new ArrayList<>();
        //Ahora rellenamos todas las celdas con figuras.
        Random aleatorio = new Random();
        int seleccion, indice_seleccionado, celdas_rellenadas=0;

        for(int i=0; i<numCeldas && celdas_rellenadas<tamGrid; i++){
            //****************** Seleccionar cuadrado
            indice_seleccionado = aleatorio.nextInt(enteros.size());  //Se escoge un número entre 0 y el tamaño del array enteros
            seleccion = enteros.get(indice_seleccionado);

            //Quitamos el elemento del ArrayList enteros para que no se vuelva a repetir el numero
            enteros.remove(indice_seleccionado);
            //Añadimos el numero seleccionado al array de magentas
            finales_magenta.add(seleccion);
            celdas_rellenadas++;

            //****************** Seleccionar circulo
            indice_seleccionado = aleatorio.nextInt(enteros.size());  //Se escoge un número entre 0 y el tamaño del array enteros
            seleccion = enteros.get(indice_seleccionado);

            //Quitamos el elemento del ArrayList enteros para que no se vuelva a repetir el numero
            enteros.remove(indice_seleccionado);
            //Añadimos el numero seleccionado al array de verdes
            finales_verde.add(seleccion);
            celdas_rellenadas++;

            //**************** Seleccionar triangulo
            indice_seleccionado = aleatorio.nextInt(enteros.size());  //Se escoge un número entre 0 y el tamaño del array enteros
            seleccion = enteros.get(indice_seleccionado);

            //Quitamos el elemento del ArrayList enteros para que no se vuelva a repetir el numero
            enteros.remove(indice_seleccionado);
            //Añadimos el numero seleccionado al array de rojo
            finales_rojo.add(seleccion);
            celdas_rellenadas++;
        }

        //Tenemos todos los aleatorios seleccionados. Ahora hay que rellenar la matriz
        /*
            Recordemos:
            Boton vacío =>0
            Cuadrado => 1
            Triangulo => 2
            Circulo => 3
         */
        for(int i=0; i<finales_magenta.size();i++){
            int indice = finales_magenta.get(i);
            matrizEnteros[indice]=1;
        }

        for(int i=0; i<finales_rojo.size();i++){
            int indice = finales_rojo.get(i);
            matrizEnteros[indice]=3;
        }

        for(int i=0; i<finales_verde.size();i++){
            int indice = finales_verde.get(i);
            matrizEnteros[indice]=2;
        }

        return matrizEnteros;
    }



    public static boolean compruebaMatrices_juego2(int matrizOriginal[], int matrizJugador[], int numFilas, int numColumnas, int color){

        /*
        El objetivo es comparar si las posiciones dadas por el jugador son las correctas para la figura seleccionada
        */


        boolean resultado=true;
        int tamGrid=numFilas*numColumnas;

        //Primero, hay que encontrar en qué posicion del vector están las figuras solución, y añadirlas a un ArayList
        ArrayList<Integer> indices_solucion = new ArrayList<>();
        for(int i=0; i< tamGrid;i++){
            if(matrizOriginal[i]==color)
                indices_solucion.add(i);
        }

        //Tenemos los indices solución. Debemos encontrar ahora los indices que ha contestado el usuario
        ArrayList<Integer> indices_contestados = new ArrayList<>();
        for (int i =0; i< tamGrid;i++){
            if(matrizJugador[i]==color)
                indices_contestados.add(i);
        }

        //Con los dos arrays de indices, comprobamos si son iguales (el jugador habría superado el nivel) o si son distintos

        if(indices_contestados.size() != indices_solucion.size())
            resultado=false;
        else{
            for(int i=0; i<indices_contestados.size() && resultado;i++){
                if(!indices_solucion.contains(indices_contestados.get(i)))
                    resultado=false;
            }
        }

        return resultado;
    }
}
