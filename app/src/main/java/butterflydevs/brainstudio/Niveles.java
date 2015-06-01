package butterflydevs.brainstudio;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;


public class Niveles extends ActionBarActivity {


    //Filas de juegos dentro de la actividad Juegos
    LinearLayout listaNivelesJuegoA, listaJuegoB, listaNivelesJuegoC, listaNivelesJuegoD, listaNivelesJuegoE;


    //Contadores de progreso de cada juego:
    private CircularCounter meter, meter2;
    //Colores usados en los indicadores de progreso
    private String[] colors;
    //Variable para el manejo de fuentes de texto
    private TextView customFont, customFont2;



    //Variables necesarias para realizar la carga en una hebra independiente
    private Handler handler;
    private Runnable r;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niveles);



        //Definición de uno de los indicadores:
        //  meter = (CircularCounter) findViewById(R.id.meter);


        XmlPullParser parser=getResources().getXml(R.xml.attrs);
        AttributeSet attrs = Xml.asAttributeSet(parser);
          meter = new CircularCounter(this, null);
        // meter2 = (CircularCounter) findViewById(R.id.meter2);

        /*
        meter.setFirstWidth(getResources().getDimension(R.dimen.first))
                .setFirstColor(Color.parseColor(colors[0]))

                        //.setSecondWidth(getResources().getDimension(R.dimen.second))
                        //.setSecondColor(Color.parseColor(colors[1]))

                        //.setThirdWidth(getResources().getDimension(R.dimen.third))
                        //.setThirdColor(Color.parseColor(colors[2]))
                .setBackgroundColor(Color.TRANSPARENT);
        //.setBackgroundColor(-14606047); */

        meter.setFirstColor(Color.BLACK);
        meter.setBackgroundColor(Color.BLACK);
        meter.setMetricText("");
        meter.setMetricSize(30.f);
        meter.setRange(100);
        meter.setTextColor(Color.GRAY);
        meter.setTextSize(40.f);



        listaNivelesJuegoA=(LinearLayout)findViewById(R.id.faseA);

        Button botonPruebaA = new Button(this);

        botonPruebaA.setText("yeah, yeah");

        //AÑadimos un boton en tiempo de ejecución: funciona.
        listaNivelesJuegoA.addView(botonPruebaA);
        //Añadimos un diagrama de estos en timepo de ejecución, ##################### no funciona!!
        listaNivelesJuegoA.addView(meter);


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
                if(currV<46)
                    currV++;



                meter.setValues(currV, 0, 0);
                //  meter2.setValues(currV, currV*2, currV*3);

                handler.postDelayed(this, 30);
            }
        };

    }


}
