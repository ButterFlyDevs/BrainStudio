package butterflydevs.brainstudio;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.MatrixCursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterflydevs.brainstudio.extras.MyAdapter;
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
                if(itemPosition==1) {
                    intent = new Intent(juegos.this, juegoN.class);
                    //Iniciamos la actividad
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
        items.add(new Nivel(25,2748973));
        items.add(new Nivel(60,3387894));


        return items;
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
