package butterflydevs.brainstudio;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * ### Explicación de la rutina: ###
 *
 * En esta actividad se crean 20 pantallas de juego. Se empieza preguntando por 2 celdas y este número va aumentando
 * hasta 6 ( el grid es de doce y es el punto donde se llega el máximo de dificultad), entonces se recorren 5 niveles.
 * Dentro de cada nivel se pregunta cuatro veces la matriz (con distinta disposición de los elementos).
 *
 * Cada vez que el jugador pusa cualquier boton:
 *      1. Este se ilumina
 *      2. Se comprueba la matriz con la original
 *
 *      (En este punto se pueden ahorrar bastantes comprobaciones si no se comprueba hasta haber pulsado minimo
 *      tantos botones como se espera para la solucion. Si se esperan 5 celdas del jugador no tiene sentido ir
 *      comprobando cuando pulsa 1, 2, 3, porque siempre dara resultado negativo.)
 *
 */



public class JuegoGrid12 extends ActionBarActivity {

    //Tamaño del grid
    final private int tamGrid=12;
    int contador =0;

    //Vector de botones
    private Button[] botones = new Button[tamGrid];

    Animation loopParpadeante;

    private boolean matrizJugada[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_grid12);

        loopParpadeante= AnimationUtils.loadAnimation(this, R.anim.animacionbotongrid12);


        //Asociamos los elementos de la vista:
        asociarElementosVista();
        //Animamos el grid
       // animarGrid();


     //   for(contador=0; contador<tamGrid; contador++) {

            botones[0].setOnClickListener(

                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Acciones del botón:

                            //CAmbiarlo de color
                            botones[0].setBackgroundColor(Color.RED);

                            //Setearle un color y aplicarle una iluminación rápida.
                            botones[0].setBackgroundColor(Color.RED);

                        }
                    }
            );
       // }


    }

    @Override
    protected void onStart(){
        super.onStart();
        animarGrid();

        //Obtenemos la matriz de la jugada que el jugador debe resolver
        matrizJugada=obtenerMatrizJugada(2,4,3);







    }


    public void asociarElementosVista(){
        String buttonID;
        int resID;

        for(int i=0; i<tamGrid; i++) {
            buttonID="boton"+Integer.toString(i);
            resID = getResources().getIdentifier(buttonID, "id","butterflydevs.brainstudio");
            botones[i]=(Button)findViewById(resID);
           // botones[i].startAnimation(loopParpadeante);
        }

    }

    /**
     * Función para animar el grid al entrar en la actívity
     */
    public void animarGrid(){
        //Cargamos la animación del botón:
        botones[0].startAnimation(loopParpadeante);
        botones[11].startAnimation(loopParpadeante);
        //for(int i=0; i<tamGrid; i++)
          //  botones[i].startAnimation(loopParpadeante);
    }

    /**
     * Función para comparar la matriz original y la que crea el usuario
     * @param matrizOriginal La matriz de referencia (la correcta)
     * @param matrizJugador  La matriz que crea el jugador al pulsar los botones
     * @param numFilas Número de filas de las matrices
     * @param numColumnas Número de columnas de las matrices
     * @return true si las matrices son iguales y false si no lo son
     */
    public boolean compruebaMatrices(boolean matrizOriginal[], boolean matrizJugador[], int numFilas, int numColumnas){

        /*
        El único objetivo es compara si dos matrices son iguales, para eso comprobaremos celda a celda.
        */

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
     * Esta es la función que se copiará en el código de AndroidStudio
     * @param numCeldas El número de celdas a con las que jugar.
     * @param numFilas El número de filas que tiene el grid de la jugada.
     * @param numColumnas El número de columnas que tiene el grid de la jugada.
     * @return
     */
    public boolean[] obtenerMatrizJugada(int numCeldas, int numFilas, int numColumnas){

        boolean depuracion=false;

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


}
