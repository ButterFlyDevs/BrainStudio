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
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.facebook.AppEventsLogger;
import com.github.premnirmal.textcounter.CounterView;
import com.github.premnirmal.textcounter.Formatter;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

import butterflydevs.brainstudio.extras.Dialogos.DialogoSalidaJuegos;
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


    private int progress2 = 100;

    private Button botonBack;
    private Button botonHelp;

    private boolean puedeMostrarBarra=true;


    private int[] coloresElegidos;
    private int colorAnterior=-1;
    private int botonAnterior=-1;

    private int valorAnterior=-1;

    private int sector=1;


    //Matrices usadas en el juego:

        //Matriz aleatoria con las parejas dentro.
        private int matrizParejas[];


    private int numCeldasActivadas = 0;

    private int numRepeticionActual;
    private int numRepeticionesMaximas;
    private int numMaximoCeldas;

    private float puntuacion;
    private int puntos=0;

    private int aciertos=0;

    private int fallosConsecutivos=0;
    private int maxFallosConsecutivos=5;

    private int numGridsJugados;

    private int numCeldas = 2;



    private CounterView counterView;

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
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }


        });


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
                        Intent intent = new Intent(Juego4niveln.this, Help.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("Zona_llamada","Juego");
                        bundle.putInt("Numero_zona", 4);
                        //Introducimos la informacion en el intent para enviarsela a la actívity.
                        intent.putExtras(bundle);
                        startActivityForResult(intent, 1);
                    }
                }
        );

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
            tamButtons = 140;

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




    public void asociarElementosVista(){

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




    public int calculaPorcentaje(){

        int resultado;


        //Una regla de tres simple
        resultado=(int)(100*aciertos)/120;



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
        db.addJugada(new Jugada((int) puntos, calculaPorcentaje()), level,4);
    }

    public void finalizarPartida(){

        //Grabar en la base de datos
        grabarDatosBD();

        //Mostrar mensaje de fin:
        mensajeFin();



    }

    public void mensajeFin() {

        //Informamos de ello:
        DialogoSalidaJuegos dialogoCambioSector = new DialogoSalidaJuegos();
        dialogoCambioSector.setComportamientoBoton(DialogoSalidaJuegos.ComportamientoBoton.SALIR);
        dialogoCambioSector.setNivel(4);

        //dialogoCambioSector.setPadre(this);

        dialogoCambioSector.setDatos("Ohh!", "puntos: "+puntos, "Completado:",calculaPorcentaje());

        //Mostramos el diálogo
        dialogoCambioSector.show(getFragmentManager(), "");


    }

    /**
     * Función para reiniciar de nuevo el grid
     */
    public void reiniciarGrid(){

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



        //Pintamos todos los botones grises
        for (int i = 0; i < numFilas * numColumnas; i++)
            botones[i].setBackgroundColor(getResources().getColor(R.color.darkgray));

        if(sector==2 || sector==3 || sector==4 || sector==5){
            for(int i=0; i<numFilas*numColumnas; i++)
                botones[i].setText("");
        }

        //Ponemos toda la matriz de acertados a false
        for(int i=0; i<numFilas*numColumnas; i++)
            acertados[i]=false;



    }


    /**
     * Función que informa que has concluido el juego.
     */
    public void finJuego(){
        //Informamos de ello:
        DialogoSalidaJuegos dialogoFinJuego = new DialogoSalidaJuegos();
        dialogoFinJuego.setComportamientoBoton(DialogoSalidaJuegos.ComportamientoBoton.SALIR);
        dialogoFinJuego.setNivel(5);


        //Mostramos el diálogo
        dialogoFinJuego.show(getFragmentManager(), "");

        //Grabar datos de partida!

        MySQLiteHelper db = new MySQLiteHelper(this);

        db.addJugada(new Jugada((int) puntos, calculaPorcentaje()), level,5);
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

    /**
     * Función que modifica las variables necesarias para que la proxima construcción sea correcta.
     */
    public void finPartida(){
            Toast.makeText(getApplicationContext(), "FIN DE GRID "+this.numRepeticionActual, Toast.LENGTH_SHORT).show();

            //Si se han realizado todas las repeticiones de un sector
            if(numRepeticionActual==numRepeticionesMaximas) {
                //Se aumenta de sector
                sector++;
                //Damos un plus de puntos por pasar de sector.
                aumentarPuntos(true);
                System.out.println("pasando al sector: "+sector);
                //Se reinicia la variable numRepeticionActual
                numRepeticionActual=0;
            }

            //Modificación de variables:
            this.numRepeticionActual++;



            reiniciarGrid();
    }

    /**
     * Función que aumenta el nivel de puntos del jugador.
     * @param extra Indica si se trata de un bono por pasar de sector.
     */
    public void aumentarPuntos(boolean extra){



        counterView.setStartValue(puntos);


        //Si es un bono por pasar de sector se aumenta 100 puntos:
        if(extra){
            puntos+=100;
        //Si son aciertos normales se aumenta dependiendo del punto en el que se encuentre:
        }else {

            //Se reestablece el numero de fallos
            fallosConsecutivos=0;

            //Se aumenta la puntuación
            puntos+=10*sector;
            //Contabilizamos un acierto:
            aciertos++;
        }
        Toast.makeText(getApplicationContext(), "Puntos: "+puntos, Toast.LENGTH_SHORT).show();

        counterView.setPrefix("");
        counterView.setSuffix("");


        counterView.setEndValue(puntos);
        counterView.start();
    }

    public void reducirPuntos(){

        fallosConsecutivos++;

        if(fallosConsecutivos==maxFallosConsecutivos)
            finalizarPartida();

        counterView.setStartValue(puntos);

        //Se restan dos puntos por el numero de sector cada vez que se falle.
        puntos-=2*(sector);

        counterView.setPrefix("");
        counterView.setSuffix("");


        counterView.setEndValue(puntos);
        counterView.start();

        Toast.makeText(getApplicationContext(), "Puntos: "+puntos, Toast.LENGTH_SHORT).show();
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

            /*
            Comportamiento del click para el sector 1:
             */
            if(sector==1){
                if (acertados[numBoton] == false) {
                    //Cambiamos el color del boton según la matriz parejas.

                    if(sector==1)
                        botones[numBoton].setBackgroundColor(Color.parseColor(colores[coloresElegidos[matrizParejas[numBoton]]]));
                    if (botonAnterior == -1)
                        botonAnterior = numBoton;

                    //Si es el primer boton pulsado de la pareja se guarda el color.
                    if (colorAnterior == -1) {
                        if(sector==1)
                            colorAnterior = Color.parseColor(colores[coloresElegidos[matrizParejas[numBoton]]]);



                       // Toast.makeText(getApplicationContext(), "Guardado color" + colorAnterior, Toast.LENGTH_SHORT).show();

                        //Si es el segundo boton se compara con el primero
                    } else {
                        //Si HQY COINCIDENCIA

                        //Control de pulsación sobre el mismo boton
                        if (botonAnterior != numBoton){

                            //Comprobación estandar
                            if (colorAnterior == Color.parseColor(colores[coloresElegidos[matrizParejas[numBoton]]])) {
                                //   Toast.makeText(getApplicationContext(), "MISMO COLOR", Toast.LENGTH_SHORT).show();
                                acertados[numBoton] = true;
                                acertados[botonAnterior] = true;

                                //Se reinician los parámetros para una nueva jugada
                                colorAnterior = -1;
                                botonAnterior = -1;

                                //Aumentamos puntos sin tratarse de un bono
                                aumentarPuntos(false);

                                if (matrizCompleta())
                                    finPartida();


                            //Si no hay coincidencia se oscurecen ambos botones y se reinician los punteros.
                            } else {


                                //Como se trata de un error del jugador le restamos puntos:
                                reducirPuntos();

                                //   Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();

                                System.out.println("Botones: " + botonAnterior + " " + numBoton);

                                botones[numBoton].setBackgroundColor(getResources().getColor(R.color.darkgray));
                                botones[botonAnterior].setBackgroundColor(getResources().getColor(R.color.darkgray));

                                colorAnterior = -1;
                                botonAnterior = -1;
                            }

                    }
                    }
                }




        }else //Fin sector 1
        if(sector==2 || sector==3 || sector==4 || sector==5 ){
            if (acertados[numBoton] == false) {

                    //El color de fondo del boton es aleatorio:
                    botones[numBoton].setBackgroundColor(Color.parseColor(colores[matrixHelper.randInt(0,colores.length-1)]));
                    botones[numBoton].setTextSize(70);

                    //Introducimos numeros
                    if(sector==2)
                        botones[numBoton].setText(Integer.toString(matrizParejas[numBoton]+1));
                    //Introducimos letras
                    if(sector==3)
                        botones[numBoton].setText(matrixHelper.letraEquivalente(matrizParejas[numBoton]+1));
                    //Introducimos numeros y letras (siempre usamos matrizParejas[numBoton], ese int como base.
                    if(sector==4)
                        if(0==matrixHelper.randInt(0, 1))
                            botones[numBoton].setText(Integer.toString(matrizParejas[numBoton]+1));
                        else
                            botones[numBoton].setText(matrixHelper.letraEquivalente(matrizParejas[numBoton] + 1));
                    if(sector==5) {

                        /**
                         * En el sector 5 puede que se coloque una letra, un número o un número romano,
                         * siempre significando el mismo valor decimal.
                         */

                        int rand=matrixHelper.randInt(1,3);

                        if (rand==1) //Se colocal el número decimal
                            botones[numBoton].setText(Integer.toString(matrizParejas[numBoton] + 1));

                        if(rand==2) //Se coloca la letra equivalente al decimal
                            botones[numBoton].setText(matrixHelper.letraEquivalente(matrizParejas[numBoton] + 1));

                        if(rand==3) { //Se coloca el número romano equivalente al decimal.
                            botones[numBoton].setText(matrixHelper.numeroRomanoEquivalente(matrizParejas[numBoton] + 1));
                            botones[numBoton].setTextSize(30); //Se reduce un poco el tamaño.
                        }

                    }




                    botones[numBoton].setTextSize(70);

                if (botonAnterior == -1)
                    botonAnterior = numBoton;

                //Si es el primer boton pulsado de la pareja se guarda el color.
                if (valorAnterior == -1) {
                        valorAnterior=matrizParejas[numBoton];
                    //Si es el segundo boton se compara con el primero
                } else {


                    //Control de pulsación sobre el mismo boton
                    if (botonAnterior != numBoton) {

                        //Si hay coincidencia se avisa
                        if (valorAnterior == matrizParejas[numBoton]) {
                            Toast.makeText(getApplicationContext(), "MISMO VALOR", Toast.LENGTH_SHORT).show();
                            acertados[numBoton] = true;
                            acertados[botonAnterior] = true;

                            //Se reinician los parámetros para una nueva jugada
                            valorAnterior = -1;
                            botonAnterior = -1;

                            //Aumentamos puntos sin tratarse de un bono
                            aumentarPuntos(false);

                            if (matrizCompleta())
                                finPartida();


                            //Si no hay coincidencia se elimina el valor de los dos botones y se reinician los punteros.
                        } else {

                            //Como se trata de un error del jugador le restamos puntos:
                            reducirPuntos();
                            //   Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();

                            System.out.println("Botones: " + botonAnterior + " " + numBoton);

                            botones[numBoton].setText("");
                            botones[botonAnterior].setText("");

                            valorAnterior = -1;
                            botonAnterior = -1;
                        }
                    }
                }
            }


        }else if(sector>5){
            finJuego();
        }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
}
