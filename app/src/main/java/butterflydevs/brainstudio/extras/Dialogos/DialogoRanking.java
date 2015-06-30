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
package butterflydevs.brainstudio.extras.Dialogos;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterflydevs.brainstudio.Juego2niveln;
import butterflydevs.brainstudio.Juego4;
import butterflydevs.brainstudio.R;
import butterflydevs.brainstudio.Ranking;
import butterflydevs.brainstudio.extras.ConexionServidor;

/**
 * Created by juan on 18/06/15.
 */
public class DialogoRanking extends DialogFragment {

    public Button botonEnviar;
    public Button botonCancelar;
    public TextView textoInfo;
    public EditText cajaTexto;

    public int puntuacion=0;
    public int posicion=0;

    public void setPuntuacion(int newPuntuacion){
        puntuacion=newPuntuacion;
    }
    public void setPosicion(int newPosicion){
        posicion=newPosicion;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog dialog = new Dialog(getActivity());

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.custom_dialog_ranking);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));

        dialog.show();


        //Asociamos los elementos de la vista
        botonEnviar=(Button) dialog.findViewById(R.id.botonEnviar);
        botonCancelar=(Button)dialog.findViewById(R.id.botonCancelar);
        textoInfo = (TextView) dialog.findViewById(R.id.textoInfo);
        cajaTexto = (EditText) dialog.findViewById(R.id.editText);

        //Ajuste de la info que se muestra:
        textoInfo.setText("Tienes "+puntuacion+" puntos acumulados. " +
                          "Te colocarías en la posicion "+posicion+" del ranking." +
                           "¿Quieres registrar tu puntuación?");



        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Enviamos los datos al servidor:
                    enviarDatos();

                //Vamos a la actividad del ranking:
                    Intent intent = new Intent(getActivity(), Ranking.class);
                    startActivity(intent);

                //Cerramos el diálogo
                dismiss();

            }
        });

        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return dialog;
    }

    /**
     * Realiza el envio real a la base de datos en linea.
     */
    public void enviarDatos(){
        ConexionServidor miConexion = new ConexionServidor();
        miConexion.enviaPuntuacion(cajaTexto.getText().toString(),puntuacion,getActivity().getResources().getConfiguration().locale.getISO3Country().toString());
    }


}
