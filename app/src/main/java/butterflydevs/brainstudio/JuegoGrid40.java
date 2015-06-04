package butterflydevs.brainstudio;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.github.premnirmal.textcounter.CounterView;
import com.github.premnirmal.textcounter.Formatter;

import java.text.NumberFormat;
import java.util.Locale;

import butterflydevs.brainstudio.extras.matrixHelper;


public class JuegoGrid40 extends ActionBarActivity {

    final private int numFilas = 8;
    final private int numColumnas = 5;


    private ProgressBar barraProgreso;
    private Button botonBack;
    private Button botonHelp;

    private boolean matrizJugada[];
    private boolean matrizRespuesta[] = new boolean[numFilas * numColumnas];
    private int numCeldasActivadas = 0;

    private int numRepeticionActual;
    private int numRepeticionesMaximas;
    private int numMaximoCeldas;

    private float puntuacion;

    private int numCeldas = 2;

    //Variables para el reloj:
    private CountDownTimer countDownTimer;
    private CounterView counterView;
    private int time;
    private float timeNow;

    private int tamButtons = 90;

    private LinearLayout layoutGridBotones;

    //Filas del grid de botones:
    private LinearLayout[] filasLinearLayout; //Cada fila de botones es un linearLayout

    //Vector de botones (solo uno, no habra un vector por fila, para el procesamiento posterior es mejor tenerlos todos en un solo vector)
    private Button[] botones;


    private Button prueba;


    Animation animacion1, animacion2;

    public JuegoGrid40() {

        time = 5;
        numRepeticionesMaximas = 4;
        numRepeticionActual = 1;
        numMaximoCeldas = 20;
        puntuacion = 0;


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_grid40);

