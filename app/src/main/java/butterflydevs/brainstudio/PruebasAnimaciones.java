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

import java.util.ArrayList;
import java.util.List;


public class PruebasAnimaciones extends ActionBarActivity {

    static Button []botones;
    private int pos=0;
    private int tamSecuencia;
    Thread background;

    List<Integer> secuencia = new ArrayList();



    Animation animacion;



    public PruebasAnimaciones(){

        secuencia.add(0);
        secuencia.add(1);
        secuencia.add(0);
        secuencia.add(0);

        System.out.println("Secuencia: ");
        for(int a: secuencia)
            System.out.print(a);

        tamSecuencia=secuencia.size();


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
            System.out.println("yeah"+msg.what);
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
                botones[i].setBackgroundColor(Color.YELLOW);

                botones[i].startAnimation(animacion);
            }
          //  else
            //    botones[i].setBackgroundColor(Color.DKGRAY);
    }


    public void resetearBotones(){
        for(int i=0; i<botones.length; i++)
            botones[i].setBackgroundColor(Color.DKGRAY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pruebas_animaciones);

        botones = new Button[3];

        botones[0] = (Button)findViewById(R.id.botonA);
        botones[1] = (Button)findViewById(R.id.botonB);
        botones[2] = (Button)findViewById(R.id.botonC);

        animacion = AnimationUtils.loadAnimation(this, R.anim.animacionboton_juego_memo);
        animacion.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                System.out.println("La animación empieza");

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

        botones[0].setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        pos = 0;
                        secuencia.add(2);
                        background = null;
                        inicializarHebra();
                        background.start();
                    }
                }
        );

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


}
