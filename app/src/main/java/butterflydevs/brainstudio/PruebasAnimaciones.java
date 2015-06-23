package butterflydevs.brainstudio;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PruebasAnimaciones extends ActionBarActivity {

    static Boton []botones;

    static int[] colores;


    private int pos=0;

    Thread background;

    List<Integer> secuencia = new ArrayList();

    List<Integer> secuenciaJugador = new ArrayList();

    Animation animacion;



    public PruebasAnimaciones(){

        secuencia.add(randInt(0,2));

        inicializarHebra();





    }
    public void inicializarHebra(){
        //  new prueba().execute();
        background = new Thread(new Runnable() {



            @Override
            public void run() {




                try {

                    Thread.sleep(1000);
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
    public static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();


        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
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


    public void resetearBotones(){
        for(int i=0; i<botones.length; i++)
            botones[i].boton.setBackgroundColor(botones[i].color);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pruebas_animaciones);

        botones = new Boton[3];


        botones[0] = new Boton();
        botones[1] = new Boton();
        botones[2] = new Boton();


        //Boton verde
        botones[0].boton = (Button)findViewById(R.id.botonA);
        botones[0].color=Color.rgb(13,127,0);
        botones[0].colorEnfasis=Color.rgb(26,255,0);

        //Boton rojo
        botones[1].boton = (Button)findViewById(R.id.botonB);
        botones[1].color=Color.rgb(127,38,38);
        botones[1].colorEnfasis=Color.rgb(255,0,0);

        //BOton azul
        botones[2].boton = (Button)findViewById(R.id.botonC);
        botones[2].color=Color.rgb(4,118,217);
        botones[2].colorEnfasis=Color.rgb(115,174,255);

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


        resetearBotones();



        botones[0].boton.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        botones[0].boton.setBackgroundColor(botones[0].colorEnfasis);

                        botones[0].boton.startAnimation(animacion);

                        secuenciaJugador.add(0);

                        if(secuenciaCorrecta())
                            System.out.println("Correcto");
                        else
                            finPartida();
                    }
                }
        );
        botones[1].boton.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        botones[1].boton.setBackgroundColor(botones[1].colorEnfasis);

                        botones[1].boton.startAnimation(animacion);

                        secuenciaJugador.add(1);

                        if(secuenciaCorrecta())
                            System.out.println("Correcto");
                        else
                            finPartida();
                    }
                }
        );
        botones[2].boton.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        botones[2].boton.setBackgroundColor(botones[2].colorEnfasis);

                        botones[2].boton.startAnimation(animacion);

                        secuenciaJugador.add(2);

                        if(secuenciaCorrecta())
                            System.out.println("Correcto");
                        else
                            finPartida();
                    }
                }
        );







    }

    public void avance(){
        System.out.println("Avance");
        secuencia.add(randInt(0,2));
        reiniciaSecuencia();
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
        }



        return salida;
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

    @Override
    protected void onStart(){
        super.onStart();


        background.start();



    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pruebas_animaciones, menu);






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


    class Boton{

        public Button boton;
        public int color;
        public int colorEnfasis;

    };

}