        //Con esta orden conseguimos hacer que no se muestre la ActionBar.
        getSupportActionBar().hide();
        //Con esta hacemos que la barra de estado del teléfono no se vea y la actividad sea a pantalla completa.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        animacion1 = AnimationUtils.loadAnimation(this, R.anim.animacionbotongrid12);
        animacion1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                System.out.println("La animación empieza");


            }

            @Override
            public void onAnimationEnd(Animation animation) {
                System.out.println("La animacion acaba");
                for (int i = 0; i < numFilas * numColumnas; i++)
                    botones[i].setBackgroundColor(Color.TRANSPARENT);

                //Cuando la animacion 1 acaba se encarga de lanzar la animacion 2
                for (int i = 0; i < numFilas * numColumnas; i++)
                    botones[i].startAnimation(animacion2);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }


        });

        animacion2 = AnimationUtils.loadAnimation(this, R.anim.animacionbotongrid12_2);
        animacion2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                System.out.println("La animación empieza");
                for (int i = 0; i < numFilas * numColumnas; i++)
                    botones[i].setBackgroundColor(getResources().getColor(R.color.darkgray));
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

        asociarElementosVista();

        //Inicializamos el vector de filas.
        filasLinearLayout = new LinearLayout[numFilas];

        //Inicializamos el vector de botones:
        botones = new Button[numFilas * numColumnas];


        //Inicializamos los botones
        for (int i = 0; i < numFilas * numColumnas; i++) {
            //Inicializamos cada uno de los elementos del vector
            botones[i] = new Button(this);
            //Establecemos parametros de layout a cada uno:
            botones[i].setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(tamButtons, tamButtons);
            params.setMargins(5, 5, 5, 5);
            botones[i].setLayoutParams(params);
            botones[i].setBackgroundColor(getResources().getColor(R.color.darkgray));
        }

        //Inicializamos los LinearLayout (filas)
        for (int i = 0; i < numFilas; i++) {
            filasLinearLayout[i] = new LinearLayout(this);
            filasLinearLayout[i].setOrientation(LinearLayout.HORIZONTAL);
            filasLinearLayout[i].setGravity(Gravity.CENTER);
        }


        // prueba=new Button(this);
        //prueba.setText("prueba");
        //prueba.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //prueba.setWidth(50);
        //prueba.setHeight(50);

        //Asociamos el layout principal
        layoutGridBotones = (LinearLayout) findViewById(R.id.gridBotones);
        //Le especificamos una horientación
        layoutGridBotones.setOrientation(LinearLayout.VERTICAL);


        //Añadimos todos los layout de filas al layout principal
        for (int i = 0; i < numFilas; i++)
            layoutGridBotones.addView(filasLinearLayout[i]);


        //Añadimos un boto de prueba
        //filasLinearLayout[0].addView(botones[0]);
        //filasLinearLayout[1].addView(botones[1]);

        //filasLinearLayout es un vector de filas!

        int numBoton = 0;
        for (int i = 0; i < numFilas; i++) //i sera el numero de fila
            for (int j = 0; j < numColumnas; j++) {
                filasLinearLayout[i].addView(botones[numBoton]); //no usamos numColumnas porque es 5 y tendriamos que reajustar
                numBoton++;
            }


        //Configuramos el comportamiento del grid de botones con un Listener específico.
        for (int i = 0; i < numFilas * numColumnas; i++) {
            System.out.println("Boton: " + i);
            botones[i].setOnClickListener(new MyListener(i));
        }


        botonBack.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Creamos el Intent
                        Intent intent = new Intent(JuegoGrid40.this, Niveles.class);
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


    }


    public void asociarElementosVista(){


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

    /**
     * Función para animar el grid al entrar en la actívity
     */
    public void animarGrid() {
        //Cargamos la animación del botón:
        for (int i = 0; i < numFilas * numColumnas; i++)
            botones[i].startAnimation(animacion1);
    }


    @Override
    protected void onStart() {
        super.onStart();


        //Obtenemos la matriz de la jugada que el jugador debe resolver
        matrizJugada = matrixHelper.obtenerMatrizJugada(numCeldas, numFilas, numColumnas);

        //Coloreamos la matriz según la matriz de jugada pedida al matrixHelper
        for (int i = 0; i < numFilas * numColumnas; i++)
            if (matrizJugada[i] == true)
                botones[i].setBackgroundColor(Color.rgb(154, 184, 0));
            else
                botones[i].setBackgroundColor(getResources().getColor(R.color.darkgray));

        //Ilumina el grid
        animarGrid();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prueba, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void siguienteJugada() {



        /*
        *Dependiendo del estado de nivel se crea una jugada u otra
        */

        //Si se ha llegado al maximo numero de repeticiones para este numero de celdas se aumenta el numero de celdas.


        System.out.println("##JUGADA## Celdas: " + numCeldas + "  Repeticiones: " + numRepeticionActual + " de máx " + numRepeticionesMaximas);

        if (numRepeticionActual == numRepeticionesMaximas) {
            System.out.println("pasando a 3 celdas");
            numCeldas++;
            numRepeticionActual = 0;
            /*
            Numero de celdas se usara a continuacion apra configurar la matriz de la jugada (la que tiene que recordar el jugador)
             */
        }

        if (numCeldas == numMaximoCeldas + 1)
            salirNivel();


        //Ajuste de variables:

        //Cuando se acierta se reinicia el proceso.

        for (int i = 0; i < numFilas * numColumnas; i++) {
            matrizJugada[i] = false;
            matrizRespuesta[i] = false;
        }
        numCeldasActivadas = 0;

        //Obtenemos la matriz de la jugada que el jugador debe resolver
        matrizJugada = matrixHelper.obtenerMatrizJugada(numCeldas, numFilas, numColumnas);

        //Seteamos el grid visual con la matriz obtenida.
        for (int i = 0; i < numFilas * numColumnas; i++)
            if (matrizJugada[i] == true)
                botones[i].setBackgroundColor(getResources().getColor(R.color.kiwi));
            else
                botones[i].setBackgroundColor(getResources().getColor(R.color.darkgray));

        //Ilumina el grid
        animarGrid();


        //Aumentamos el valor de repeticion actual:
        numRepeticionActual++;
    }

    public void salirNivel() {

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

    public void calculaPuntuacion() {
        float a = (float) 4.5;
        System.out.println("Tiempo al acabar: " + (float) timeNow / 1000);

        //Ajustamos el tiempo a segundos con un decimal:
        String tiempo = Float.toString((float) timeNow / 1000);

        tiempo = tiempo.substring(0, 4);

        timeNow = Float.parseFloat(tiempo);

        counterView.setStartValue(puntuacion);
        counterView.setPrefix("");
        counterView.setSuffix("");
        puntuacion += timeNow * numCeldas;

        counterView.setEndValue(puntuacion);
        counterView.start();
        System.out.println("Puntuacón : " + puntuacion);


        //Ponemos la puntuación en pantalla
        //textPuntos.setText(Float.toString(puntuacion));


        //puntuacion=numCeldas*

    }
    public void mensajeFin(){
        new AlertDialog.Builder(this)
                .setTitle("YOU ARE DEAD")
                .setMessage("Se te acabó el tiempo!")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete

                        //Creamos el Intent
                        Intent intent = new Intent(JuegoGrid40.this, Niveles.class);
                        //Iniciamos la nueva actividad
                        startActivity(intent);

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    class MyListener implements Button.OnClickListener {

        private int numBoton;

        public MyListener(int numBoton) {
            this.numBoton = numBoton;
        }


        @Override
        public void onClick(View v) {
            //Acciones a realizar al pulsar sobre un botón:



                /*1º Cambiamos de color el boton en función de su estado y por consiguiente la matriz del jugador haciendo
                true la celda en caso de que estuviera a false y viceversa.
                 */
            if (matrizRespuesta[numBoton] == false) {
                botones[numBoton].setBackgroundColor(getResources().getColor(R.color.kiwi));
                matrizRespuesta[numBoton] = true;
                numCeldasActivadas++;
            } else {
                botones[numBoton].setBackgroundColor(getResources().getColor(R.color.darkgray));
                matrizRespuesta[numBoton] = false;
                numCeldasActivadas--;
            }

            //2º Comparamos ambas matrices

                    /*
                    Lo ideal sería llevar el control del número de celdas pulsadas para no realizar comprobaciones
                    antes de tiempo.
                     */

            System.out.println("Celdas Activadas: " + numCeldasActivadas);
            if (numCeldasActivadas == numCeldas) {
                System.out.println("Hay que comparar matrices");

                //Se ha completado el grid
                if (matrixHelper.compruebaMatrices(matrizJugada, matrizRespuesta, numFilas, numColumnas)) {
                    System.out.println("SUCESS");

                    //Se calcula la puntuación obtenida
                    calculaPuntuacion();
                    //Se pasa a la siguiente jugada
                    siguienteJugada();


                } else
                    System.out.println("FAIL");


            }


        }

    }
}
