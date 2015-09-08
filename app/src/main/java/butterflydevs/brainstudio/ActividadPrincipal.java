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

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.facebook.AppEventsLogger;

import net.frakbot.jumpingbeans.JumpingBeans;

import java.util.List;

import butterflydevs.brainstudio.extras.ConexionServidor;
import butterflydevs.brainstudio.extras.Dialogos.DialogoRanking;
import butterflydevs.brainstudio.extras.Jugador;
import butterflydevs.brainstudio.extras.Medalla;
import butterflydevs.brainstudio.extras.Dialogos.MyCustomDialog;
import butterflydevs.brainstudio.extras.MySQLiteHelper;

/**
 * Actividad principal donde se encuentra el meter del estado general, la lista de las medallas obtenidas,
 * los puntos acumulaods y la posición general en el ranking.
 * Además de esto proporciona el menú principal que da acceso al ranking de jugadores mundial,
 * al botón de compartir y al botón de jugar.
 */
public class ActividadPrincipal extends Activity {

    private CircularCounter meter, meter2;

    private String[] colors;

    private Handler handler;

    private Runnable r;

    private Button botonBrain, buttonRanking, buttonShare;

    private TextView customFont, customFont2;

    private TextView textPuntos, textPOS;
    private int porcentajeGeneral=0;

    private JumpingBeans jumpingBeans1;

    private List<Medalla> medallas;
    private int tamMedallas=130;

    private LinearLayout layoutMedallas;

    private ConexionServidor miConexion = new ConexionServidor();

