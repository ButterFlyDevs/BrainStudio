package butterflydevs.brainstudio;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.daimajia.numberprogressbar.NumberProgressBar;
import com.github.premnirmal.textcounter.CounterView;
import com.github.premnirmal.textcounter.Formatter;

import java.text.NumberFormat;
import java.util.ArrayList;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;


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



public class JuegoGrid12 extends ActionBarActivity{




    //Variables para el reloj:
    private CountDownTimer countDownTimer;
    private int time;
    private float timeNow;

    private float puntuacion;

    private NumberProgressBar bnp;

    private CounterView counterView;



    //Tamaño del grid
    final private int tamGrid=12;
    private int numCeldas=2;

    int contador =0;

    //Vector de botones
    private Button[] botones = new Button[tamGrid];
    private TextView textPuntos;


    Animation animacion1, animacion2;

    private boolean matrizJugada[];
    private boolean matrizRespuesta[] = new boolean[tamGrid];

    private int numCeldasActivadas=0;

    //Variables de partida
    private int numRepeticionesMaximas;
    private int numRepeticionActual;
    private int numMaximoCeldas;


    private ProgressBar barraProgreso;



    public JuegoGrid12(){
        time=5;
        numRepeticionesMaximas=4;
        numRepeticionActual=1;
        numMaximoCeldas=6;
        puntuacion=0;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_grid12);

