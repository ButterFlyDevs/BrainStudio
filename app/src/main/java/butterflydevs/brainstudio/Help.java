package butterflydevs.brainstudio;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Activity para mostrar ayuda al usuario de la aplicación.
 *
 * La Activity reconocerá por los datos pasados a través del Intent desde qué Activity se le está llamando
 * y cargará los recursos necesarios para ella.
 */
public class Help extends ActionBarActivity {

    private String zona_de_llamada; //Podrá ser desde el activity de resultados de un juego concreto, o desde el tablero de jugo
    private int numero_zona;    //Del 1 al 5 (POR AHORA! :D ), define el juego que está invocando a esta clase

    private TextView titulo, descripcion, fin_texto;
    //Textos de descripciones de imagen
    private TextView texto_imagen1, texto_imagen2, texto_imagen3;
    private ImageView imagen_1, imagen_2, imagen_3;
    private Button boton_back;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_max2_imagenes);

        //Con esta orden conseguimos hacer que no se muestre la ActionBar.
        getSupportActionBar().hide();
        //Con esta hacemos que la barra de estado del telefono no se vea y la actividad sea a pantalla completa.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        titulo = (TextView) findViewById(R.id.titulo_ayuda);
        descripcion = (TextView) findViewById(R.id.descripcion_ayuda);
        fin_texto = (TextView) findViewById(R.id.texto_fin_ayuda);
        imagen_1 = (ImageView) findViewById(R.id.foto_linearLayout_1);
        imagen_2 = (ImageView) findViewById(R.id.foto_linearLayout_2);

        texto_imagen1 = (TextView) findViewById(R.id.texto_linearLayout_1);
        texto_imagen2 = (TextView) findViewById(R.id.texto_linearLayout_2);
        boton_back = (Button) findViewById(R.id.boton_atras_ayuda);


        //Obtenemos los datos que se le pasa a la actividad.
        intent=getIntent();
        //Obtenemos la información del intent
        String Zona_llamada = intent.getStringExtra("Zona_llamada");
        int Numero_zona = intent.getIntExtra("Numero_zona",0);

        System.out.println(Zona_llamada);
        System.out.println(Numero_zona);
        if(Zona_llamada.contains("Juego")){    //Si se llama desde las activitys de los juegos...
            System.out.println("Entrando al if");
            switch (Numero_zona){
                case 1:
                    //Implementar
                    break;
                case 2:
                    //Configuramos el layout (EL JUEGO 2 PRECISA DEL LAYOUT DE 3 IMÁGENES!!!!)
                    setContentView(R.layout.activity_help__max3_imagenes);
                    titulo = (TextView) findViewById(R.id.titulo_ayuda);
                    descripcion = (TextView) findViewById(R.id.descripcion_ayuda);
                    fin_texto = (TextView) findViewById(R.id.texto_fin_ayuda);
                    imagen_1 = (ImageView) findViewById(R.id.foto_linearLayout_1);
                    imagen_2 = (ImageView) findViewById(R.id.foto_linearLayout_2);

                    texto_imagen1 = (TextView) findViewById(R.id.texto_linearLayout_1);
                    texto_imagen2 = (TextView) findViewById(R.id.texto_linearLayout_2);
                    boton_back = (Button) findViewById(R.id.boton_atras_ayuda);
                    imagen_3 = (ImageView) findViewById(R.id.foto_linearLayout_3);
                    texto_imagen3 = (TextView) findViewById(R.id.texto_linearLayout_3);

                    titulo.setText(getString(R.string.titulo_juego2));
                    fin_texto.setText(getString(R.string.fin_ayuda_2));
                    descripcion.setText(getString(R.string.descripcion_2));
                    texto_imagen1.setText(getString(R.string.juego2_foto1));
                    texto_imagen2.setText(getString(R.string.juego2_foto2));
                    texto_imagen3.setText(getString(R.string.juego2_foto3));
                    imagen_1.setImageResource(R.drawable.juego2_1);
                    imagen_2.setImageResource(R.drawable.juego2_2);
                    imagen_3.setImageResource(R.drawable.juego2_3);
                    break;
                case 3:
                    //Implementar
                    break;
                case 4:
                    //Implementar
                    break;
                default:
                    //Implementar
            }
        }

        boton_back.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       finish();
                    }
                });

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
