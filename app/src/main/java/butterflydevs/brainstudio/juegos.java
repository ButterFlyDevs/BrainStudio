package butterflydevs.brainstudio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

    private Button juego1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juegos);

        MyAdapter adapter = new MyAdapter(this,generateData());

        ListView listView = (ListView) findViewById(R.id.listview);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> listView, View itemView, int itemPosition, long itemId)
            {
                //Creamos el Intent
                Intent intent;

                //Dependiendo de la posición pulsada vamos a una activity u a otra.
                if(itemPosition==0) {
                    intent = new Intent(juegos.this, Juego1.class);
                    //Iniciamos la actividad
                    startActivity(intent);
                }
                else if(itemPosition==1)
                {
                    //TODO IMPLEMENTAR JUEGO2
                    intent = new Intent(juegos.this,Juego2.class);
                    startActivity(intent);
                }
            }
        });
    }


    /**
     * Función que se encarga de generar los datos que se cargan en el listView, ahora son datos estáticos
     * pero tendrán que sustituirse por datos obtenidos de nuestra base de datos.
     *
     * @return El array con los items del ListView.
     */
    private ArrayList<Nivel> generateData(){

        ArrayList<Nivel> items = new ArrayList<Nivel>();
        items.add(new Nivel(25, puntuacionJuego1()));
        //items.add(new Nivel(60,3387894));
        items.add(new Nivel(25,puntuacionJuego2()));

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_juegos, menu);
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
