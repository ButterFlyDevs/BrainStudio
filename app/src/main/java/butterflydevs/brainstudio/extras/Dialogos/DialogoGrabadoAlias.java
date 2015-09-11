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
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import butterflydevs.brainstudio.ActividadPrincipal;
import butterflydevs.brainstudio.Ajustes;
import butterflydevs.brainstudio.R;
import butterflydevs.brainstudio.extras.MySQLiteHelper;
import butterflydevs.brainstudio.extras.utilidades;

/**
 * Clase asociada al layout dialogo_nombre_usuario.xml que muestra un diálogo para que el usuario
 * modifique o intrudca por primera vez (dependiendo de cuando se abra) su nombre de usuario, una forma
 * simple de identificarlo, útil para mostrarlo por pantalla en el menú prinpical y para subirlo al ranking.
 */
public class DialogoGrabadoAlias extends DialogFragment {

    private Button botonGuardar;
    private EditText editTextNombre;
    /**
     * En este caso necesitamos una referencia al padre paara poder acceder a las preferencias compartidas.
     */
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
        dialog.setContentView(R.layout.dialogo_nombre_usuario);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));

        dialog.show();


        //Asociamos los elementos de la vista
        botonGuardar=(Button)dialog.findViewById(R.id.botonGuardar);
        editTextNombre=(EditText)dialog.findViewById(R.id.editTextNombreUsuario);

        //Lo hacemos en modo privado para que sólo pueda ser accesible para esta app.
        //Obtenemos una referencia
        SharedPreferences prefe=actividadPadre.getSharedPreferences("datos", Context.MODE_PRIVATE);

        /*Consutamos el dato con la key "alias", si no estuviera (como cuando sucede la primera ejecución) devuelve "Nombre" para
        que sea eso lo que se escriba en el editText.
        */
        editTextNombre.setText(prefe.getString("alias","Nombre"));


        //Cuando pulsamos el botón de cancelar sólo se cierra el diálogo.
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Cuando grabamos los datos lo que hacemos es grabar en el archivo de preferencias el contenido del cuadro de texto en
                //una variable llamada alias.

                SharedPreferences datos = actividadPadre.getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=datos.edit();
                editor.putString("alias",editTextNombre.getText().toString());
                editor.commit(); //Cuando se hace realmente la grabación.


                /*Para que los efectos del cambio se vean inmediatamente y no haya que volver
                a entrar a la página llamamos a un método de la clase, sea la que sea que puede usarlo
                para que modifique en el acto el nombre.
                */
                if(actividadPadre.getLocalClassName().contains("Ajustes"))
                    //Hacemos casting a Ajustes.
                    ((Ajustes)actividadPadre).cargarNombreUsuario();

                if(actividadPadre.getLocalClassName().contains("ActividadPrincipal"))
                    //Hacemos casting a ActividadPrincipal.
                    ((ActividadPrincipal)actividadPadre).cargarNombreUsuario();



                //Cerramos el diálogo
                dismiss();

            }
        });


        return dialog;
    }



}
