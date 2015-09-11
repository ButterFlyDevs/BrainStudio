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

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import butterflydevs.brainstudio.R;
import butterflydevs.brainstudio.extras.MySQLiteHelper;

/**
 * Clase asociada al diseño custom_dialog.xml que construye el DialgoFragment para avisar
 * de que no se tiene acceso a la red. Usando cuando el usuario en la pantalla principal intenta
 * acceder al ranking de usuarios y no se tiene red.)
 */
public class DialogoBorrarBD extends DialogFragment {

    private Button botonBorrar, botonCancelar;
    private Activity actividadPadre;


    public void setPadre(Activity padre){
        actividadPadre=padre;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog dialog = new Dialog(getActivity());

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.dialogo_borrar_bd);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));

        dialog.show();


        //Asociamos los elementos de la vista
        botonBorrar=(Button) dialog.findViewById(R.id.botonBorrar);
        botonCancelar=(Button)dialog.findViewById(R.id.botonCancelar);



        //Cuando pulsamos el botón de cancelar sólo se cierra el diálogo.
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cerramos el diálogo
                dismiss();

            }
        });


        //Cuando pulsamos el botón de borrarsólo se procede al ### VACIADO DE LA BASE DE DATOS ###
        botonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Instanciamos la clase de la base de datos, usando la clase padre.
                MySQLiteHelper db = new MySQLiteHelper(actividadPadre);
                //Eliminamos todo contenido de la base de datos.
                db.eraseAll();
                //Cerramos el diálogo
                dismiss();

            }
        });

        return dialog;
    }

}
