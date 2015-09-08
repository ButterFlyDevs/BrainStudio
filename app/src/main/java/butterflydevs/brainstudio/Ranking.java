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

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterflydevs.brainstudio.extras.AdaptadorListRanking;
import butterflydevs.brainstudio.extras.ConexionServidor;
import butterflydevs.brainstudio.extras.Jugador;


public class Ranking extends ActionBarActivity {


    private ConexionServidor miConexion = new ConexionServidor();

    private Button buttonBack, buttonHelp;

    List <Jugador> lista;

    final ArrayList<Jugador> jugadores = new ArrayList<Jugador>();

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Con esta orden conseguimos hacer que no se muestre la ActionBar.
        getSupportActionBar().hide();
        getSupportActionBar().hide();
        //Con esta hacemos que la barra de estado del teléfono no se vea y la actividad sea a pantalla completa.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_ranking);

        //Asociamos los botones de la vista a variables de la clase
        buttonBack=(Button)findViewById(R.id.buttonBack);
        buttonHelp=(Button)findViewById(R.id.buttonHelp);

        //Programamos el comportamiento de los botones:
        buttonBack.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Creamos el Intent
                        Intent intent = new Intent(Ranking.this, ActividadPrincipal.class);
                        //Iniciamos la nueva actividad
                        startActivity(intent);
                    }
                }
        );
        buttonHelp.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Ranking.this, Help.class);

                        //    Bundle bundle = new Bundle();
                        //    bundle.putInt("nivel",3);

                        //Introducimos la informacion en el intent para enviarsela a la actívity.
                        //    intent.putExtras(bundle);

                        //Iniciamos la nueva actividad
                        startActivity(intent);
                    }
                }
        );



        //Poner dialog a funcionar
        progressDialog = ProgressDialog.show(Ranking.this, "", "Cargando lista de jugadores...");
        //Ejecutar la hebra para obtener los datos

        final ListView listview = (ListView)findViewById(R.id.listview);

       new Thread(new Runnable() {
           @Override
           public void run() {
               lista = miConexion.pedirRankingNueva();
               jugadores.addAll(lista);
               final AdaptadorListRanking adapter = new AdaptadorListRanking(getApplicationContext(),jugadores);
               System.out.println("TAMAÑO DE LA LISTA: " + jugadores.size());

               //4º ASociamos el adaptador
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       listview.setAdapter(adapter);
                   }
               });

               progressDialog.dismiss();
           }
       }).start();

    }

    //Clase que configura el adaptador del Array,
    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}
