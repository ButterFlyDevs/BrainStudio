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

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterflydevs.brainstudio.extras.AccesoRestringidoDialog;
import butterflydevs.brainstudio.extras.Jugada;
import butterflydevs.brainstudio.extras.MyCustomDialog;
import butterflydevs.brainstudio.extras.MySQLiteHelper;

/**
 *  Dinamización del juego:
 *
 *      IDEAS:
 *
 *      Lo ideal sería tener tantos linear layout horizontales como niveles. Esto programado a nivel de diseño, y que
 *      no pueda cambiar ya que no conseguimos añadir en tiempo de ejecución los CircularCounter.
 *
 *      Pero como si podemos controlar su aspecto y lo que les acompañan en el layout horizontal añadiremos
 *      una imagen de un candado y el texto bloqueado para definir que a ese nivel aún no se puede acceder.
 *
 *      Es más para no tener que cancelar el enlace desde el CircularCounter lo cambiaremos por uno a
 *      un fragment donde se indique que ese nivel está bloqueado.
 *
 *      Por otro lado como podemos controlar el aspecto del circular counter lo dibujaremos con un circulo completo
 *      pero en gris claro que denote su bloqueo.
 *
 *
 *
 *
 */
public class Juego4 extends ActionBarActivity {


    private CircularCounter meterA, meterB, meterC;
    private float tamTextoInCircles;

    private LinearLayout layoutNivel1, layoutNivel2, layoutNivel3;

    private LinearLayout layoutMedallas;
    private int tamMedallas;

    private Handler handler;
    private Runnable r;
    private String[] colors;

    private Jugada maxJugadaNivel1;
    private Jugada maxJugadaNivel2;
    private Jugada maxJugadaNivel3;

    /**
     * Variables que representan el porcentaje obtenido respecto a la puntuación máxima posible.. En las mejores condiciones
     * (imaginando que no tardasemos nada en completar las pruebas obtendríamos la máxima puntuación (ya que baja cuando más tardamos)
     * entonces tendríamos el 100% de la puntuación, hubiésemos sacado el máximo posible. Este porcentaje mide cuando el máximo posible
     * que se puede sacar hemos sacado.
     */
    private int porcentajePuntosNivel1;
    private int porcentajePuntosNivel2;
    private int porcentajePuntosNivel3;

    private Button botonBack;
    private Button botonHelp;

    //Textos que compañan a los CircularCounter
    private TextView textNivel1;
    private TextView textNivel2;
    private TextView textNivel3;

    //Tamaño del texto para los TextView
    private int tamTextoNiveles;

    // Valores de apertura para los niveles.
    private int llaveNivel2;
    private int llaveNivel3;
    private int llaveFinal;

    //Variables para el control y el bloqueo de niveles.
    boolean puedeJugar2=false;
    boolean puedeJugar3=false;

    //Variable para e manejo de la base de datos.
    MySQLiteHelper db;

    //Tamaño del candado cuando se dibuja: nxn
    private int tamCandado;

    private LinearLayout.LayoutParams llp;


    public Juego4(){
        tamTextoInCircles=30f;
        tamMedallas=150;
        tamCandado=70;

        //En % de juego pasado.
        llaveNivel2=10; //Consiguiendo este valor en el nivel 1 se abre el nivel 2.
        llaveNivel3=10; //Consiguiendo este valor en el nivel 2 se abre l nivel 3.

        //Media de todos los niveles superados en porcentaje para obtener la medalla de plata
        llaveFinal=20;

        tamTextoNiveles=20;
        llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llp.setMargins(50, 0, 0, 0);


        //Inicialización de las variables jugadas:
        maxJugadaNivel1 = new Jugada(0,0);
        maxJugadaNivel2 = new Jugada(0,0);
        maxJugadaNivel3 = new Jugada(0,0);





    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego4);

