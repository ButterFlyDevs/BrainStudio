package butterflydevs.brainstudio;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterflydevs.brainstudio.extras.matrixHelper;

/**
 * Puede funcionar de dos maneras, tendrems que elegir una:
 * A: el vector crece indefinidamente mientras el jugador pueda ser capaz de seguirlo. Calcular aquí el porcentaje de juego superado
 * es entonces imposible porque el juego no tiene fin.
 * B: el vector tiene un tamaño definido, como 30 elementos, así dependiendo cuantos elementos haya sido cacaz de recordar el jugador
 * podremos calcular el porcenjate pasado del juego.
 *
 * En principio vamos a optar por la opción B.
 *
 */
public class Juego5niveln extends ActionBarActivity {

    //Botones del juego
    static Boton []botones;

    //Colores base
    static String[] coloresBase;

    //Colores enfasis
    static String[] coloresEnfasis;

    //Variables necesarias para construir el grid de forma dinámica:

    //Para referenciar el layout grande donde van todos los layout que componen las filas
    private LinearLayout layoutGridBotones;

    //Filas del grid de botones:
    private LinearLayout[] filasLinearLayout; //Cada fila de botones es un linearLayout


    private int numFilas;
    private int numColumnas;
    private int tamButtons;


    private int maxRandom;

    private Button buttonBack, buttonHelp;

    private int pos=0;

    Thread background;

    Animation animacion;

    List<Integer> secuencia = new ArrayList();

    List<Integer> secuenciaJugador = new ArrayList();




