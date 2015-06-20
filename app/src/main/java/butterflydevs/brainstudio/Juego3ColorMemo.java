package butterflydevs.brainstudio;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

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
public class Juego3ColorMemo extends ActionBarActivity {




    //Vector de botones a cargar en el layout de botones.
    private Boton[] buttons;
    //Numero de botones que contendrá el array y el layout.
    private int numButtons;
    //Tamaño (ancho = alto) de estos.
    private int tamButtons;

    //Layout donde irán cargados los botones con los que jugamos.
    private LinearLayout layoutButtons;

    //Los botones del menu inferior
    private Button buttonBack, buttonHelp;

    //Variables puras del juego

        //Vector con la secuencia que el jugador tiene que repetir.
        private int[] secuencia = {1,1,0,1,1};
        //Número máximo de elementos que tendrá el vector, en el caso B será fijo y en el caso A no será necesaria esta variable.
        private int numMaxElementos;

        //Control del juego


        private int a;
        private int b;

    private Handler handler;

    private Runnable r;

    //Animaciones
    Animation animacion1;


    public Juego3ColorMemo(){
        //Numero de botones del grid, a cambiar de forma dinamica mas adelante.
        numButtons=2;

        //Tamaño de los botones:
        tamButtons=300;

        numMaxElementos=5;
       // secuencia = new int [numMaxElementos];

        a=0;
        b=3;

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_juego2_color_memo);
            //Con esta orden conseguimos hacer que no se muestre la ActionBar.
            getSupportActionBar().hide();
            //Con esta hacemos que la barra de estado del teléfono no se vea y la actividad sea a pantalla completa.
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //Asciar animación con fichero que la contiene
        animacion1 = AnimationUtils.loadAnimation(this, R.anim.animacionboton_color_memo_a);

        /**
        *  Tendremos que hacer que la animación de un botón en concreto invoque al acabar la animación de otro botón.
        */

