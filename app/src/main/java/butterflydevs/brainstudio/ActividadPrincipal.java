package butterflydevs.brainstudio;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class ActividadPrincipal extends Activity {

    private CircularCounter meter, meter2;

    private String[] colors;

    private Handler handler;

    private Runnable r;

    private Button botonBrain;

    private TextView customFont, customFont2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_principal);
        Animation loopParpadeante = AnimationUtils.loadAnimation(this, R.anim.animacionbotonplay);

        colors = getResources().getStringArray(R.array.colors);
        botonBrain = (Button)findViewById(R.id.button);

        botonBrain.startAnimation(loopParpadeante);


        botonBrain.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Creamos el Intent
                       // Intent intent = new Intent(ActividadPrincipal.this, ActividadNiveles.class);
                        //Iniciamos la nueva actividad
                        //startActivity(intent);
                    }
                }
        );


        meter = (CircularCounter) findViewById(R.id.meter);
        // meter2 = (CircularCounter) findViewById(R.id.meter2);

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
        customFont2 = (TextView)findViewById(R.id.textView2);
        Typeface font = Typeface.createFromAsset(getAssets(), "gloriahallelujah.ttf");
        customFont.setTypeface(font);
        customFont2.setTypeface(font);
        meter.setTypeface(font);




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

    @Override
    protected void onResume(){
        super.onResume();
        handler.postDelayed(r, 500);
    }

    @Override
    protected void onPause(){
        super.onPause();
        handler.removeCallbacks(r);
    }


}
