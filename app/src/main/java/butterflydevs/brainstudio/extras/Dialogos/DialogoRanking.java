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
 * Clase asociada al diseño custom_dialog.xml que construye el DialgoFragment para avisar
 * de que no se tiene acceso a la red. Usando cuando el usuario en la pantalla principal intenta
 * acceder al ranking de usuarios y no se tiene red.)
 */
public class DialogoRanking extends DialogFragment {

    public Button botonEntendido;
    public TextView textoInfo;




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
        botonEntendido=(Button) dialog.findViewById(R.id.botonEntendido);
        textoInfo = (TextView) dialog.findViewById(R.id.textoInfo);


        //Ajuste de la info que se muestra:
        textoInfo.setText("Necesitamos acceso a la red para mostrarte el ranking mundial de jugadores."
                         +" Por favor, revisa tu conexión.");


        //Cuando pulsamos el botón de entendido sólo se cierra el diálogo.
        botonEntendido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cerramos el diálogo
                dismiss();

            }
        });

        return dialog;
    }

}
