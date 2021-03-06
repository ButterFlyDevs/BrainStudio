/*
        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with this program. If not, see <http://www.gnu.org/licenses/>.

        Copyright 2015 Jose A. Gonzalez Cervera
        Copyright 2015 Juan A. Fernández Sánchez
*/
package butterflydevs.brainstudio.extras;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by juan on 4/06/15.
 */
public class matrixHelper {


    static boolean depuracion = false; //Para on/off de la depuración por consola


    // ### JUEGO 1 ### //

    /**
     * Esta es la función que se copiará en el código de AndroidStudio
     *
     * @param numCeldas   El número de celdas a con las que jugar.
     * @param numFilas    El número de filas que tiene el grid de la jugada.
     * @param numColumnas El número de columnas que tiene el grid de la jugada.
     * @return La matriz (construida de forma aleatoria) que tendrá que adivinar el jugador.
     */
    public static boolean[] obtenerMatrizJugada(int numCeldas, int numFilas, int numColumnas) {


        int tamGrid = numFilas * numColumnas;
        //Creamos el vector que vamos a devolver:
        boolean[] matrizBooleanos = new boolean[tamGrid];

        //Inicializamos el vector:
        for (int i = 0; i < tamGrid; i++)
            matrizBooleanos[i] = false;

        //Rellenamos la matriz con el número de celdas positivas que nos indique el parámetro:

        //Random que hace imposible repetir dos veces un número:
        List<Integer> enteros = new ArrayList<>();
        for (int i = 0; i < tamGrid; i++)
            enteros.add(i);

        if (depuracion) {
            for (int elemento : enteros)
                System.out.print(elemento);
            System.out.println("");
        }


        ArrayList finales = new ArrayList();

        //Sacamos cuantos numeros nos sean necesarios como celdas tengamos que rellenar.
        int elegido;
        int numFinal = tamGrid;
        Random rnd = new Random();
        for (int i = 0; i < numCeldas; i++) {
            elegido = (int) (rnd.nextDouble() * numFinal); //Random de números entre el 0 y el numFinal
            if (depuracion) System.out.println("elegido: " + elegido);

            if (depuracion) {
                System.out.println("Vector de enteros antes");
                for (int elemento : enteros)
                    System.out.print(elemento);
                System.out.println("");


                System.out.println("Vector de enteros después");
                for (int elemento : enteros)
                    System.out.print(elemento);
                System.out.println("");
            }

            finales.add(enteros.get(elegido));
            enteros.remove(elegido);
            numFinal--;
        }

        for (int i = 0; i < finales.size(); i++) {
            int elemento = (int) finales.get(i);
            matrizBooleanos[elemento] = true;
        }

        return matrizBooleanos;
    }

    /**
     * Función para comparar la matriz original y la que crea el usuario
     *
     * @param matrizOriginal La matriz de referencia (la correcta)
     * @param matrizJugador  La matriz que crea el jugador al pulsar los botones
     * @param numFilas       Número de filas de las matrices
     * @param numColumnas    Número de columnas de las matrices
     * @return true si las matrices son iguales y false si no lo son
     */
    public static boolean compruebaMatrices(boolean matrizOriginal[], boolean matrizJugador[], int numFilas, int numColumnas) {

            /*
            El único objetivo es comparar si dos matrices son iguales, para eso comprobaremos celda a celda.
            */

        for (int i = 0; i < numFilas * numColumnas; i++) {
            if (matrizOriginal[i] == false)
                System.out.print("0");
            else
                System.out.print("1");
        }
        System.out.println("");
        for (int i = 0; i < numFilas * numColumnas; i++) {
            if (matrizJugador[i] == false)
                System.out.print("0");
            else
                System.out.print("1");
        }


        boolean resultado = true;
        int tamGrid = numFilas * numColumnas;

        //De esta forma si el error está al principio no tiene que recorrer toda la matriz
        int pos = 0;
        while (resultado == true && pos < tamGrid) {
            if (matrizOriginal[pos] != matrizJugador[pos]) resultado = false;
            pos++;
        }

        return resultado;
    }


    // ### JUEGO 2 ### //

