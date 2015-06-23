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

package butterflydevs.brainstudio;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.github.premnirmal.textcounter.CounterView;
import com.github.premnirmal.textcounter.Formatter;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

import butterflydevs.brainstudio.extras.Jugada;
import butterflydevs.brainstudio.extras.MySQLiteHelper;
import butterflydevs.brainstudio.extras.matrixHelper;

/**
 * Clase para el Juego4 parejas de colores.
 * En este caso el centro de la logica esta en el listener de los botones.
 */
public class Juego4niveln extends ActionBarActivity {

    //Constantes que definen el tamaño del grid
    private int numFilas = 6;
    private int numColumnas = 4;

    //Variables de elementos visuales que necesitan referenciación
    private RoundCornerProgressBar barraProgreso;
    private int progress2 = 100;

    private Button botonBack;
    private Button botonHelp;

    private boolean puedeMostrarBarra=true;


    private int[] coloresElegidos;
    private int colorAnterior=-1;
    private int botonAnterior=-1;


    //Matrices usadas en el juego:

        //Matriz aleatoria con las parejas dentro.
        private int matrizParejas[];


    private int numCeldasActivadas = 0;

    private int numRepeticionActual;
    private int numRepeticionesMaximas;
    private int numMaximoCeldas;

    private float puntuacion;

    private int numGridsJugados;

    private int numCeldas = 2;


    //Variables para el reloj:
    private CountDownTimer countDownTimer;
    private CounterView counterView;
    private int time;
    private float timeNow;

    private int level;


    //Variables para la configuración del grid de botones en tiempo de ejecución

    //El tamaño de los botones, usado para el alto y el ancho.
    private int tamButtons = 120;

    //Para referenciar el layout grande donde van todos los layout que componen las filas
    private LinearLayout layoutGridBotones;

    //Filas del grid de botones:
    private LinearLayout[] filasLinearLayout; //Cada fila de botones es un linearLayout

    //Vector de botones (solo uno, no habra un vector por fila, para el procesamiento posterior es mejor tenerlos todos en un solo vector)
    private Button[] botones;
    private Boolean[] acertados;

    Intent intent;

    //Colores
    String[] colores;
    int colorMarcado;
    int colorFondo;


    //Variables para las animaciones del grid.
    Animation animacion1, animacion2;

    public Juego4niveln() {



        time = 15;
        numRepeticionesMaximas = 4;
        numRepeticionActual = 1;
        numMaximoCeldas = 20;
        puntuacion = 0;
        numGridsJugados=0;



    }