        //Con esta orden conseguimos hacer que no se muestre la ActionBar.
        getSupportActionBar().hide();
        //Con esta hacemos que la barra de estado del teléfono no se vea y la actividad sea a pantalla completa.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        botonBack=(Button)findViewById(R.id.botonBack);
        botonHelp=(Button)findViewById(R.id.botonHelp);

        //Asociamos los layouts
        layoutNivel1=(LinearLayout)findViewById(R.id.layoutNivel1);
        layoutNivel2=(LinearLayout)findViewById(R.id.layoutNivel2);
        layoutNivel3=(LinearLayout)findViewById(R.id.layoutNivel3);

        layoutMedallas=(LinearLayout)findViewById(R.id.layoutImagenes);



        //El primer nivel está disponible siempre por lo que habrá que cargar sus datos.

        //Instanciamos la base de datos.
        db = new MySQLiteHelper(this);

        //Obtener todas la jugadas del nivel 1 juego 4
        List<Jugada> jugadasNivel1=db.getAllJugadas(1,4);

    //    Toast.makeText(getApplicationContext(), "obtenidas " + jugadasNivel1.size() + "jugadas", Toast.LENGTH_SHORT).show();

        maxJugadaNivel1=Jugada.obtenMaximaJugada(jugadasNivel1);

        textNivel1=new TextView(this);



        textNivel1.setLayoutParams(llp);
        textNivel1.setTextSize(tamTextoNiveles);

        //Escribimos el pantalla la máxima jugada
        textNivel1.setText(Integer.toString(maxJugadaNivel1.getPuntuacion())+" puntos");

        //Añadimos el texto al layout:
        layoutNivel1.addView(textNivel1);

        //Sacamos el porcentaje de puntos, el porcentaje de juego lo tiene dentro la jugada.
        porcentajePuntosNivel1=calculaPorcentaje(1, maxJugadaNivel1.getPuntuacion());

        /**
         * Si se consigue superar el umbral de llaveNivel2 se nos abre el nivel 2 para poder jugar
         * y entonces lo notificamos mediante el fragment y añadimos la medalla al layout.
         * Además de eso añadimos la medalla a la base de datos.
         */
        //Hemos superado los minimos del nivel 1 para obtener la medalla y abrir el siguiente nivel
        if(maxJugadaNivel1.getPorcentaje()>llaveNivel2) {

            //Abrimos el nivel:

            this.puedeJugar2 = true;

            //Informamos de ello:

                    /*
                    Para evitar que la notificacion de la obtencion de medalla se realice una y otra vez
                    vamos a comprobar si esta existe en la base de datos de medallas. Si existe querra decir que
                    ya fue mostrada en su dia cuando se consiguio y que no debe ser mostrada otra vez.
                     */

            //Si no existe la medalla se notifica que se ha ganado. Despues se añadira a la base de datos y no se mostrara mas-
            if(!db.compruebaMedala(1,1)) {
                MyCustomDialog dialogoMedalla = new MyCustomDialog();
                // fragment1.mListener = MainActivity.this;
                dialogoMedalla.text = "nombre";
                dialogoMedalla.juego = 4;
                dialogoMedalla.nivel = 1;
                dialogoMedalla.show(getFragmentManager(), "");
            }


            //Añadimos la medalla de bronce al layout de medallas:

            ImageView medallaBronce = new ImageView(this);
            //Añadimos la imagen
            medallaBronce.setImageResource(R.drawable.juego41);
            //Creamos unos parámetros para su layout.
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(tamMedallas, tamMedallas);
            //Aplicamos el layout.
            medallaBronce.setLayoutParams(layoutParams);

            //Añadimos la imagen al layout.
            layoutMedallas.addView(medallaBronce);

            //Añadimos la medalla a la base de datos:


            //Instanciamos la base de datos
            db = new MySQLiteHelper(this);
            //Añadimos la medalla de bronce: Juego1 , conseguida al superar el Nivel 1
            db.addMedalla(1,1);

        }

        /**
         * Para que ajuste el aspecto del layout al acceso que el usuario tiene a los niveles.
         */
        ajusteRestoNiveles();