    /**
     * ####### FUNCIÓN RE-ADAPTADA PARA EL JUEGO2
     * <p/>
     * Esta es la función que se copiará en el código de AndroidStudio
     *
     * @param numCeldas   El número de celdas a con las que jugar.
     * @param numFilas    El número de filas que tiene el grid de la jugada.
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
        int seleccion, indice_seleccionado, celdas_rellenadas = 0;

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



        boolean resultado = true;
        int tamGrid = numFilas * numColumnas;

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

        if (indices_contestados.size() != indices_solucion.size())
            resultado = false;
        else {
            for (int i = 0; i < indices_contestados.size() && resultado; i++) {
                if (!indices_solucion.contains(indices_contestados.get(i)))
                    resultado = false;
            }
        }

        return resultado;
    }


    // ### JUEGO4 ### //

    /**
     * Función para obtener una matriz que tenga parejas dentro de ella.
     * Tendrá tanatas parejas (de números) como la mitad del número de celdas.
     *
     * @param numFilas    Numero de filas de la matriz que queremos generar
     * @param numColumnas Numero de columnas de l matriz que queremos generar
     * @return Un vector que simula la matriz.
     */
    static public int[] obtenerMatrizParejas(int numFilas, int numColumnas) {

        int tamMatriz = numFilas * numColumnas;
        int numParejas = tamMatriz / 2;

        int[] matrizResultado = new int[tamMatriz];


        //Montamos esto con dos arrayList ya que es mas facil para no entrar en bucles del random

        //Creamos una lista con los número que tendremos que hacer las parejas
        List<Integer> numeros = new ArrayList<>();
        if (depuracion)
            System.out.println("## NUMEROS ##");
        for (int i = 0; i<numParejas; i++) {
            numeros.add(i);
            if (depuracion)
                System.out.print(numeros.get(i) + " ");
        }
        if (depuracion)
            System.out.println("");


        //Creamos una lista con las posiciones que se puede tener dentro de la matriz:
        List<Integer> posiciones = new ArrayList<>();
        if (depuracion)
            System.out.println("## POSICIONES ##");
        for (int i = 0; i < tamMatriz; i++) {
            posiciones.add(i);
            if (depuracion)
                System.out.print(posiciones.get(i) + " ");
        }
        if (depuracion)
            System.out.println("");


        //  ### ALGORITMO ###

        Random rnd = new Random();
        int elegido, posA, posB;

        for (int i = 0; i < numParejas; i++) {

            //1º. Sacamos un numero al azar del array de numeros. El numero dejara de estar en el array.

            //Lo sacamos mediante un random con los indices del vector.
            elegido = numeros.remove((int) (rnd.nextDouble() * numeros.size()));
            //System.out.print(elegido+" ");

            //2º Sacamos dos numeros de array de posiciones, que sera donde ira en la matriz resultado el numero elegido.
            posA = posiciones.remove((int) (rnd.nextDouble() * posiciones.size()));
            posB = posiciones.remove((int) (rnd.nextDouble() * posiciones.size()));
            //System.out.print("A:"+posA+"  B:"+posB);


            //3º Introducimos esos valores en la matriz resultado
            matrizResultado[posA] = elegido;
            matrizResultado[posB] = elegido;


            //System.out.println("");


        }
        return matrizResultado;

    }


    static public void leerMatrizParejas(int[] valores, int filas, int columnas) {

        System.out.println("## Matriz de parejas ##");

        int col = 1;

        for (int i = 0; i < filas * columnas; i++) {
            System.out.print(valores[i] + " ");
            if (col == columnas) {
                System.out.print("\n");
                col = 1;
            } else
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

    static public String letraEquivalente(int numero){

        //1  ->  A
        //25 ->  Z

        char letra = 'A'; // A es 65, el máximo es Z, 90
        letra--;
        letra+=numero;

        return String.valueOf(letra);

    }

    static public String numeroRomanoEquivalente(int numero){
        String n="";
        switch(numero){
            case 1:
                n="I";
                break;
            case 2:
                n="II";
                break;
            case 3:
                n="III";
                break;
            case 4:
                n="IV";
                break;
            case 5:
                n="V";
                break;
            case 6:
                n="VI";
                break;
            case 7:
                n="VII";
                break;
            case 8:
                n="VIII";
                break;
            case 9:
                n="IX";
                break;
            case 10:
                n="X";
                break;
            case 11:
                n="XI";
                break;
            case 12:
                n="XII";
                break;
            case 13:
                n="XIII";
                break;
            case 14:
                n="XIV";
                break;
            case 15:
                n="XV";
                break;
            case 16:
                n="XVI";
                break;
            case 17:
                n="XVII";
                break;
            case 18:
                n="XVIII";
                break;
            case 19:
                n="XIX";
                break;
            case 20:
                n="XX";
                break;
            case 21:
                n="XXI";
                break;
            case 22:
                n="XXII";
                break;
            case 23:
                n="XXIII";
                break;
            case 24:
                n="XXIV";
                break;
            case 25:
                n="XXV";
                break;
            default:
                n="";
                break;
        }
        return n;
    }


     // ### JUEGO 5 ### //


    /**
     * Para generar de forma simple un número aleatorio entre dos valores inclusives.
     * @param min Cota inferior
     * @param max Cota superior
     * @return  Un valor aleatorio entre las cotas inclusives.
     */
    static public int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();


        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

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

            burbuja(valores, numElems);

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



}
