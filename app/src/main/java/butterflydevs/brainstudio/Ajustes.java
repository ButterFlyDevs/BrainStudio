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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import butterflydevs.brainstudio.extras.ConexionServidor;
import butterflydevs.brainstudio.extras.Dialogos.DialogoBorrarBD;
import butterflydevs.brainstudio.extras.Dialogos.DialogoGrabadoAlias;
import butterflydevs.brainstudio.extras.utilidades;

/**
 * Actividad que permite al usuario configurar algunos ajustes de la aplicación.
 * En principio, su nombre de usuario (que será el que se use para grabar sus datos en el ranking)
 * y el color en el que prefiere la aplicación (por meter algo distinto, ya que no había muchos más
 * ajustes que hacer).
 */
public class Ajustes extends Activity {



    private Button buttonBack, buttonBorrarDatos, buttonEditarNombre;
    private TextView textNombreUsuario;

    private Button colorA, colorB, colorC, colorD;
    private info.hoang8f.android.segmented.SegmentedGroup grupo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        //Con esta orden conseguimos hacer que no se muestre la ActionBar.
        //getSupportActionBar().hide();
        //Con esta hacemos que la barra de estado del teléfono no se vea y la actividad sea a pantalla completa.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);




        buttonBack=(Button)findViewById(R.id.buttonBack);
        buttonBorrarDatos=(Button)findViewById(R.id.buttonBorrarDatos);
        textNombreUsuario=(TextView)findViewById(R.id.textViewNombreUsuario2);
        buttonEditarNombre=(Button)findViewById(R.id.buttonEditName);

        colorA=(Button)findViewById(R.id.ColorA);
        colorB=(Button)findViewById(R.id.ColorB);
        colorC=(Button)findViewById(R.id.ColorC);
        colorD=(Button)findViewById(R.id.ColorD);


        grupo=(info.hoang8f.android.segmented.SegmentedGroup)findViewById(R.id.grupoLista);

        cargarNombreUsuario();


        //Al pulsar uno de los colores cambiamos el color de fondo.
        colorA.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    //Cambiamos de color el fondo, extrayendolo desde colors.xml
                        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.colorfondoA));
                        grabaColor("a");
                    }
                }
        );

        colorB.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Cambiamos de color el fondo, extrayendolo desde colors.xml
                        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.colorfondoB));
                        grabaColor("b");
                    }
                }
        );
        colorC.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Cambiamos de color el fondo, extrayendolo desde colors.xml
                        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.colorfondoC));
                        grabaColor("c");
                    }
                }
        );
        colorD.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Cambiamos de color el fondo, extrayendolo desde colors.xml
                        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.colorfondoD));
                        grabaColor("d");
                    }
                }
        );



        buttonBack.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Creamos el Intent
                        Intent intent = new Intent(Ajustes.this, ActividadPrincipal.class);
                        //Iniciamos la nueva actividad
                        startActivity(intent);
                    }
                }
        );


        buttonEditarNombre.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Se procede de la misma manera que cuando se arranca la activida principal por primera vez.
                        DialogoGrabadoAlias dga = new DialogoGrabadoAlias();
                        dga.setPadre(Ajustes.this); //Para que pueda acceder a las sharesPreferences de la app
                        dga.show(getFragmentManager(), "");
                    }
                }
        );


        buttonBorrarDatos.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        /*Cuando el usuario quiere borra la base de datos tenemos que advertirle
                        de las consecuencias con un diálogo.
                        */

                        //Creamos el dialogo:
                        DialogoBorrarBD dialogoAlerta = new DialogoBorrarBD();
                        dialogoAlerta.setPadre(Ajustes.this);
                        //Lo mostramos:
                        dialogoAlerta.show(getFragmentManager(), "");
                    }
                }
        );

        utilidades.cargarColorFondo(this);

        String colorUsado=utilidades.colorUsado(this);

        if (colorUsado.contains("a"))
            grupo.check(colorA.getId());

        if(colorUsado.contains("b"))
            grupo.check(colorB.getId());

        if(colorUsado.contains("c"))
            grupo.check(colorC.getId());

        if(colorUsado.contains("d"))
            grupo.check(colorD.getId());

    }

    /**
     * Función para grabar color en las shared preferences.
     */

    public void grabaColor(String color){

        SharedPreferences datos=getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=datos.edit();

        editor.putString("colorFondo",color);
        editor.commit(); //Cuando se hace realmente la grabación.
    }


    /**
     * Cambia el nombre del usuario en la base de datos.
     */
    public void cambiarNombreUsuarioBD(){


        SharedPreferences prefe=getSharedPreferences("datos", Context.MODE_PRIVATE);
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

        ConexionServidor miConexion = new ConexionServidor();
        miConexion.actualizarNombre(prefe.getString("alias","Nombre"),telephonyManager.getDeviceId() );
    }

    /**
     * Se extrae a una función para que pueda llamarse desde un par de sitios y así no duplicar código.
     */
    public void cargarNombreUsuario(){
        SharedPreferences prefe=getSharedPreferences("datos", Context.MODE_PRIVATE);
        textNombreUsuario.setText(prefe.getString("alias","Nombre"));
    }

    /**
     * Comprueba si el dispositivo tiene conexión de red.
     * @return
     */
    public boolean hayRed(){

        boolean salida=true;

        System.out.println("HAY RED??");

        ConnectivityManager conMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conMgr.getActiveNetworkInfo();
        if(info!=null)
            return true;
        else
            return false;
    }



    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onStart(){
        super.onStart();

    }

    @Override
    protected void onPause(){
        super.onPause();
    }



}