        //Con esta orden conseguimos hacer que no se muestre la ActionBar.
        getSupportActionBar().hide();
        //Con esta hacemos que la barra de estado del teléfono no se vea y la actividad sea a pantalla completa.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        animacion1= AnimationUtils.loadAnimation(this, R.anim.animacionbotongrid12);
        animacion1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
               System.out.println("La animación empieza");


            }

            @Override
            public void onAnimationEnd(Animation animation) {
                System.out.println("La animacion acaba");
                for(int i=0; i<tamGrid; i++)
                    botones[i].setBackgroundColor(Color.TRANSPARENT);

                //Cuando la animacion 1 acaba se encarga de lanzar la animacion 2
                for(int i=0; i<tamGrid; i++)
                    botones[i].startAnimation(animacion2);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }


        });

        animacion2= AnimationUtils.loadAnimation(this, R.anim.animacionbotongrid12_2);
        animacion2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                System.out.println("La animación empieza");for(int i=0; i<tamGrid; i++)
                    botones[i].setBackgroundColor(Color.BLACK);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                System.out.println("La animacion acaba");
                //Cuando la segunda animación termina el tiempo comienza a correr.
                countDownTimer.start();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }


        });

        //Asociamos los elementos de la vista:
        asociarElementosVista();

        ajustarAspectoBotones();

        for(contador=0; contador<tamGrid; contador++) {
            System.out.println("Boton: "+contador);
            botones[contador].setOnClickListener(new MyListener(contador));
        }

        //Configuracion del temporizador.
        //Le pasamos al constructor la variable tiempo
        countDownTimer = new CountDownTimer(time*1000, 1000) {

            //Lo que hacemos en cada tick del reloj.
            public void onTick(long millisUntilFinished) {
                barraProgreso.setProgress((int)millisUntilFinished / 1000);
                System.out.println("reloj:" + millisUntilFinished / 1000);
                timeNow= millisUntilFinished;

            }
            //Comportamiento al acabarse el timepo.
            public void onFinish() {

                barraProgreso.setProgress(0);

            }
        };
        //countDownTimer.start();

    } //Fin onCreate

    /**
     * Ajuste del aspecto del grid de botones
     */
    public void ajustarAspectoBotones(){


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(150,150);
        params.setMargins(5,5,5,5);

        for(contador=0; contador<tamGrid; contador++){
            botones[contador].setBackgroundColor(Color.BLACK);
            botones[contador].setLayoutParams(params);
        }

//        textPuntos.setText("0");
    }

    /**
     * Hace la asociación de los elementos de la vista con variables de esta clase para poder controlarlos.
     */
    public void asociarElementosVista(){
        String buttonID;
        int resID;

        //Asociación de los botones de forma dinámica
        for(int i=0; i<tamGrid; i++) {
            buttonID="boton"+Integer.toString(i);
            resID = getResources().getIdentifier(buttonID, "id","butterflydevs.brainstudio");
            botones[i]=(Button)findViewById(resID);
        }

        barraProgreso=(ProgressBar)findViewById(R.id.progressBar);
       // textPuntos=(TextView)findViewById(R.id.textPuntos);

        counterView=(CounterView)findViewById(R.id.counter);

        counterView.setAutoFormat(false);
        counterView.setFormatter(new Formatter() {
            @Override
            public String format(String prefix, String suffix, float value) {
                return prefix + NumberFormat.getNumberInstance(Locale.US).format(value) + suffix;
            }
        });
        counterView.setAutoStart(false);
        counterView.setStartValue(0);
        counterView.setEndValue(0);
        counterView.setIncrement(1f); // the amount the number increments at each time interval
        counterView.setTimeInterval(2); // the time interval (ms) at which the text changes
        counterView.setPrefix("");
        counterView.setSuffix("");
        counterView.start();


    }

    @Override
    protected void onStart(){
        super.onStart();

        //Obtenemos la matriz de la jugada que el jugador debe resolver
        matrizJugada=obtenerMatrizJugada(numCeldas,4,3);

        for(int i=0; i<tamGrid; i++)
            if(matrizJugada[i]==true)
                botones[i].setBackgroundColor(Color.RED);
            else
                botones[i].setBackgroundColor(Color.BLACK);

        //Ilumina el grid
        animarGrid();
    }

    /**
     * Función para animar el grid al entrar en la actívity
     */
    public void animarGrid(){
        //Cargamos la animación del botón:
        for(int i=0; i<tamGrid; i++)
                botones[i].startAnimation(animacion1);
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

    public void siguienteJugada(){



        /*
        *Dependiendo del estado de nivel se crea una jugada u otra
        */

        //Si se ha llegado al maximo numero de repeticiones para este numero de celdas se aumenta el numero de celdas.



        System.out.println("##JUGADA## Celdas: "+numCeldas+"  Repeticiones: "+numRepeticionActual + " de máx "+ numRepeticionesMaximas);

        if(numRepeticionActual==numRepeticionesMaximas) {
            System.out.println("pasando a 3 celdas");
            numCeldas++;
            numRepeticionActual=0;
            /*
            Numero de celdas se usara a continuacion apra configurar la matriz de la jugada (la que tiene que recordar el jugador)
             */
        }

        if(numCeldas==numMaximoCeldas+1)
            salirNivel();


        //Ajuste de variables:

        //Cuando se acierta se reinicia el proceso.

        for(int i=0; i<tamGrid; i++) {
            matrizJugada[i] = false;
            matrizRespuesta[i] = false;
        }
        numCeldasActivadas=0;

        //Obtenemos la matriz de la jugada que el jugador debe resolver
        matrizJugada=obtenerMatrizJugada(numCeldas,4,3);

        //Seteamos el grid visual con la matriz obtenida.
        for(int i=0; i<tamGrid; i++)
            if(matrizJugada[i]==true)
                botones[i].setBackgroundColor(Color.RED);
            else
                botones[i].setBackgroundColor(Color.BLACK);

        //Ilumina el grid
        animarGrid();


        //Aumentamos el valor de repeticion actual:
        numRepeticionActual++;
    }


    public void calculaPuntuacion(){
        float a= (float) 4.5;
        System.out.println("Tiempo al acabar: " +(float)timeNow/1000);

        //Ajustamos el tiempo a segundos con un decimal:
        String tiempo = Float.toString((float)timeNow/1000);
        tiempo = tiempo.substring(0,4);

        timeNow=Float.parseFloat(tiempo);

        counterView.setStartValue(puntuacion);
        counterView.setPrefix("");
        counterView.setSuffix("");
        puntuacion+=timeNow*numCeldas;

        counterView.setEndValue(puntuacion);
        counterView.start();
        System.out.println("Puntuacón : "+puntuacion );



        //Ponemos la puntuación en pantalla
        //textPuntos.setText(Float.toString(puntuacion));



        //puntuacion=numCeldas*

    }

    public void salirNivel(){

        //Avisar de que se ha acabado el juego y salir al panel de niveles.

        //¿Mostrar un fragment con la puntuación y y el porcentaje completado?

        new AlertDialog.Builder(this)
                .setTitle("TERMINADO")
                .setMessage("has llegado al final del nivel")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    class MyListener implements Button.OnClickListener{

        private int numBoton;

        public MyListener(int numBoton){
            this.numBoton=numBoton;
        }

        @Override
        public void onClick(View v) {
            //Acciones a realizar al pulsar sobre un botón:



                /*1º Cambiamos de color el boton en función de su estado y por consiguiente la matriz del jugador haciendo
                true la celda en caso de que estuviera a false y viceversa.
                 */
                if(matrizRespuesta[numBoton]==false) {
                    botones[numBoton].setBackgroundColor(Color.RED);
                    matrizRespuesta[numBoton] = true;
                    numCeldasActivadas++;
                }else {
                    botones[numBoton].setBackgroundColor(Color.BLACK);
                    matrizRespuesta[numBoton] = false;
                    numCeldasActivadas--;
                }

                //2º Comparamos ambas matrices

                    /*
                    Lo ideal sería llevar el control del número de celdas pulsadas para no realizar comprobaciones
                    antes de tiempo.
                     */
                    System.out.println("Celdas Activadas: "+ numCeldasActivadas);
                    if(numCeldasActivadas==numCeldas) {
                        System.out.println("Hay que comparar matrices");

                        //Se ha completado el grid
                        if(compruebaMatrices(matrizJugada,matrizRespuesta,4,3)) {
                            System.out.println("SUCESS");

                            //Se calcula la puntuación obtenida
                            calculaPuntuacion();
                            //Se pasa a la siguiente jugada
                            siguienteJugada();



                        }else
                            System.out.println("FAIL");

                    }



        }

        }
    }