    public Juego5niveln(){


    }
    public void inicializarHebra(){
        //  new prueba().execute();
        background = new Thread(new Runnable() {



            @Override
            public void run() {




                try {

                    Thread.sleep(1500);
                    for (int i = 0; i <= secuencia.size(); i++) {

                        Message message = handler.obtainMessage();
                        message.what = 1;
//                        Thread.sleep(1000);
                        handler.sendMessage(message);
                        Thread.sleep(600);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            //Recibimos un mensaje desde la hebra del tiempo:
            // System.out.println("yeah"+msg.what);
            secuencia();
        }

    };
    //SEcuencia tiene que recorrer toda la secuencia de botones sin tener nada más a su disposicion que una llamada.
    public void secuencia(){
        if(pos!=secuencia.size()) {
            //botones[pos].setBackgroundColor(Color.YELLOW);
            coloreaElegido( secuencia.get(pos) );
            pos++;
            //Al final se apagan todos los botones.
        }else{
            resetearBotones();
        }
    }

    public void coloreaElegido(int elegido){
        for(int i=0; i<botones.length; i++)
            if(i==elegido) {
                botones[i].boton.setBackgroundColor(botones[i].colorEnfasis);

                botones[i].boton.startAnimation(animacion);
            }
        //  else
        //    botones[i].setBackgroundColor(Color.DKGRAY);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego5niveln);
        //Con esta orden conseguimos hacer que no se muestre la ActionBar.
        getSupportActionBar().hide();
        //Con esta hacemos que la barra de estado del teléfono no se vea y la actividad sea a pantalla completa.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //1º Asociación de los elementos del layout a variables para su contol.
        asociarElementosVista();

        //Cargar los colores que vamos a usar:
        coloresBase = getResources().getStringArray(R.array.colores_base);
        coloresEnfasis = getResources().getStringArray(R.array.colores_enfasis);


        //2º Obtenemos los datos que se le pasa a la actividad.

        Intent intent=getIntent();
        //Obtenemos la información del intent que nos evía la actividad que nos crea.
        int level=intent.getIntExtra("nivel",0);

        System.out.println("recibiod de la actividad llamante: "+level);

        // 2.1 Ajustamos el nivel mediante la modificación de algunas variables que luego serviarán para la configuración del resto.
        ajustarNivel(level);



        //3º Configuramos los listener de los botones estáticos del layout:

        buttonBack.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Creamos el Intent
                         Intent intent = new Intent(Juego5niveln.this, Juego5.class);
                        //Iniciamos la nueva actividad
                         startActivity(intent);
                    }
                }
        );

        buttonHelp.setOnClickListener(

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

        //4º Cargamos las animaciones

        animacion = AnimationUtils.loadAnimation(this, R.anim.animacionboton_juego_memo);
        animacion.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //  System.out.println("La animación empieza");

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                resetearBotones();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }


        });

    }

    public void resetearBotones(){
        for(int i=0; i<botones.length; i++)
            botones[i].boton.setBackgroundColor(botones[i].color);
    }

    /**
     * Función de ajuste de nivel del juego. Es la más importante pués según configure las variables se configurará después
     * el aspecto y jugabilidad de todas los grids que se vayan creando. Tambien afecta a la fomra en la que se calcula
     * el porcentaje completado del juego.
     * @param level
     */
    public void ajustarNivel(int level){

        //1º Ajuste de las variables:

        //Ajustes para el nivel 1
        if(level==1){

            Toast.makeText(this, "Level1", Toast.LENGTH_SHORT).show();

            //1º Establecemos el tamaño del grid:
            numFilas=5;
            numColumnas=3;

            //2º Establecemos el tamaño de los botones:
            tamButtons=350;

            maxRandom=numFilas*numColumnas;


        }else
            //Ajustes para el nivel 2
            if(level==2){

                Toast.makeText(this, "Level2", Toast.LENGTH_SHORT).show();

                numFilas=2;
                numColumnas=2;

                tamButtons=250;

                maxRandom=numFilas*numColumnas;

            }else
                //Ajustes para el nivel 3
                if(level==3){

                    Toast.makeText(this, "Level3", Toast.LENGTH_SHORT).show();

                    numFilas=3;
                    numColumnas=2;

                    tamButtons=200;

                    maxRandom=numFilas*numColumnas;
                }


        //2º Inicialización de los elementos:



        //2.1 BOTONES

        //Inicializamos el vector de botones a un número de botones determinado dependiendo del nivel en el que estemos.
        botones = new Boton[numFilas * numColumnas];

        //Inicializamos los botones
        for (int i = 0; i < botones.length; i++) {

            //Inicializamos los objetos.
            botones[i] = new Boton();

            //Inicializamos cada uno de los elementos del vector
            botones[i].boton = new Button(this);
            //Establecemos parametros de layout a cada uno:
            botones[i].boton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(tamButtons, tamButtons);
            params.setMargins(20, 20, 5, 5);
            botones[i].boton.setLayoutParams(params);
            //    botones[i].boton.setBackgroundColor(getResources().getColor(Color.parseColor(coloresBase[i])));

            //Inicializamos los colores de los botones:

            //Ahora mismo solo para dos botones:
            botones[i].color=Color.parseColor(coloresBase[i]);
            botones[i].colorEnfasis=Color.parseColor(coloresEnfasis[i]);

            botones[i].boton.setBackgroundColor(botones[i].color);
        }

        //Asociamos a cada boton un listener.



        for (int i = 0; i<botones.length; i++) {
            System.out.println("Boton: " + i);
            botones[i].boton.setOnClickListener(new MyListener(i));
        }




        //2.2 LAYOUTS

        //Inicializamos el vector de filas (vector de layouts)
        filasLinearLayout = new LinearLayout[numFilas];

        //Inicializamos los LinearLayout (filas)
        for (int i = 0; i < numFilas; i++) {
            filasLinearLayout[i] = new LinearLayout(this);
            filasLinearLayout[i].setOrientation(LinearLayout.HORIZONTAL);
            filasLinearLayout[i].setGravity(Gravity.CENTER);
        }

        //Añadimos todos los layout de filas al layout principal
        for (int i = 0; i < numFilas; i++)
            layoutGridBotones.addView(filasLinearLayout[i]);



        //3º Asociación de los elementos:

        //Añadimos los botones a los layouts.
        int numBoton = 0;
        for (int i = 0; i < numFilas; i++) //i sera el numero de fila
            for (int j = 0; j < numColumnas; j++) {
                filasLinearLayout[i].addView(botones[numBoton].boton);
                numBoton++;
            }







    }

    @Override
    protected void onStart(){
        super.onStart();



        //Añadimos el primer elemento:
        secuencia.add(randInt(0,maxRandom));

        //Inicializamos la hebra:
        inicializarHebra();

        //Arrancamos la hebra:
        background.start();


    }



    public void asociarElementosVista(){

        System.out.println("Asociando elementos de la vista");

        //Los botones del menu inferior
        buttonBack = (Button) findViewById(R.id.botonBack);
        buttonHelp = (Button) findViewById(R.id.botonHelp);

        //Asociamos el layout principal
        layoutGridBotones = (LinearLayout) findViewById(R.id.gridBotones);
        //Le especificamos una horientación
        layoutGridBotones.setOrientation(LinearLayout.VERTICAL);

    }

    public boolean secuenciaCorrecta(){

        boolean salida=true;

        //Imprimimos la secuencia del juego:
        System.out.println("Secuencia del juego:");
        for(int a: secuencia)
            System.out.print(a + " ");
        System.out.println("");

        //Imprimimos la secuencia del juego:
        System.out.println("Secuencia del usuario:");
        for(int a: secuenciaJugador)
            System.out.print(a+" ");
        System.out.println("");


        for(int i=0; i<secuenciaJugador.size(); i++) {
            if (secuenciaJugador.get(i) != secuencia.get(i))
                return false;
        }

        if(secuenciaJugador.size()==secuencia.size()) {
            avance();
            //Reiniciamos la secuencia del Jugador
            secuenciaJugador.clear();
            //Acemos que vibre:
            Vibrator v = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);

            // Vibrar durante 3 segundos
            v.vibrate(200);
        }



        return salida;
    }

    public void avance(){
        System.out.println("Avance");
        secuencia.add(randInt(0,maxRandom));
        reiniciaSecuencia();
    }

    public static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();


        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public void finPartida(){
        Toast.makeText(this, "FIN DE PARTIDA", Toast.LENGTH_SHORT).show();
        System.out.println("Se acabo la partida");
    }

    public void reiniciaSecuencia(){
        pos = 0;
        background = null;
        inicializarHebra();
        background.start();
    }

    /**
     * Clase propia boton.
     * Simplifica la abstracción del concepto de boton junto a sus colores.
     */
    class Boton{

        public Button boton;
        public int color;
        public int colorEnfasis;

    };

    class MyListener implements Button.OnClickListener {

        private int numBoton;

        public MyListener(int numBoton) {
            this.numBoton = numBoton;
        }


        @Override
        public void onClick(View v) {

            botones[numBoton].boton.setBackgroundColor(botones[numBoton].colorEnfasis);

            botones[numBoton].boton.startAnimation(animacion);

            secuenciaJugador.add(numBoton);

            if(secuenciaCorrecta())
                System.out.println("Correcto");
            else
                finPartida();

        }

    }

}
