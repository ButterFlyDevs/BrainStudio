package butterflydevs.brainstudio;

import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * Activity para mostrar ayuda al usuario de la aplicación.
 *
 * La Activity reconocerá por los datos pasados a través del Intent desde qué Activity se le está llamando
 * y cargará los recursos necesarios para ella.
 */
public class Help extends ActionBarActivity {

    private String zona_de_llamada; //Podrá ser desde el activity de resultados de un juego concreto, o desde el tablero de jugo
    private int numero_zona;    //Del 1 al 5 (POR AHORA! :D ), define el juego que está invocando a esta clase

    private TextView titulo, descripcion_peque, descripcion_larga;
    private ImageView imagen_ayuda;
    private Button boton_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        //Con esta orden conseguimos hacer que no se muestre la ActionBar.
        getSupportActionBar().hide();
        //Con esta hacemos que la barra de estado del telefono no se vea y la actividad sea a pantalla completa.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        titulo = (TextView) findViewById(R.id.titulo_ayuda);
        descripcion_larga = (TextView) findViewById(R.id.descripcion_larga_ayuda);
        descripcion_peque = (TextView) findViewById(R.id.descripcion_peque_ayuda);
        //imagen_ayuda = (ImageView) findViewById(R.id.imagen_ayuda);
        boton_back = (Button) findViewById(R.id.boton_atras_ayuda);

        titulo.setText(getString(R.string.titulo_juego1));
        descripcion_larga.setText(getString(R.string.desc_larga_juego1));
        descripcion_peque.setText(getString(R.string.desc_peque_juego1));

        boton_back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
        //imagen_ayuda.setImageDrawable(getDrawable(R.drawable.profile));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_help, menu);
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
