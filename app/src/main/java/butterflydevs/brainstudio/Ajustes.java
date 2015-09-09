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
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.AppEventsLogger;

import net.frakbot.jumpingbeans.JumpingBeans;

import java.util.List;

import butterflydevs.brainstudio.extras.ConexionServidor;
import butterflydevs.brainstudio.extras.Dialogos.DialogoRanking;
import butterflydevs.brainstudio.extras.Jugador;
import butterflydevs.brainstudio.extras.Medalla;
import butterflydevs.brainstudio.extras.MySQLiteHelper;

/**
 * Actividad que permite al usuario configurar algunos ajustes de la aplicación.
 * En principio, su nombre de usuario (que será el que se use para grabar sus datos en el ranking)
 * y el color en el que prefiere la aplicación (por meter algo distinto, ya que no había muchos más
 * ajustes que hacer).
 */
public class Ajustes extends Activity {



    private Button buttonBack, buttonSave;
    private TextView textNombreUsuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        //Con esta orden conseguimos hacer que no se muestre la ActionBar.
        //getSupportActionBar().hide();
        //Con esta hacemos que la barra de estado del teléfono no se vea y la actividad sea a pantalla completa.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);




        buttonBack=(Button)findViewById(R.id.buttonBack);
        textNombreUsuario=(TextView)findViewById(R.id.textViewNombreUsuario2);


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
