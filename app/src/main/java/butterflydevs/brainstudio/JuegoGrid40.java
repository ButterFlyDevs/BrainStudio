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

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.github.premnirmal.textcounter.CounterView;
import com.github.premnirmal.textcounter.Formatter;

import java.text.NumberFormat;
import java.util.Locale;

import butterflydevs.brainstudio.extras.matrixHelper;


public class JuegoGrid40 extends ActionBarActivity {

    //Constantes que definen el tamaño del grid
    final private int numFilas = 8;
    final private int numColumnas = 5;

    //Variables de elementos visuales que necesitan referenciación
    private RoundCornerProgressBar barraProgreso;
    private int progress2 = 100;

    private Button botonBack;
    private Button botonHelp;

    private boolean puedeMostrarBarra=true;


    //Matrices usadas en el juego:

        //Matriz aleatoria con el número de celdas a adescubrir creada por el matrixHelper
        private boolean matrizJugada[];
        //Matriz que se inicializa a false
        private boolean matrizRespuesta[];


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




    //Variables para la configuración del grid de botones en tiempo de ejecución

        //El tamaño de los botones, usado para el alto y el ancho.
        final private int tamButtons = 70;

        //Para referenciar el layout grande donde van todos los layout que componen las filas
        private LinearLayout layoutGridBotones;

        //Filas del grid de botones:
        private LinearLayout[] filasLinearLayout; //Cada fila de botones es un linearLayout

        //Vector de botones (solo uno, no habra un vector por fila, para el procesamiento posterior es mejor tenerlos todos en un solo vector)
        private Button[] botones;




    //Variables para las animaciones del grid.
    Animation animacion1, animacion2;

    public JuegoGrid40() {

        matrizRespuesta = new boolean[numFilas * numColumnas];
        for(int i=0; i<numFilas*numColumnas; i++)
            matrizRespuesta[i]=false;

        time = 15;
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

        //Cargamos el fichero que define la animación 1
        animacion1 = AnimationUtils.loadAnimation(this, R.anim.animacionbotongrid12);
        //Especificamos el comportamiento al empezar y al finalizar
        animacion1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                System.out.println("La animación empieza");
                barraProgreso.setProgress(100f);
                updateProgressTwoColor(100f);


            }

            //Especificamos que ocurre cuando la animación1 termina
            @Override
            public void onAnimationEnd(Animation animation) {
                System.out.println("La animacion acaba");
                //Cuando la animación 1 termina volvemos todos los botones transparentes.
                for (int i = 0; i < numFilas * numColumnas; i++)
                    botones[i].setBackgroundColor(Color.TRANSPARENT);

                //Cuando la animacion 1 acaba se encarga de lanzar la animacion 2
                for (int i = 0; i < numFilas * numColumnas; i++)
                    botones[i].startAnimation(animacion2);

                puedeMostrarBarra=true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }


        });

        //Cargamos el fichero que define la animación 2, que se lanza al acabar la animación 1
        animacion2 = AnimationUtils.loadAnimation(this, R.anim.animacionbotongrid12_2);
        //Especificamos el comportamiento al empezar y al finalizar
        animacion2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                System.out.println("La animación empieza");
                //En la animación 2 volvemos todos los botones grises cuando empieza.
                for (int i = 0; i < numFilas * numColumnas; i++)
                    botones[i].setBackgroundColor(getResources().getColor(R.color.darkgray));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                System.out.println("La animacion acaba");
                //Cuando la segunda animación termina el tiempo comienza a correr.
                  countDownTimer.start();
                  //countDownTimer.
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

                if(puedeMostrarBarra) //Evita mostrar la barra corriendo cuando no debe
                    barraProgreso.setProgress(reglaTres((int)millisUntilFinished / 1000));

                updateProgressTwoColor((int)millisUntilFinished / 1000);

                System.out.println("reloj:" + (int)millisUntilFinished / 1000);
                timeNow= millisUntilFinished;

            }
            //Comportamiento al acabarse el timepo.
            public void onFinish(){

                barraProgreso.setProgress(0f);
                //updateProgressTwoColor();
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
                        Intent intent = new Intent(JuegoGrid40.this, juegoN.class);
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


        //Hacemos transparente el fondo de la barra de progreso.
        barraProgreso.setBackgroundColor(Color.TRANSPARENT);

    }

    public float reglaTres(int x){
        return (x*100)/(time-1);
    }

    private void updateProgressTwoColor(float time ) {
        if(time <= 2) {
            barraProgreso.setProgressColor(getResources().getColor(R.color.custom_progress_red_progress));
        } else if(time > 2 && time <= 6) {
            barraProgreso.setProgressColor(getResources().getColor(R.color.custom_progress_orange_progress));
        } else if(time > 6) {
            barraProgreso.setProgressColor(getResources().getColor(R.color.custom_progress_green_progress));
        }
    }



    public void asociarElementosVista(){


        barraProgreso=(RoundCornerProgressBar)findViewById(R.id.progress_two);

        botonBack=(Button)findViewById(R.id.botonBack);
        botonHelp=(Button)findViewById(R.id.botonHelp);


        //CounterView es el texto de puntos que va aumentando
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
        //Cargamos la animación "animacion1" a cada uno de los botones que componen el grid.
        for (int i = 0; i < numFilas * numColumnas; i++)
            botones[i].startAnimation(animacion1);
    }

    /**
     * Método que se ejecuta cuando la actividad se ha cargado y comienza a funcionar.
     */
    @Override
    protected void onStart() {
        super.onStart();


        //1º Obtenemos la matriz de la jugada que el jugador debe resolver con la clase matrixHelper
        matrizJugada = matrixHelper.obtenerMatrizJugada(numCeldas, numFilas, numColumnas);

        //2º Coloreamos los botones del grid según el contenido de la matriz de booleanos
        for (int i = 0; i < numFilas * numColumnas; i++)
            if (matrizJugada[i] == true)
                botones[i].setBackgroundColor(Color.rgb(154, 184, 0));
            else
                botones[i].setBackgroundColor(getResources().getColor(R.color.darkgray));

        //3º Con los botones configurados como la matriz llamamos a animarGrid para que anime la visualización
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
                        Intent intent = new Intent(JuegoGrid40.this, juegoN.class);
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


                    barraProgreso.setProgress(100f);

                    puedeMostrarBarra=false;

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
