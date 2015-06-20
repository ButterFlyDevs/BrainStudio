package butterflydevs.brainstudio;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterflydevs.brainstudio.extras.Jugada;
import butterflydevs.brainstudio.extras.MySQLiteHelper;


public class Juego2 extends ActionBarActivity {


    private CircularCounter meterA, meterB, meterC;
    private Handler handler;
    private Runnable r;
    private String[] colors;

    private Jugada maxJugadaNivel1;
    private Jugada maxJugadaNivel2;
    private Jugada maxJugadaNivel3;

    private int porcentajePuntosNivel1;
    private int porcentajePuntosNivel2;
    private int porcentajePuntosNivel3;

    private Button botonBack;
    private Button botonHelp;

    private TextView puntosNivel1;
    private TextView puntosNivel2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_1);

        //Con esta orden conseguimos hacer que no se muestre la ActionBar.
        getSupportActionBar().hide();
        //Con esta hacemos que la barra de estado del tel�fono no se vea y la actividad sea a pantalla completa.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        botonBack=(Button)findViewById(R.id.botonBack);
        botonHelp=(Button)findViewById(R.id.botonHelp);
        puntosNivel1=(TextView)findViewById(R.id.puntosNivel1);
        puntosNivel2=(TextView)findViewById(R.id.puntosNivel2);

        //Lo primero que hacemos es recuperar los datos que necesitamos
        MySQLiteHelper db = new MySQLiteHelper(this);

        //Obtener todas la jugadas
        List<Jugada> jugadasNivel1=db.getAllJugadas(1);
        List<Jugada> jugadasNivel2=db.getAllJugadas(2);

        System.out.println("datos"+jugadasNivel1.size()+jugadasNivel1.toString());

        maxJugadaNivel1=Jugada.obtenMaximaJugada(jugadasNivel1);
        puntosNivel1.setText(Integer.toString(maxJugadaNivel1.getPuntuacion())+" puntos");

        porcentajePuntosNivel1=calculaPorcentaje(1, maxJugadaNivel1.getPuntuacion());

        maxJugadaNivel2=Jugada.obtenMaximaJugada(jugadasNivel2);
        puntosNivel2.setText(Integer.toString(maxJugadaNivel2.getPuntuacion())+" puntos");

        porcentajePuntosNivel2=calculaPorcentaje(2, maxJugadaNivel2.getPuntuacion());

        System.out.println("max:"+maxJugadaNivel1.toString());




        /**
         * Aqu� podemos hacer dos cosas:
         * A: procesar la lista nosotros para sacar lo que queremos (en este caso la jugada de mayor puntuaci�n del usuario)
         * B: hacer un consulta especial que nos la devuelva
         */




        colors = getResources().getStringArray(R.array.colors);

        meterA=(CircularCounter)findViewById(R.id.meter1);
        meterB=(CircularCounter)findViewById(R.id.meter2);
        meterC=(CircularCounter)findViewById(R.id.meter3);


        meterA.setFirstWidth(getResources().getDimension(R.dimen.first))
                .setFirstColor(Color.parseColor(colors[0]))

                .setSecondWidth(getResources().getDimension(R.dimen.first))
                .setSecondColor(Color.parseColor(colors[1]))
                        //.setThirdWidth(getResources().getDimension(R.dimen.third))
                        //.setThirdColor(Color.parseColor(colors[2]))
                .setBackgroundColor(Color.TRANSPARENT);
        //.setBackgroundColor(-14606047);

        meterA.setMetricText("");
        //meter.setMetricSize(30.f);
        meterA.setRange(100);
        meterA.setTextColor(Color.GRAY);
        meterA.setTextSize(30.f);


        meterB.setFirstWidth(getResources().getDimension(R.dimen.first))
                .setFirstColor(Color.parseColor(colors[0]))
                .setSecondWidth(getResources().getDimension(R.dimen.first))
                .setSecondColor(Color.parseColor(colors[1]))

                        //.setSecondWidth(getResources().getDimension(R.dimen.second))
                        //.setSecondColor(Color.parseColor(colors[1]))
                        //.setThirdWidth(getResources().getDimension(R.dimen.third))
                        //.setThirdColor(Color.parseColor(colors[2]))
                .setBackgroundColor(Color.TRANSPARENT);
        //.setBackgroundColor(-14606047);

        meterB.setMetricText("");
        //meter.setMetricSize(30.f);
        meterB.setRange(100);
        meterB.setTextColor(Color.GRAY);
        meterB.setTextSize(30.f);

        meterC.setFirstWidth(getResources().getDimension(R.dimen.first))

                .setFirstColor(Color.parseColor(colors[0]))

                        //.setSecondWidth(getResources().getDimension(R.dimen.second))
                        //.setSecondColor(Color.parseColor(colors[1]))
                        //.setThirdWidth(getResources().getDimension(R.dimen.third))
                        //.setThirdColor(Color.parseColor(colors[2]))
                .setBackgroundColor(Color.TRANSPARENT);
        //.setBackgroundColor(-14606047);

        meterC.setMetricText("");
        //meter.setMetricSize(30.f);
        meterC.setRange(100);
        meterC.setTextColor(Color.GRAY);
        meterC.setTextSize(40.f);

        handler = new Handler();

        r = new Runnable(){

            int level1 = 0;
            int level11=0;
            int level2 =0;
            int level22=0;

            boolean go = true;

            public void run(){

                if(level1<maxJugadaNivel1.getPorcentaje())
                    level1++;
                if(level11<porcentajePuntosNivel1)
                    level11++;
                if(level22<porcentajePuntosNivel2)
                    level22++;
                if(level2<maxJugadaNivel2.getPorcentaje())
                    level2++;

                meterA.setValues(level1, level11, 0);
                meterB.setValues(level2, level22, 0);


                //meterC.setValues(currV, 0, 0);
                //  meter2.setValues(currV, currV*2, currV*3);

                handler.postDelayed(this, 30);
            }
        };

        meterA.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Creamos el Intent

                        Intent intent = new Intent(Juego2.this, Juego2niveln.class);


                        Bundle bundle = new Bundle();
                        bundle.putInt("nivel",1);

                        //Introducimos la informacion en el intent para enviarsela a la act�vity.
                        intent.putExtras(bundle);

                        //Iniciamos la nueva actividad
                        startActivity(intent);
                    }
                }
        );

        meterB.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Creamos el Intent

                        Intent intent = new Intent(Juego2.this, Juego2niveln.class);


                        Bundle bundle = new Bundle();
                        bundle.putInt("nivel",2);

                        //Introducimos la informacion en el intent para enviarsela a la act�vity.
                        intent.putExtras(bundle);
                        //Iniciamos la nueva actividad
                        startActivity(intent);
                    }
                }
        );
        meterC.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Creamos el Intent

                        Intent intent = new Intent(Juego2.this, Juego2niveln.class);


                        Bundle bundle = new Bundle();
                        bundle.putInt("nivel",3);

                        //Introducimos la informacion en el intent para enviarsela a la act�vity.
                        intent.putExtras(bundle);
                        //Iniciamos la nueva actividad
                        startActivity(intent);
                    }
                }
        );



        botonBack.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Creamos el Intent
                        Intent intent = new Intent(Juego2.this, juegos.class);
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

    public int calculaPorcentaje(int nivel, int puntuacion){

        /*
        Tanto el 1200 como el 4620 son las puntuaciones m�ximas que se podr�an sacar en ambos niveles
        si se recorrieran todos los grid generados sin tardar nada en resolverlos. EL jugador nunca podr� conseguir
        tanta puntuaci�n pero obviamente podr� acercarse si es muy r�pido contestando y no se equivoca.
         */

        int resultado=0;
        if(nivel==1)
            resultado=(100*puntuacion)/1200;
        if(nivel==2)
            resultado=(100*puntuacion)/4620;
        return resultado;
    }


    /**
     * Sobrecarga para el control de los botones f�sicos del terminal.
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){

        //Si pulsamos el bot�n back nos devuelve a la pantalla principal!.
        if(keyCode==KeyEvent.KEYCODE_BACK){

            Intent intent = new Intent(Juego2.this, ActividadPrincipal.class);
            startActivity(intent);

            //Aplicacion de transicion animada entre activities:
            //overridePendingTransition(R.anim.entrada_abajo2, R.anim.salida_abajo2);

            return true;
        }

        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onResume(){
        super.onResume();
        handler.postDelayed(r, 500);
    }

    @Override
    protected void onStart(){
        super.onStart();



    }

    @Override
    protected void onPause(){
        super.onPause();
        handler.removeCallbacks(r);
    }

}
