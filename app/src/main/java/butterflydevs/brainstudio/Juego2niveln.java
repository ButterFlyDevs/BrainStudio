package butterflydevs.brainstudio;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
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
import java.util.Random;

import butterflydevs.brainstudio.extras.Jugada;
import butterflydevs.brainstudio.extras.MySQLiteHelper;
import butterflydevs.brainstudio.extras.matrixHelper;


/**
 *
 * #################### Clase del juego 2 ##########################
 * ___________________________________________________________
 *
 * En este juego, se usan figuras para rellenar la matriz. El usuario debe recordar las figuras, y la aplicación
 * le preguntará por una de ellas, y debe de marcar todas las figuras del tipo marcado en la matriz.
 * En esencia, es muy parecido al juego 1, solo que ahora la matriz no debe ser de booleanos; debe ser una matriz
 * de enteros, y cada figura dentrá un entero asociado a ella.
 *
 *  La codificación de las figuras es:
 *  0 ==> Esa celda no tiene figura
 *  1 ==> Esa celda tiene un cuadrado
 *  2 ==> Esa celda tiene un triángulo
 *  3 ==> Esa celda tiene un córculo
 *
 */
public class Juego2niveln extends ActionBarActivity {


    //Constantes que definen el tamaño del grid
    private int numFilas = 6;
    private int numColumnas = 4;

    //Variables de elementos visuales que necesitan referenciación
    private RoundCornerProgressBar barraProgreso;
    private int progress2 = 100;

    private Button botonBack;
    private Button botonHelp;

    private boolean puedeMostrarBarra=true;


    //Matrices usadas en el juego:

        //Matriz aleatoria con el número de celdas a adescubrir creada por el matrixHelper
        private boolean matrizJugada[];
        //Matriz que se inicializa a 0
        private int matrizRespuesta[];


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

    Intent intent;

    //Colores
    String[] colores;
    int colorMarcado;
    int colorFondo;


    //Variables para las animaciones del grid.
    Animation animacion1, animacion2;

    public Juego2niveln() {



        time = 15;
        numRepeticionesMaximas = 4;
        numRepeticionActual = 1;
        numMaximoCeldas = 20;
        puntuacion = 0;
        numGridsJugados=0;


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego2niveln);

        //Con esta orden conseguimos hacer que no se muestre la ActionBar.
        getSupportActionBar().hide();
        //Con esta hacemos que la barra de estado del teléfono no se vea y la actividad sea a pantalla completa.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        colores= getResources().getStringArray(R.array.colores);

        //Obtenemos los datos que se le pasa a la actividad.
        intent=getIntent();
        //Obtenemos la información del intent que nos evía la actividad que nos crea.
        level=intent.getIntExtra("nivel",0);

        System.out.println("recibiod de la actividad llamante: "+level);
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

                finalizarPartida();
                //Si el mensaje anterior queda ahí se añade muchisimas veces la puntuación otenida.


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
                        Intent intent = new Intent(Juego2niveln.this, Juego1.class);
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
     * Método usado para ajustar el nivel del juego. Recibe un nivel como parámetro (1,2, o 3) y ajusta
     * las matrices del tablero y el tamaño de los botones para ese nivel
     *
     * @param nivel ==> el nivel de tablero (1,2 o 3) que se debe preparar
     */
    private void ajustarNivel(int nivel){
        if(level==1){

            //1º Establecemos el tamaño del grid

            numFilas=4;
            numColumnas=3;

            //Inicializamos la matriz
            matrizRespuesta = new int[numFilas * numColumnas];

            //La inicializamos a false
            for(int i=0; i<numFilas*numColumnas; i++)
                matrizRespuesta[i]=0;

            //2º Establecemos el número máximo de celdas a preguntar

            numMaximoCeldas=6;

            //3º Ajustar el tamaño de los botones
            tamButtons = 150;

        }else if(level==2){

            //1º Establecemos el tamaño del grid

            numFilas=6;
            numColumnas=4;

            //Inicializamos la matriz
            matrizRespuesta = new int [numFilas * numColumnas];

            //La inicializamos a false
            for(int i=0; i<numFilas*numColumnas; i++)
                matrizRespuesta[i]=0;

            //2º Establecemos el número máximo de celdas a preguntar

            numMaximoCeldas=12;

            //3º Ajustar el tamaño de los botones
            tamButtons = 110;

        }else if(level==3){
            //1º Establecemos el tamaño del grid

            numFilas=8;
            numColumnas=5;

            //Inicializamos la matriz
            matrizRespuesta = new int[numFilas * numColumnas];

            //La inicializamos a false
            for(int i=0; i<numFilas*numColumnas; i++)
                matrizRespuesta[i]=0;

            //2º Establecemos el número máximo de celdas a preguntar
            numMaximoCeldas=20;

            //3º Ajustar el tamaño de los botones
            tamButtons = 80;


        }
    }