    private int puntuacionGeneral;
    private int posicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_principal);

        //Con esta orden conseguimos hacer que no se muestre la ActionBar.
        //getSupportActionBar().hide();
        //Con esta hacemos que la barra de estado del teléfono no se vea y la actividad sea a pantalla completa.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

       // this.runFragment();
        /*
        synchronized(this) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        */



        Animation loopParpadeante = AnimationUtils.loadAnimation(this, R.anim.animacionbotonplay);

        textPuntos=(TextView)findViewById(R.id.textPuntos);
        textPOS=(TextView)findViewById(R.id.textPos);


        //Introducimos en textPos la posicion que tiene el jugador en el ranking:
        MySQLiteHelper db = new MySQLiteHelper(this);

        puntuacionGeneral=db.calcularPuntuacionGeneral();


        //Si el dispositivo tiene red.
        if(hayRed()) {
            //Se calcula la posición que el jugador tendría en el ranking mundial.
            posicion = calcularPosicion(puntuacionGeneral);
            //Se graba dicha posición en el TextView
            textPOS.setText(posicion + "º en el mundo.");
            textPOS.setTextColor(Color.BLACK);
            textPOS.setTextSize(25);
            // Hacemos que el texto de la posición salte.
            jumpingBeans1 = JumpingBeans.with(textPOS)
                .makeTextJump(0, textPOS.getText().toString().indexOf(' '))
                .setIsWave(false)
                .setLoopDuration(2000)
                .build();

        //Si no es así, se graba un mensaje advirtiéndolo.
        }else {
            textPOS.setText("Sin conexión de red.");
            textPOS.setTextColor(Color.RED);
            textPOS.setTextSize(15);
        }




        colors = getResources().getStringArray(R.array.colors);
        botonBrain = (Button)findViewById(R.id.buttonGAme);
        buttonRanking=(Button)findViewById(R.id.buttonUser);
        buttonShare = (Button) findViewById(R.id.buttonShare);
        layoutMedallas=(LinearLayout)findViewById(R.id.layoutMedallas);


        botonBrain.startAnimation(loopParpadeante);


        botonBrain.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Creamos el Intent
                        Intent intent = new Intent(ActividadPrincipal.this, juegos.class);
                        //Iniciamos la nueva actividad
                        startActivity(intent);
                    }
                }
        );
        buttonRanking.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(hayRed()) {

                            Intent intent = new Intent(ActividadPrincipal.this, Ranking.class);

                            //    Bundle bundle = new Bundle();
                            //    bundle.putInt("nivel",3);

                            //Introducimos la informacion en el intent para enviarsela a la actívity.
                            //    intent.putExtras(bundle);

                            //Iniciamos la nueva actividad
                            startActivity(intent);
                        }else{

                            //Creamos el dialogo:
                            DialogoRanking dialogoRanking = new DialogoRanking();


                            dialogoRanking.show(getFragmentManager(), "");

                        }
                    }
                }
        );

        buttonShare.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActividadPrincipal.this,PruebasFacebook.class);
                        startActivity(intent);
                    }
                }
        );


        meter = (CircularCounter) findViewById(R.id.meter);


        meter.setFirstWidth(getResources().getDimension(R.dimen.first))
                .setFirstColor(Color.parseColor(colors[0]))

                        //.setSecondWidth(getResources().getDimension(R.dimen.second))
                        //.setSecondColor(Color.parseColor(colors[1]))
                        //.setThirdWidth(getResources().getDimension(R.dimen.third))
                        //.setThirdColor(Color.parseColor(colors[2]))
                .setBackgroundColor(Color.TRANSPARENT);
        //.setBackgroundColor(-14606047);

        meter.setMetricText("");
        //meter.setMetricSize(30.f);
        meter.setRange(100);
        meter.setTextColor(Color.GRAY);
        meter.setTextSize(40.f);

        customFont = (TextView)findViewById(R.id.textView);
        customFont2 = (TextView)findViewById(R.id.textPuntos);

        //Cargamos una fuente que tenemos almacenada en el directorio princiapl assets (bienes, activs)
        Typeface font = Typeface.createFromAsset(getAssets(), "gloriahallelujah.ttf");

        //Aplicamos esa fuente a los TextView de los puntos y del titulo principal
        customFont.setTypeface(font);
        customFont2.setTypeface(font);
        meter.setTypeface(font);




        customFont2.setText(Integer.toString(puntuacionGeneral)+" pts");

        porcentajeGeneral=db.calcularPorcentajeGeneral();


        //Comprueba medallas del juego 1, nivel 1
        db.getMedallas();
        medallas = db.getMedallas();
        System.out.println("Recibidas "+medallas.size()+" medallas.");

        //Introducimos las imagenes correspondientes a las medallas cargadas en la lista:

                for(Medalla medallita: medallas){

                    ImageView medalla = new ImageView(this);


                    //Añadimos la imagen que diga la medalla
                    if(medallita.juego==1) {
                        if (medallita.nivel == 1)
                            medalla.setImageResource(R.drawable.bronce);
                        if (medallita.nivel == 2)
                            medalla.setImageResource(R.drawable.plata);
                        if (medallita.nivel == 3)
                            medalla.setImageResource(R.drawable.oro);
                    }
                    if(medallita.juego==2) {
                        if (medallita.nivel == 1)
                            medalla.setImageResource(R.drawable.bronce_juego2);
                        if (medallita.nivel == 2)
                            medalla.setImageResource(R.drawable.plata_juego2);
                        if (medallita.nivel == 3)
                            medalla.setImageResource(R.drawable.oro_juego2);
                    }
                    if(medallita.juego==3) {
                        if (medallita.nivel == 1)
                            medalla.setImageResource(R.drawable.juego33);
                        if (medallita.nivel == 2)
                            medalla.setImageResource(R.drawable.juego32);
                        if (medallita.nivel == 3)
                            medalla.setImageResource(R.drawable.juego31);
                    }
                    if(medallita.juego==4) {
                        if (medallita.nivel == 1)
                            medalla.setImageResource(R.drawable.juego43);
                        if (medallita.nivel == 2)
                            medalla.setImageResource(R.drawable.juego42);
                        if (medallita.nivel == 3)
                            medalla.setImageResource(R.drawable.juego41);
                    }
                    if(medallita.juego==5) {
                        if (medallita.nivel == 1)
                            medalla.setImageResource(R.drawable.juego53);
                        if (medallita.nivel == 2)
                            medalla.setImageResource(R.drawable.juego52);
                        if (medallita.nivel == 3)
                            medalla.setImageResource(R.drawable.juego51);
                    }



                    //Creamos unos parámetros para su layout.
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(tamMedallas, tamMedallas);
                    //Aplicamos el layout.
                    medalla.setLayoutParams(layoutParams);

                    //Añadimos la imagen al layout.
                    layoutMedallas.addView(medalla);


                }








        handler = new Handler();
        r = new Runnable(){

            int currV = 0;
            boolean go = true;

            public void run(){

                /*
                if(currV == 60 && go)
                    go = false;
                else if(currV == -60 && !go)
                    go = true;

                if(go)
                    currV++;
                else
                    currV--;
                */
                if(currV<porcentajeGeneral)
                    currV++;



                meter.setValues(currV, 0, 0);
                //  meter2.setValues(currV, currV*2, currV*3);

                handler.postDelayed(this, 30);
            }
        };






    }

    /**
     * Comprueba si el dispositivo tiene conexión de red.
     * @return
     */
    public boolean hayRed(){

        boolean salida=true;

        System.out.println("HAY RED??");

        ConnectivityManager conMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conMgr.getActiveNetworkInfo();
        if(info!=null)
            return true;
        else
            return false;
    }

    /**
     * Calcula la posición de un jugador en el ranking mundial a través de la puntuación que tiene acumulada.
     * @param puntuacion Puntuación que tiene acumulada en el juego.
     * @return Posición que esa puntuación le da en el ranking.
     */
    public int calcularPosicion(int puntuacion){

        System.out.println("Puntuación recibida" + puntuacion);

        int posicion=0;
        boolean esUltimo=true;

        //Obtenemos el ranking de usuarios
        List<Jugador> rankingJugadores = miConexion.pedirRankingNueva();


        for(Jugador jugador: rankingJugadores){
            if(puntuacion>=jugador.getPuntuacion()) {
                System.out.println("Posición  del jugador "+rankingJugadores.indexOf(jugador)+" "+jugador.getPuntuacion());
                posicion = rankingJugadores.indexOf(jugador);
                esUltimo=false;
                break;
            }
        }

        if(!esUltimo)
            posicion++; //Sólo para evitar que salga posición cero si eres el primero
        if(esUltimo) {
            posicion = rankingJugadores.size();
            posicion++;
        }

        System.out.println("Woo ");
        return posicion;
    }


    /**
     * Programa la acción a tomar cuando se pulsa el botón back del teclado físico del terminal.
     */
    @Override
    public void onBackPressed() {
        //Se cierra la apliación.
        this.finish();
    }

    @Override
    protected void onResume(){
        super.onResume();
        handler.postDelayed(r, 500);
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onStart(){
        super.onStart();

    }

    @Override
    protected void onPause(){
        super.onPause();
        handler.removeCallbacks(r);
        AppEventsLogger.deactivateApp(this);
    }

    /*
    Para crear un fragment tenemos que extender de una de las subclases de fragment,
    DialogFragment, ListFragment, PreferenceFragment o WebViewFragment
     */
    static public class MyDialogFragment extends DialogFragment {



        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
        }

        @Override
        public void onStart() {
            super.onStart();
            Dialog d = getDialog();
            if (d != null) {
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                d.getWindow().setLayout(width, height);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.fragment_entrada, container, false);
            return root;
        }
    }

}