        /**
         * Aquí podemos hacer dos cosas:
         * A: procesar la lista nosotros para sacar lo que queremos (en este caso la jugada de mayor puntuación del usuario)
         * B: hacer un consulta especial que nos la devuelva
         */




        colors = getResources().getStringArray(R.array.colors);

        meterA=(CircularCounter)findViewById(R.id.meter1);
        meterB=(CircularCounter)findViewById(R.id.meter2);
        meterC=(CircularCounter)findViewById(R.id.meter3);


        meterA.setFirstWidth(getResources().getDimension(R.dimen.first))
                .setFirstColor(Color.rgb(93,162,71))

                .setSecondWidth(getResources().getDimension(R.dimen.first))
                .setSecondColor(Color.rgb(136,133,133))
                        //.setThirdWidth(getResources().getDimension(R.dimen.third))
                        //.setThirdColor(Color.parseColor(colors[2]))
                .setBackgroundColor(Color.TRANSPARENT);
        //.setBackgroundColor(-14606047);

        meterA.setMetricText("");
        //meter.setMetricSize(30.f);
        meterA.setRange(100);
        meterA.setTextColor(Color.GRAY);
        meterA.setTextSize(tamTextoInCircles);


        meterB.setFirstWidth(getResources().getDimension(R.dimen.first))
                .setFirstColor(Color.rgb(207,85,69))
                .setSecondWidth(getResources().getDimension(R.dimen.first))
                .setSecondColor(Color.rgb(172,164,164))

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
        meterB.setTextSize(tamTextoInCircles);

        meterC.setFirstWidth(getResources().getDimension(R.dimen.first))
                .setFirstColor(Color.rgb(159,60,196))
                .setSecondWidth(getResources().getDimension(R.dimen.second))
                .setSecondColor(Color.rgb(172,164,164))
                        //.setThirdWidth(getResources().getDimension(R.dimen.third))

                        //.setThirdColor(Color.parseColor(colors[2]))
                .setBackgroundColor(Color.TRANSPARENT);
        //.setBackgroundColor(-14606047);

        meterC.setMetricText("");
        //meter.setMetricSize(30.f);
        meterC.setRange(100);
        meterC.setTextColor(Color.GRAY);
        meterC.setTextSize(tamTextoInCircles);

        //No podemos hacer esto directamente porque hace internamente una llamada al manejador y tendríamos
        //que añadir algun método nuevo a CircularCounter.java si quisieramos cambiar su aspecto fuera del un
        //manejador Handler.
        // meterC.setValues(100, 0,0);

        handler = new Handler();

        r = new Runnable(){

            int level1 = 0;
            int level11=0;

            int level2 =0;
            int level22=0;

            int level3=0;
            int level33=0;

            boolean go = true;

            public void run(){

                if(level1<maxJugadaNivel1.getPorcentaje())
                    level1++;
                if(level11<porcentajePuntosNivel1)
                    level11++;

                if(puedeJugar2) { //Si puede jugar en el nivel 2 entonces se trendran datos de las partidas.
                    if (level22 < porcentajePuntosNivel2)
                        level22++;
                    if (level2 < maxJugadaNivel2.getPorcentaje())
                        level2++;
                }

                if(puedeJugar3) { //Si puede jugar en el nivel 2 entonces se trendran datos de las partidas.
                    if (level33 < porcentajePuntosNivel3)
                        level33++;
                    if (level3 < maxJugadaNivel3.getPorcentaje())
                        level3++;
                }


                meterA.setValues(level1, level11, 0);
                meterB.setValues(level2, level22, 0);
                meterC.setValues(level3, level33, 0);

                //Lo activaremos cuando ese nivel esté operativo.
                //meterC.setValues(currV, 0, 0);


                //  meter2.setValues(currV, currV*2, currV*3);

                handler.postDelayed(this, 30);
            }
        };