    /**
     * Método usado para actualizar el progreso de la barra de tiempo durante el juego. Recibe el parámetro tiempo,
     * y si es menor de 2 segundos colorea la barra de rojo, entre 2 y 6 e amarillo, y más de 6 verde.
     *
     * @param time ==> Tiempo que falta hasta acabar el juego
     */
    private void updateProgressTwoColor(float time ) {
        if(time <= 2) {
            barraProgreso.setProgressColor(getResources().getColor(R.color.custom_progress_red_progress));
        } else if(time > 2 && time <= 6) {
            barraProgreso.setProgressColor(getResources().getColor(R.color.custom_progress_orange_progress));
        } else if(time > 6) {
            barraProgreso.setProgressColor(getResources().getColor(R.color.custom_progress_green_progress));
        }
    }

    public float reglaTres(int x){
        return (x*100)/(time-1);
    }


    public void finalizarPartida(){

        mensajeFin();
    }

    public void mensajeFin(){
        new AlertDialog.Builder(this)
                .setTitle("YOU ARE DEAD")
                .setMessage("Se te acabó el tiempo!")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete

                        //TODO IMPLEMENTAR GRABAR DATOS BD
                        //grabarDatosBD();
                        //Creamos el Intent
                        Intent intent = new Intent(Juego2niveln.this, Juego1.class);
                        //Iniciamos la nueva actividad
                        startActivity(intent);

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Método para asociar los elementos creados en las funciones anteriores a elementos visibles en la vista
     * de la aplicación.
     */
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_juego2niveln, menu);
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

    /**
     * Método usado para calcular la puntuación de la jugada.
     * Tiene en cuenta el tiempo que quedaba restante para finalizar la partida (a más tiempo, más puntuación),y
     * el número de celdas que el usuario ha acertado.
     *
     * Modifica la variable de clase "puntuación"
     */
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

    /**
     * Método usado para definir nuevos colores de celdas marcadas y de
     */
    public void nuevosColores(){



        /**
         * Para que funcione tienen que existir al menos dos colores en el array xml en colors.xml
         */
        Random rnd = new Random();
        colorMarcado=(int)(rnd.nextDouble() * 3 + 0);
        System.out.println("Color seleccionado "+colorMarcado);

        //this.colorFondo=Color.DKGRAY;
    }

    /**
     * Método para calcular el porcentaje superado
     *
     * @return porcentaje superado del nivel
     */
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
    /**
     * Función para animar el grid al entrar en la actívity
     */
    public void animarGrid() {
        //Cargamos la animación "animacion1" a cada uno de los botones que componen el grid.
        for (int i = 0; i < numFilas * numColumnas; i++)
            botones[i].startAnimation(animacion1);
    }

    /**
     * Método para salir de un nivel al terminar la partida.
     */
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
                        Intent intent = new Intent(Juego1niveln.this, Juego1.class);
                        //Iniciamos la nueva actividad
                        startActivity(intent);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Método que prepara la siguiente jugada
     */
    public void siguienteJugada() {

        nuevosColores();

        /*
        *Dependiendo del estado de nivel se configura el grid de una manera u otra
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
                botones[i].setBackgroundColor(Color.parseColor(colores[colorMarcado]));
            else
                botones[i].setBackgroundColor(getResources().getColor(R.color.darkgray));

        //Ilumina el grid
        animarGrid();

        //Aumentamos el valor de repeticion actual:
        numRepeticionActual++;

        //Calculamos los siguiente colores:

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
                //botones[numBoton].setBackgroundColor(getResources().getColor(R.color.kiwi));
                botones[numBoton].setBackgroundColor(Color.parseColor(colores[colorMarcado]));
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

                    //Aumentamos el número de grids que el jugador a superado.
                    numGridsJugados++;

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
