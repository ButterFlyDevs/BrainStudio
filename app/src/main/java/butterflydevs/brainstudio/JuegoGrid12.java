package butterflydevs.brainstudio;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import java.util.Locale;


import butterflydevs.brainstudio.extras.matrixHelper;


/**
 *  ### Explicaci&oacute;n de la rutina: ###
 *
 * En esta actividad se crean 20 pantallas de juego. Se empieza preguntando por 2 celdas y este n&uacute;mero va aumentando
 * hasta 6 ( el grid es de doce y es el punto donde se llega el m&aacute;ximo de dificultad), entonces se recorren 5 niveles.
 * Dentro de cada nivel se pregunta cuatro veces la matriz (con distinta disposici&oacute;n de los elementos).
 *
 * Cada vez que el jugador pusa cualquier boton:
 *      1. Este se ilumina
 *      2. Se comprueba la matriz con la original
 *
 *      (En este punto se pueden ahorrar bastantes comprobaciones si no se comprueba hasta haber pulsado minimo
 *      tantos botones como se espera para la solucion. Si se esperan 5 celdas del jugador no tiene sentido ir
 *      comprobando cuando pulsa 1, 2, 3, porque siempre dara resultado negativo.)
 *
 *
 * @author Juan A. Fern&aacute;ndez S&aacute;nchez
 * @version Junio 2015
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

    private Button botonBack;
    private Button botonHelp;



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

        //Configuramos el comportamiento del grid de botones con un Listener específico.
        for(contador=0; contador<tamGrid; contador++) {
            System.out.println("Boton: "+contador);
            botones[contador].setOnClickListener(new MyListener(contador));
        }



        botonBack.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Creamos el Intent
                        Intent intent = new Intent(JuegoGrid12.this, Niveles.class);
                        //Iniciamos la nueva actividad
                        startActivity(intent);
                    }
                }
        );
        botonHelp.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Creamos el Intent
                       // Intent intent = new Intent(JuegoGrid12.this, Help.class);
                        //Iniciamos la nueva actividad
                       // startActivity(intent);
                    }
                }
        );



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
            public void onFinish(){

                barraProgreso.setProgress(0);
                mensajeFin();


            }
        };
        //countDownTimer.start();

    } //Fin onCreate

    /**
     * Ajuste del aspecto del grid de botones
     */
    public void ajustarAspectoBotones(){


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(150,150);
        params.setMargins(5, 5, 5, 5);

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
        botonBack=(Button)findViewById(R.id.botonBack);
        botonHelp=(Button)findViewById(R.id.botonHelp);



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
        matrizJugada=matrixHelper.obtenerMatrizJugada(numCeldas,4,3);

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
        matrizJugada=matrixHelper.obtenerMatrizJugada(numCeldas,4,3);

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

    public void mensajeFin(){
        new AlertDialog.Builder(this)
                .setTitle("YOU ARE DEAD")
                .setMessage("Se te acabó el tiempo!")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete

                        //Creamos el Intent
                        Intent intent = new Intent(JuegoGrid12.this, Niveles.class);
                        //Iniciamos la nueva actividad
                        startActivity(intent);

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
                        if(matrixHelper.compruebaMatrices(matrizJugada,matrizRespuesta,4,3)) {
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