        // La implementacion del listener del boton dependera de si el jugador puede o no puede acceder a ese nivel.
        meterA.setOnClickListener(

                //En este caso el nivel 1 siempre esta abierto y no hay restricciones.

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Creamos el Intent
                        Intent intent = new Intent(Juego4.this, Juego4niveln.class);

                        Bundle bundle = new Bundle();
                        bundle.putInt("nivel",1);

                        //Introducimos la informacion en el intent para enviarsela a la actívity.
                        intent.putExtras(bundle);

                        //Iniciamos la nueva actividad
                        startActivity(intent);
                    }
                }
        );

        // La implementacion del listener del boton dependera de si el jugador puede o no puede acceder a ese nivel.
        meterB.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /**
                         * Si tiene permiso para poder jugar tendra acceso al juego.
                         */
                        if(puedeJugar2) {


                            //Creamos el Intent
                            Intent intent = new Intent(Juego4.this, Juego4niveln.class);

                            Bundle bundle = new Bundle();
                            bundle.putInt("nivel", 2);

                            //Introducimos la informacion en el intent para enviarsela a la actívity.
                            intent.putExtras(bundle);
                            //Iniciamos la nueva actividad
                            startActivity(intent);

                            /**
                             * En caso de no tener acceso se le mostrara un fragment dialog avisandole de esto.
                             */

                        }else{
                            AccesoRestringidoDialog fragmentAccesoRestringido = new AccesoRestringidoDialog();
                            fragmentAccesoRestringido.show(getFragmentManager(), "");
                        }
                    }

                }
        );



        // La implementacion del listener del boton dependera de si el jugador puede o no puede acceder a ese nivel.

        meterC.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        /**
                         * Si tiene permiso para poder jugar tendra acceso al juego.
                         */
                        if(puedeJugar3) {


                            //Creamos el Intent
                            Intent intent = new Intent(Juego4.this, Juego4niveln.class);

                            Bundle bundle = new Bundle();
                            bundle.putInt("nivel", 3);

                            //Introducimos la informacion en el intent para enviarsela a la actívity.
                            intent.putExtras(bundle);
                            //Iniciamos la nueva actividad
                            startActivity(intent);

                            /**
                             * En caso de no tener acceso se le mostrara un fragment dialog avisandole de esto.
                             */
                        }else{
                            AccesoRestringidoDialog fragmentAccesoRestringido = new AccesoRestringidoDialog();
                            fragmentAccesoRestringido.show(getFragmentManager(), "");
                        }
                    }
                }
        );




        botonBack.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Creamos el Intent
                        Intent intent = new Intent(Juego4.this, juegos.class);
                        //Iniciamos la nueva actividad
                        startActivity(intent);
                    }
                }
        );
        botonHelp.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("Zona_llamada","Juego");
                        bundle.putInt("Numero_zona",4);
                        Intent intent = new Intent(Juego4.this, Help.class);
                        startActivityForResult(intent,1);
                    }
                }
        );


    }

    /**
     * Con esta funcion ajustaremos el aspecto de los layout de acceso a los juegos.
     * Cuando un nivel este activo se mostrara la puntuacion y la palabra puntos ademas el
     * listener del CircularProgress enlazara a el juego mientras que cuando no este activo el enlace
     * cargara un fragment con informacion y se vera la imagen de un candado y la palabra bloqueado.
     *
     * Ademas de esto se hacen comprobacion del estado del juego y se añaden medallas a la base de datos.
     *
     */
    public void ajusteRestoNiveles(){


        if(this.puedeJugar2){

            //Creamos una variable:
            textNivel2 = new TextView(this);

            textNivel2.setLayoutParams(llp);
            textNivel2.setTextSize(tamTextoNiveles);

            db = new MySQLiteHelper(this);
            //Conseguimos las jugadas del nivel 2 del juego 4
            List<Jugada> jugadasNivel2=db.getAllJugadas(2,4);

            //Obtenemos la máxima jugada de estas jugadas:
            maxJugadaNivel2=Jugada.obtenMaximaJugada(jugadasNivel2);

            //Obtenida la máxima jugada del nivel 2 vemos si puede acceder al nivel 3:
            if(maxJugadaNivel2.getPorcentaje()>llaveNivel3) {
                puedeJugar3 = true;

                //Informamos de ello:
                //Si no existe la medalla se notifica que se ha ganado. Despues se añadira a la base de datos y no se mostrara mas-
                if(!db.compruebaMedala(1,2)) {
                    MyCustomDialog dialogoMedalla = new MyCustomDialog();
                    // fragment1.mListener = MainActivity.this;
                    dialogoMedalla.text = "nombre";
                    dialogoMedalla.juego = 4;
                    dialogoMedalla.nivel = 2;
                    dialogoMedalla.show(getFragmentManager(), "");

                }


                //Añadimos la medalla de bronce al layout de medallas:


                ImageView medallaPlata = new ImageView(this);
                //Añadimos la imagen
                medallaPlata.setImageResource(R.drawable.juego42);
                //Creamos unos parámetros para su layout.
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(tamMedallas, tamMedallas);
                //Aplicamos el layout.
                medallaPlata.setLayoutParams(layoutParams);

                //Añadimos la imagen al layout.
                layoutMedallas.addView(medallaPlata);



                //Añadimos la medalla de plata a la base de datos:


                //Instanciamos la base de datos
                db = new MySQLiteHelper(this);
                //Añadimos la medalla de plata: Juego1 , conseguida al superar el Nivel 2
                db.addMedalla(1,2);

            }

            textNivel2.setText(Integer.toString(maxJugadaNivel2.getPuntuacion())+" puntos");


            layoutNivel2.addView(textNivel2);

            porcentajePuntosNivel2=calculaPorcentaje(2, maxJugadaNivel2.getPuntuacion());


        }else{
            //Cambiamos el enlace que da el circular counter, pero eso se hace en el
            //listener y no aquí.

            //Añadimos al layout del nivel 3 la imagen de un candado.

            //Creamos la variable
            ImageView candado = new ImageView(this);
            //Añadimos la imagen
            candado.setImageResource(R.drawable.lock);
            //Creamos unos parámetros para su layout.
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(tamCandado, tamCandado);
            //Aplicamos el layout.
            candado.setLayoutParams(layoutParams);

            //Añadimos la imagen al layout.
            layoutNivel2.addView(candado);

            //Añadimos un texto con el contenido: BLOQUEADO

            //Creamos la variables:
            TextView textBloqueado = new TextView(this);
            //Añadimos el texto
            textBloqueado.setText("Bloqueado");
            layoutNivel2.addView(textBloqueado);

        }



        //Si se puede jugar en el nivel 3 del juego 4 entonces nos molestamos en pedir los datos a la base de datos
        if(this.puedeJugar3){


            //Creamos una variable:
            textNivel3 = new TextView(this);

            textNivel3.setLayoutParams(llp);
            textNivel3.setTextSize(tamTextoNiveles);


            db = new MySQLiteHelper(this);
            //Conseguimos las jugadas del nivel 3 del juego 4
            List<Jugada> jugadasNivel3=db.getAllJugadas(3,4);

            //Obtenemos la máxima jugada de estas jugadas:
            maxJugadaNivel3=Jugada.obtenMaximaJugada(jugadasNivel3);


            textNivel3.setText(Integer.toString(maxJugadaNivel3.getPuntuacion())+" puntos");


            layoutNivel3.addView(textNivel3);


            porcentajePuntosNivel3=calculaPorcentaje(2, maxJugadaNivel3.getPuntuacion());

        }else{ //Si no se puede jugar

            //Cambiamos el enlace que da el circular counter, pero eso se hace en el
            //listener y no aquí.

            //Añadimos al layout del nivel 3 la imagen de un candado.

            //Creamos la variable
            ImageView candado = new ImageView(this);
            //Añadimos la imagen
            candado.setImageResource(R.drawable.lock);
            //Creamos unos parámetros para su layout.
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(tamCandado, tamCandado);
            //Aplicamos el layout.
            candado.setLayoutParams(layoutParams);

            //Añadimos la imagen al layout.
            layoutNivel3.addView(candado);

            //Añadimos un texto con el contenido: BLOQUEADO

            //Creamos la variables:
            TextView textBloqueado = new TextView(this);
            //Añadimos el texto
            textBloqueado.setText("Bloqueado");
            layoutNivel3.addView(textBloqueado);



        }


        // ### OBTENCIÓN DE LA MEDALLA DE ORO ###


        int media=((maxJugadaNivel3.getPorcentaje()+maxJugadaNivel2.getPorcentaje()+maxJugadaNivel1.getPorcentaje())/3);
        System.out.println("media: "+media);
        if(media>llaveFinal) {
            puedeJugar3 = true;

            //Notificamos si no existe ya que se va a añadir:
            if(!db.compruebaMedala(1,3)) {
                //Informamos de ello:
                MyCustomDialog dialogoMedalla = new MyCustomDialog();
                // fragment1.mListener = MainActivity.this;
                dialogoMedalla.text = "nombre";
                dialogoMedalla.juego = 4;
                dialogoMedalla.nivel = 3;
                dialogoMedalla.show(getFragmentManager(), "");
            }

            //Añadimos la medalla de bronce al layout de medallas:

            ImageView medallaOro = new ImageView(this);
            //Añadimos la imagen
            medallaOro.setImageResource(R.drawable.juego43);
            //Creamos unos parámetros para su layout.
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(tamMedallas, tamMedallas);
            //Aplicamos el layout.
            medallaOro.setLayoutParams(layoutParams);

            //Añadimos la imagen al layout.
            layoutMedallas.addView(medallaOro);


            //Añadimos la medalla de plata a la base de datos:


            //Instanciamos la base de datos
            db = new MySQLiteHelper(this);
            //Añadimos la medalla de plata: Juego1 , conseguida al superar el Nivel 3 y media con los otros.
            db.addMedalla(1,3);



        }






    }

    /**
     * Función totalmente dependiente del juego, que se basará en cada uno en unas reglas distintas para calcular
     * el porcentaje de puntuación obtenida respecto a todo el juego.
     * El juego sabe el máximo de puntuación que puede conseguir un jugador y calcula según la puntuación
     * que ha conseugido el % de ese máximo para mostrarselo junto al % de juego "fisico" pasado. Para que el
     * jugador sepa en todo momento si puede obtener mejores resultados o ha llegado al final.
     * @param nivel
     * @param puntuacion
     * @return
     */

    public int calculaPorcentaje(int nivel, int puntuacion){

        /*
        Tanto el 1200 como el 4620 son las puntuaciones máximas que se podrían sacar en ambos niveles
        si se recorrieran todos los grid generados sin tardar nada en resolverlos. EL jugador nunca podrá conseguir
        tanta puntuación pero obviamente podrá acercarse si es muy rápido contestando y no se equivoca.
         */

        int resultado=0;

        int limiteNivel1=1200;
        int limiteNivel2=4620;
        int limiteNivel3=5000;


        if(nivel==1)
            resultado=(100*puntuacion)/limiteNivel1;
        if(nivel==2)
            resultado=(100*puntuacion)/limiteNivel2;
        if(nivel==3)
            resultado=(100*puntuacion)/limiteNivel3;


        return resultado;
    }

    public void ganarMedalla(){


        //Grabamos en la base de datos la medalla obtenida.

        //Mostramos el mensaje de que ha ganado una medalla

        MyCustomDialog fragment1 = new MyCustomDialog();
        // fragment1.mListener = MainActivity.this;
        fragment1.text = "nombre";
        fragment1.juego=4;
        fragment1.nivel=2;
        fragment1.show(getFragmentManager(), "");




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

            Intent intent = new Intent(Juego4.this, ActividadPrincipal.class);
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
