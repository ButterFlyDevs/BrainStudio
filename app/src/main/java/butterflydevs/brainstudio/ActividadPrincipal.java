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
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.frakbot.jumpingbeans.JumpingBeans;

import butterflydevs.brainstudio.extras.MySQLiteHelper;

public class ActividadPrincipal extends Activity {

    private CircularCounter meter, meter2;

    private String[] colors;

    private Handler handler;

    private Runnable r;

    private Button botonBrain, buttonUser;

    private TextView customFont, customFont2;

    private TextView textPuntos, textPTS, textPOS;

    private int porcentajeGeneral=0;

    private JumpingBeans jumpingBeans1;


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
        textPTS=(TextView)findViewById(R.id.textPTS);
        textPOS=(TextView)findViewById(R.id.textPos);

        jumpingBeans1 = JumpingBeans.with(textPOS)
                .makeTextJump(0, textPOS.getText().toString().indexOf(' '))
                .setIsWave(false)
                .setLoopDuration(2000)
                .build();




        colors = getResources().getStringArray(R.array.colors);
        botonBrain = (Button)findViewById(R.id.button);
        buttonUser=(Button)findViewById(R.id.buttonUser);

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
        buttonUser.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActividadPrincipal.this, Ranking.class);

                    //    Bundle bundle = new Bundle();
                    //    bundle.putInt("nivel",3);

                        //Introducimos la informacion en el intent para enviarsela a la actívity.
                    //    intent.putExtras(bundle);

                        //Iniciamos la nueva actividad
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
        Typeface font = Typeface.createFromAsset(getAssets(), "gloriahallelujah.ttf");
        customFont.setTypeface(font);
        customFont2.setTypeface(font);
        meter.setTypeface(font);


        MySQLiteHelper db = new MySQLiteHelper(this);

        customFont2.setText(Integer.toString(db.calcularPuntuacionGeneral()));

        porcentajeGeneral=db.calcularPorcentajeGeneral();
        

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

    private void runFragment() {


        //Declaramos una nueva hebra
        new Thread() {
            //Le decimos lo que queremos que haga:
            public void run() {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                MyDialogFragment frag = new MyDialogFragment();
                frag.show(ft, "txn_tag");


                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                frag.dismiss();

            }
        }.start();
    }

    @Override
    protected void onResume(){
        super.onResume();
        handler.postDelayed(r, 500);
    }

    @Override
    protected void onStart(){
        super.onStart();

       // MySQLiteHelper db = new MySQLiteHelper(this);

        //Operaciones de prueba:

        //Añadir jugadas
       // db.addJugada(new Jugada(4839984, 54));
       // db.addJugada(new Jugada(2783827, 67));

        //Obtener todas la jugadas
        //List<Jugada> jugadas=db.getAllJugadas();

       // System.out.println("datos"+jugadas.size()+jugadas.toString());



    }

    @Override
    protected void onPause(){
        super.onPause();
        handler.removeCallbacks(r);
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
