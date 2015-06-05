package butterflydevs.brainstudio;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;


public class juegoN extends ActionBarActivity {


    private CircularCounter meterA, meterB, meterC;
    private Handler handler;
    private Runnable r;
    private String[] colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_n);

        colors = getResources().getStringArray(R.array.colors);

        meterA=(CircularCounter)findViewById(R.id.meter1);
        meterB=(CircularCounter)findViewById(R.id.meter2);
        meterC=(CircularCounter)findViewById(R.id.meter3);


        meterA.setFirstWidth(getResources().getDimension(R.dimen.first))
                .setFirstColor(Color.parseColor(colors[0]))

                        //.setSecondWidth(getResources().getDimension(R.dimen.second))
                        //.setSecondColor(Color.parseColor(colors[1]))
                        //.setThirdWidth(getResources().getDimension(R.dimen.third))
                        //.setThirdColor(Color.parseColor(colors[2]))
                .setBackgroundColor(Color.TRANSPARENT);
        //.setBackgroundColor(-14606047);

        meterA.setMetricText("");
        //meter.setMetricSize(30.f);
        meterA.setRange(100);
        meterA.setTextColor(Color.GRAY);
        meterA.setTextSize(40.f);


        meterB.setFirstWidth(getResources().getDimension(R.dimen.first))
                .setFirstColor(Color.parseColor(colors[0]))

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
        meterB.setTextSize(40.f);

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

            int currV = 0;
            boolean go = true;

            public void run(){

                if(currV<46)
                    currV++;

                meterA.setValues(currV, 0, 0);
                meterB.setValues(currV, 0, 0);
                meterC.setValues(currV, 0, 0);
                //  meter2.setValues(currV, currV*2, currV*3);

                handler.postDelayed(this, 30);
            }
        };


        meterA.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Creamos el Intent
                        Intent intent = new Intent(juegoN.this, JuegoGrid12.class);
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
                        Intent intent = new Intent(juegoN.this, JuegoGrid24.class);
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
                        Intent intent = new Intent(juegoN.this, JuegoGrid40.class);
                        //Iniciamos la nueva actividad
                        startActivity(intent);
                    }
                }
        );



    }

    /**
     * Sobrecarga para el control de los botones físicos del terminal.
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){

        //Si pulsamos el botón back nos devuelve a la pantalla principal!.
        if(keyCode==KeyEvent.KEYCODE_BACK){

            Intent intent = new Intent(juegoN.this, ActividadPrincipal.class);
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