        //Especificamos el comportamiento al empezar y al finalizar
        animacion1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                System.out.println("Animando boton "+secuencia[a]);


            }

            //Especificamos que ocurre cuando la animación1 termina
            @Override
            public void onAnimationEnd(Animation animation) {
                System.out.println("La animacion acaba");
                /**
                 * Cuando la animación acaba tiene que iniciarse la animación del siguiente objeto.
                 */

                /*
                a++;
                 //Cuando este botón deja de animarse se invoca la animación del siguiente mientras se pueda.

                 if(a<=b) {
                     //Estamos animando el botón que corresponde en la secuencia:
                     buttons[secuencia[a]].startAnimation(animacion1);
                 }
                 */

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }


        });



        //Asociación de los elementos del layout a variables para su contol.
        asociarElementosVista();

        //Inicialización de elementos del layout dinámicos
        inicializarElementosDinamicamente();


        buttonBack.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Creamos el Intent
                        Intent intent = new Intent(Juego3ColorMemo.this, Juego1.class);
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
    }

    /**
     * Función que se ejecuta una vez cargada la actividad.
     */
    @Override
    protected void onStart(){
        super.onStart();

        //Generamos la secuencia de juego de los botones.
        generarSecuencia();


        //Bucle de juego

        //El juego se repite sin fallos hasta que se completa la secuncia completa.
        for(int i=0; i<=numMaxElementos; i++){

            //1º Se anima el grid

                //Animar la secuencia hasta el elemento i.
                animarSecuencia(2);

            //2º Se espera la introducción del usuario


        }
        //Fin de bucle del juego

    }
    public void animarSecuencia(int hastaN){




       // Si hacemos esto e intentamos encadenar la misma animación tras ella misma nos da error.

                AnimatorSet s = new AnimatorSet();

                final List<Animator>listaAnimaciones = new ArrayList<Animator>();

                //Una vez cargadas las animaciones de cada botón creamos una secuencia de animación:

                final List<Animator>secuenciaAnimaciones = new ArrayList<Animator>();

                secuenciaAnimaciones.add(buttons[0].animacion);
                secuenciaAnimaciones.add(buttons[1].animacion);
              //  secuenciaAnimaciones.add(buttons[0].animacion);

                //Se la pasamos al animador
                s.playSequentially(secuenciaAnimaciones);

                //Le establecemos un dealy de inicio ( al conjunto de todas las animaciones encadenadas)
                s.setStartDelay(5000);

                //Arrancamos la animación
                s.start();

    }

    /**
     * Esta función genera la secuencia de juego que el usuario tiene que ser capaz de seguir.
     * Se podría pasar a matrixHelper
     */
    public void generarSecuencia(){

        System.out.println("Secuencia: "+secuencia.length+" elementos");
        for(int i=0; i<numMaxElementos; i++)
            System.out.print(secuencia[i]);
        System.out.println("\n");
        /*
        //Necesitamos usar random
        Random rnd = new Random();

        //Mediante un bucle se rellena el vector con elementos de 0 al numero de botones-1 (para incluir el 0)
        for(int i=0; i<numMaxElementos; i++)
            secuencia[i] = rnd.nextInt(numButtons);

        //Depuración
        System.out.println("Secuencia: "+secuencia.length+" elementos");
        for(int i=0; i<numMaxElementos; i++)
            System.out.print(secuencia[i]);
        System.out.println("");
        */
    }

    /**
     * Para comprobar si la secuencia que el usuario ha introducido corresponde con la original
     */
    public void comprobarSecuencia(){

    }


    /**
     * Para asociar los elementos definidos en la vista (xml) con variables para su control aquí.
     */
    public void asociarElementosVista(){

        //El layout de los botones para agregarlos
        layoutButtons=(LinearLayout)findViewById(R.id.layoutBotones);


        //Los botones del menu inferior
        buttonBack=(Button)findViewById(R.id.buttonBack);
        buttonHelp=(Button)findViewById(R.id.buttonHelp);


    }

    /**
     * Para inicializar los elementos de la vista que no se hace de forma estática en el editor visual con el xml sino que hay
     * que hacerlo dinámicamente cuando se ejecute la función dependiendo del estado de algunas variables.
     */
    public void inicializarElementosDinamicamente(){



        layoutButtons.setOrientation(LinearLayout.VERTICAL);



        //Inicializamos el vector de botones para guardar numButtons botones.
            buttons = new Boton[numButtons];

        //.. y cada uno de los objetos que guarda
            for(int i=0; i<numButtons; i++)
                buttons[i] = new Boton();



        //Creamos unos parámetros para los botones

            //Definimos unos parámetros (por ahora solo tamaño) para los botones, usando la var tamButtons.
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(tamButtons, tamButtons);
            params.setMargins(5, 5, 5, 5);


        //Inicializamos los botones


            for (int i = 0; i < numButtons; i++) {

                //Inicializamos cada uno de los elementos del vector
                    buttons[i].boton = new Button(this);

                    //Su color
                    if(i==0)
                        buttons[i].colorActivo =  Color.RED;
                    if(i==1)
                        buttons[i].colorActivo = Color.BLUE;
                    buttons[i].colorFondo = Color.DKGRAY;

                //Aplicamos los parámetros configurados anteriormente
                buttons[i].boton.setLayoutParams(params);
                //Los cambiamos de color
                buttons[i].boton.setBackgroundColor(getResources().getColor(R.color.darkgray));
            }

        for(int i=0; i<numButtons; i++){

            //Se especifica el tipo de animación que tendrá el botón:
            buttons[i].animacion=ObjectAnimator.ofFloat(buttons[i], "alpha", 1f, 1f);
            //Se especifica un dealy de inico.
            buttons[i].animacion.setStartDelay(1000);
            //Se especifica una duración
            buttons[i].animacion.setDuration(2000);
            //Se le añade un listener para que se pueda programar su comportamiento al inicio y al final.
            buttons[i].animacion.addListener(new miAnimador(i));

            // buttons[i].animacion=vectorAnimaciones[i];

            //listaAnimaciones.add(tmp);
        }

        //Añadimos los botones al layout
            for(int i=0; i<numButtons; i++)
                layoutButtons.addView(buttons[i].boton);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_juego2_color_memo, menu);

        //Con esta orden conseguimos hacer que no se muestre la ActionBar.
            getSupportActionBar().hide();
        //Con esta hacemos que la barra de estado del teléfono no se vea y la actividad sea a pantalla completa.
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
     *
     *
     */
    class Boton{

       ObjectAnimator animacion;
       public Button boton;
       public int colorActivo;
       public int colorFondo;

        public Boton(){};

    };


    class miAnimador implements Animator.AnimatorListener{


        int numBoton;

        public miAnimador(int num){
            numBoton=num;
        }

        public void onAnimationCancel(Animator paramAnonymousAnimator) {
        }
        @Override
        public void onAnimationEnd(Animator animator) {
            buttons[numBoton].boton.setBackgroundColor(buttons[numBoton].colorFondo);
        }
        @Override
        public void onAnimationRepeat(Animator animator) {
        }
        @Override
        public void onAnimationStart(Animator animator) {
            buttons[numBoton].boton.setBackgroundColor(buttons[numBoton].colorActivo);
        }
    };

}