    /**
     * Primera función que se ejecuta al arrancar la actívity.
     * @param savedInstanceState Valores de estado guardado en el caso de que los hubiera.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego4niveln);

        //Ajuste del aspecto de la activity.

            //Con esta orden conseguimos hacer que no se muestre la ActionBar.
            getSupportActionBar().hide();
            //Con esta hacemos que la barra de estado del teléfono no se vea y la actividad sea a pantalla completa.
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //Cargamos los colores en un vector desde el array colores_parejas en values/colors.xml
        colores= getResources().getStringArray(R.array.colores_parejas2);

        //Obtenemos los datos que se le pasa a la actividad para configurar el juego.
        intent=getIntent();
        //Obtenemos la información del intent que nos evía la actividad que nos crea.
        level=intent.getIntExtra("nivel",0);


        System.out.println("recibido de la actividad llamante: "+level);
        ajustarNivel(level);


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
        //Le pasamos al constructor la variable tiempo para ajustarlo a nuestro gusto.
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

                finalizarPartida();
                //Si el mensaje anterior queda ahí se añade muchisimas veces la puntuación otenida.


            }
        };

        asociarElementosVista();

        //Inicializamos el vector de filas.
        filasLinearLayout = new LinearLayout[numFilas];

        //Inicializamos el vector de botones:
        botones = new Button[numFilas * numColumnas];

        //Inicializamos el vector de acertados:
        acertados = new Boolean[numFilas * numColumnas];
        for(int i=0; i<numFilas*numColumnas; i++)
            acertados[i]=false;


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


        //Añadimos los botones a los layouts.
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

                        //Para evitar errores con la variable de tiempo lo paramos:
                        countDownTimer.cancel();


                        //Creamos el Intent
                        Intent intent = new Intent(Juego4niveln.this, Juego4.class);
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

    /**
     * Función de ajuste de nivel del juego. Es la más importante pués según configure las variables se configurará después
     * el aspecto y jugabilidad de todas los grids que se vayan creando. Tambien afecta a la fomra en la que se calcula
     * el porcentaje completado del juego.
     * @param level
     */
    public void ajustarNivel(int level){
        if(level==1){

            //1º Establecemos el tamaño del grid
            numFilas=4;
            numColumnas=3;

            //2º Ajustar el tamaño de los botones
            tamButtons = 150;

        }else if(level==2){

            //1º Establecemos el tamaño del grid
            numFilas=6;
            numColumnas=4;

            //2º Ajustar el tamaño de los botones
            tamButtons = 110;

        }else if(level==3){

            //1º Establecemos el tamaño del grid
            numFilas=8;
            numColumnas=5;

            //2º Establecemos el número máximo de celdas a preguntar
            numMaximoCeldas=20;

            //3º Ajustar el tamaño de los botones
            tamButtons = 80;

        }
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
        matrizParejas = matrixHelper.obtenerMatrizParejas(numFilas,numColumnas);

        //2º Obtenemos los colores con los que vamos a jugar.
        /**
         * Tenemos guardados 20 colores distintos que se usan en el grid de 40 pero en los mas pequeños
         * se usan un numero menos de estos que tenemos que elegir y guardar en un vector (solo los indices).
         */
        coloresElegidos = matrixHelper.generaColores(20,(numFilas*numColumnas)/2);

        matrixHelper.leeColores(coloresElegidos);


        matrixHelper.leerMatrizParejas(matrizParejas, numFilas, numColumnas);

        //3º Con los botones configurados como la matriz llamamos a animarGrid para que anime la visualización
        //animarGrid(); //DESCONECTAMOS LA ANIMACIÓN POR AHORA.
    }

    /**
     * Programación personalizada del comportamiento de los botones físicos del terminal.
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){

        //Si pulsamos el botón back nos devuelve a la pantalla principal!:

        if(keyCode== KeyEvent.KEYCODE_BACK){

            //Al pulsar sobre el botón atrás físico del terminal cancelamos el tiempo para evitar problemas
            //De referencia después.
            countDownTimer.cancel();

            //Despues de parar el tiempo salimos a la pantalla aterior.
            Intent intent = new Intent(Juego4niveln.this, Juego1.class);
            //Iniciamos la nueva actividad
            startActivity(intent);

            return true;
        }

        return super.onKeyDown(keyCode, event);

    }

    public void nuevosColores(){



        /**
         * Para que funcione tienen que existir al menos dos colores en el array xml en colors.xml
         */
        Random rnd = new Random();
        colorMarcado=(int)(rnd.nextDouble() * 3 + 0);
        System.out.println("Color seleccionado "+colorMarcado);

