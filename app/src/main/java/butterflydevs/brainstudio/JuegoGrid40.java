package butterflydevs.brainstudio;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;


public class JuegoGrid40 extends ActionBarActivity {

    final private int numFilas=8;
    final private int numColumnas=5;


    private int tamButtons=90;


    private LinearLayout layoutGridBotones;

    //Filas del grid de botones:
    private LinearLayout [] filasLinearLayout; //Cada fila de botones es un linearLayout

    //Vector de botones (solo uno, no habra un vector por fila, para el procesamiento posterior es mejor tenerlos todos en un solo vector)
    private Button [] botones;


    private Button prueba;


    Animation animacion1, animacion2;

    public JuegoGrid40(){



    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_grid40);

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
                for(int i=0; i<numFilas*numColumnas; i++)
                    botones[i].setBackgroundColor(Color.TRANSPARENT);

                //Cuando la animacion 1 acaba se encarga de lanzar la animacion 2
                for(int i=0; i<numFilas*numColumnas; i++)
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
                System.out.println("La animación empieza");for(int i=0; i<numFilas*numColumnas; i++)
                    botones[i].setBackgroundColor(Color.BLACK);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                System.out.println("La animacion acaba");
                //Cuando la segunda animación termina el tiempo comienza a correr.
              //  countDownTimer.start();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }


        });




        //Inicializamos el vector de filas.
        filasLinearLayout=new LinearLayout[numFilas];

        //Inicializamos el vector de botones:
        botones = new Button[numFilas*numColumnas];


        //Inicializamos los botones
        for(int i=0; i<numFilas*numColumnas; i++) {
            //Inicializamos cada uno de los elementos del vector
            botones[i] = new Button(this);
            //Establecemos parametros de layout a cada uno:
            botones[i].setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(tamButtons,tamButtons);
            params.setMargins(5,5,5,5);
            botones[i].setLayoutParams(params);
            botones[i].setBackgroundColor(Color.DKGRAY);
        }

        //Inicializamos los LinearLayout (filas)
        for(int i=0; i<numFilas; i++) {
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
        layoutGridBotones=(LinearLayout)findViewById(R.id.gridBotones);
        //Le especificamos una horientación
        layoutGridBotones.setOrientation(LinearLayout.VERTICAL);


        //Añadimos todos los layout de filas al layout principal
        for(int i=0; i<numFilas; i++)
            layoutGridBotones.addView(filasLinearLayout[i]);


        //Añadimos un boto de prueba
        //filasLinearLayout[0].addView(botones[0]);
        //filasLinearLayout[1].addView(botones[1]);

        //filasLinearLayout es un vector de filas!

        int numBoton=0;
        for(int i=0; i<numFilas; i++) //i sera el numero de fila
            for(int j=0; j<numColumnas; j++) {
                filasLinearLayout[i].addView(botones[numBoton]); //no usamos numColumnas porque es 5 y tendriamos que reajustar
                numBoton++;
            }




    }

    /**
     * Función para animar el grid al entrar en la actívity
     */
    public void animarGrid(){
        //Cargamos la animación del botón:
        for(int i=0; i<numFilas*numColumnas; i++)
            botones[i].startAnimation(animacion1);
    }


    @Override
    protected void onStart(){
        super.onStart();


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
}
