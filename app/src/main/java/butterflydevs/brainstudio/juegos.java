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
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterflydevs.brainstudio.extras.Jugada;
import butterflydevs.brainstudio.extras.MyAdapter;
import butterflydevs.brainstudio.extras.MySQLiteHelper;
import butterflydevs.brainstudio.extras.Nivel;


public class juegos extends Activity {

    private Button botonAtras, botonHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juegos);

        //Con esta hacemos que la barra de estado del teléfono no se vea y la actividad sea a pantalla completa.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        botonAtras=(Button)findViewById(R.id.buttonBack);
        botonHelp=(Button)findViewById(R.id.buttonHelp);

        botonAtras.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Creamos el Intent
                        Intent intent = new Intent(juegos.this, ActividadPrincipal.class);
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





        MyAdapter adapter = new MyAdapter(this,generateData());

        ListView listView = (ListView) findViewById(R.id.listview);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> listView, View itemView, int itemPosition, long itemId)
            {
                //Creamos el Intent
                Intent intent;

                //Dependiendo de la posición pulsada vamos a una activity u a otra.

                    //Posición 0 del list: Juego 1
                    if(itemPosition==0) {
                        intent = new Intent(juegos.this, Juego1.class);
                        //Iniciamos la actividad
                        startActivity(intent);
                    }


                    //Posición 1 del list: Juego 2
                    else if(itemPosition==1){
                        //TODO IMPLEMENTAR JUEGO2
                        intent = new Intent(juegos.this,Juego2.class);
                        startActivity(intent);
                    }


                    //Posición 2 del list: Juego 3
                    else if(itemPosition==2){
                        intent = new Intent(juegos.this,Juego3.class);
                        startActivity(intent);
                    }

                    //Posicion 3 del list: Juego 4
                    else if(itemPosition==3){
                        intent = new Intent(juegos.this, Juego4.class);
                        startActivity(intent);
                    }

                    else if(itemPosition==4){
                        intent = new Intent(juegos.this, Juego5.class);
                        startActivity(intent);
                    }
            }
        });
    }


    /**
     * Función que se encarga de generar los datos que se cargan en el listView
     * Usamos funciones que declaramos en esta clase para obtener los datos en el formatoq eu nos
     * interese.
     *
     * @return El array con los items del ListView.
     */
    private ArrayList<Nivel> generateData(){

        ArrayList<Nivel> items = new ArrayList<Nivel>();

        //Juego 1
        items.add(new Nivel(25, puntuacionJuego1()));

        //Juego 2
        items.add(new Nivel(25,puntuacionJuego2()));

        //Juego 3
        items.add(new Nivel(25, puntuacionJuego2()));

        //Juego 4
        items.add(new Nivel(25, puntuacionJuego2()));

        //Juego 5
        items.add(new Nivel(porcentajeJuego5(), 0));


        return items;
    }


    public int puntuacionJuego1(){

        MySQLiteHelper db = new MySQLiteHelper(this);

        List<Jugada> jugadasNivel1=db.getAllJugadas(1,1);
        Jugada maxJugadaNivel1= Jugada.obtenMaximaJugada(jugadasNivel1);

        List<Jugada> jugadasNivel2=db.getAllJugadas(2,1);
        Jugada maxJugadaNivel2= Jugada.obtenMaximaJugada(jugadasNivel2);


        int maxPuntuacion1 = maxJugadaNivel1.getPuntuacion();
        int maxPuntuacion2 = maxJugadaNivel2.getPuntuacion();


        return maxPuntuacion1+maxPuntuacion2;
    }

    // TODO POR HACER IMPLEMENTACION DE ESTA FUNCION

    public int puntuacionJuego2(){
        MySQLiteHelper db = new MySQLiteHelper(this);

        List<Jugada> jugadasNive21=db.getAllJugadas(1,2);
        Jugada maxJugadaNive21= Jugada.obtenMaximaJugada(jugadasNive21);

        List<Jugada> jugadasNive22=db.getAllJugadas(2,2);
        Jugada maxJugadaNive22= Jugada.obtenMaximaJugada(jugadasNive22);


        int maxPuntuacion1 = maxJugadaNive21.getPuntuacion();
        int maxPuntuacion2 = maxJugadaNive22.getPuntuacion();

        return maxPuntuacion1+maxPuntuacion2;
    }
    public int porcentajeJuego1(){

        return 2;
    }

    public int porcentajeJuego5(){
        MySQLiteHelper db = new MySQLiteHelper(this);

        List<Jugada> jugadasNivel1=db.getAllJugadas(1,5);
        Jugada maxJugadaNivel1=Jugada.obtenMaximaJugada2(jugadasNivel1);

        List<Jugada> jugadasNivel2=db.getAllJugadas(2,5);
        Jugada maxJugadaNivel2=Jugada.obtenMaximaJugada2(jugadasNivel2);

        List<Jugada> jugadasNivel3=db.getAllJugadas(3,5);
        Jugada maxJugadaNivel3=Jugada.obtenMaximaJugada2(jugadasNivel3);


        int maxPorcentaje1=maxJugadaNivel1.getPorcentaje();
        int maxPorcentaje2=maxJugadaNivel2.getPorcentaje();
        int maxPorcentaje3=maxJugadaNivel3.getPorcentaje();


        return (maxPorcentaje1+maxPorcentaje2+maxPorcentaje3)/3;
    }



}