        //this.colorFondo=Color.DKGRAY;
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
                        grabarDatosBD();
                        //Creamos el Intent
                        Intent intent = new Intent(Juego4niveln.this, Juego1.class);
                        //Iniciamos la nueva actividad
                        startActivity(intent);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void calculaPuntuacion() {
        System.out.println("Tiempo al acabar: " + (float) timeNow / 1000);

        //Ajustamos el tiempo a segundos con un decimal:
        String tiempo = Float.toString((float) timeNow / 1000);

        if(tiempo.length()>=4)
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

    public int calculaPorcentaje(){

        int resultado;

        int gridsTotales=(numMaximoCeldas-1)*numRepeticionesMaximas;

        //Una regla de tres simple
        resultado=(int)(100*numGridsJugados)/gridsTotales;

        System.out.println("numGridJugados "+numGridsJugados);

        return resultado;


    }

    /**
     * Para grabar los datos en la base de datos cuando se acaba la partida
     */
    public void grabarDatosBD(){


        MySQLiteHelper db = new MySQLiteHelper(this);

        /*
        Realizamos la insercción en la base de datos.
        --> Estamos haciendo un redondeo de la puntuación (esto hay que modificarlo)
         */

        //System.out.println("GRabando "+(int)puntuacion+" puntos "+calculaPorcentaje()+"%");
        db.addJugada(new Jugada((int) puntuacion, calculaPorcentaje()), level);
    }

    public void finalizarPartida(){

        mensajeFin();
    }

    public void mensajeFin() {
        new AlertDialog.Builder(this)
                .setTitle("YOU ARE DEAD")
                .setMessage("Se te acabó el tiempo!")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        grabarDatosBD();
                        //Creamos el Intent
                        Intent intent = new Intent(Juego4niveln.this, Juego1.class);
                        //Iniciamos la nueva actividad
                        startActivity(intent);

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Para comprobar si una matriz está completa.
     * @return Si la matriz está completa.
     */
    public boolean matrizCompleta(){

        boolean completa=true;

        //En cuanto encontremos una celda que no este acertada la mariz no esta completa.
        for(int i=0; i<numFilas*numColumnas; i++)
            if(acertados[i]==false)
                return false;

        return completa;
    }

    public void finPartida(){
            Toast.makeText(getApplicationContext(), "FIN DE PARTIDA ZORRA", Toast.LENGTH_SHORT).show();
    }

    class MyListener implements Button.OnClickListener {

        private int numBoton;

        public MyListener(int numBoton) {
            this.numBoton = numBoton;
        }


        /**
         * Esta es la programación generica de todos los botones, hay que tener cuidado.
         * @param v
         */
        @Override
        public void onClick(View v) {

            //Mensaje por terminal
            System.out.println("Pulsado botón: " + numBoton);

            if (acertados[numBoton] == false) {
                //Cambiamos el color del boton según la matriz parejas.
                botones[numBoton].setBackgroundColor(Color.parseColor(colores[  coloresElegidos[matrizParejas[numBoton]]     ]));
                if (botonAnterior == -1)
                    botonAnterior = numBoton;

                //Si es el primer boton pulsado de la pareja se guarda el color.
                if (colorAnterior == -1) {
                    colorAnterior = Color.parseColor(colores[  coloresElegidos[matrizParejas[numBoton]]     ]);
                    Toast.makeText(getApplicationContext(), "Guardado color" + colorAnterior, Toast.LENGTH_SHORT).show();

                    //Si es el segundo boton se compara con el primero
                } else {
                    //Si hay coincidencia se avisa
                    if (colorAnterior == Color.parseColor(colores[  coloresElegidos[matrizParejas[numBoton]]     ])) {
                        Toast.makeText(getApplicationContext(), "MISMO COLOR", Toast.LENGTH_SHORT).show();
                        acertados[numBoton] = true;
                        acertados[botonAnterior] = true;

                        //Se reinician los parámetros para una nueva jugada
                        colorAnterior=-1;
                        botonAnterior=-1;

                        if(matrizCompleta())
                            finPartida();


                        //Si no hay coincidencia se oscurecen ambos botones y se reinician los punteros.
                    }else {


                        Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();

                        System.out.println("Botones: " + botonAnterior + " " + numBoton);

                        botones[numBoton].setBackgroundColor(getResources().getColor(R.color.darkgray));
                        botones[botonAnterior].setBackgroundColor(getResources().getColor(R.color.darkgray));

                        colorAnterior = -1;
                        botonAnterior = -1;
                    }
                }
            }
        }

    }
}
