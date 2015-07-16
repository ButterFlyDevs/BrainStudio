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

/**
 * Created by juan on 18/06/15.
 */
public class MyCustomDialog extends DialogFragment {

    public Button botonEntendido;
    public TextView textoInfo;
    public ImageView imagenMedalla;
    public TextView textoAcceso;


    public EditText mEditText;
    public onSubmitListener mListener;
    public String text = "";

    public int juego;
    public int nivel;

    interface onSubmitListener {
        void setOnSubmitListener(String arg);
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog dialog = new Dialog(getActivity());

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));

        dialog.show();


        //Asociamos los elementos de la vista
        botonEntendido = (Button) dialog.findViewById(R.id.botonEntendido);
        textoInfo = (TextView) dialog.findViewById(R.id.textoInfo);
        imagenMedalla = (ImageView) dialog.findViewById(R.id.imageMedalla);
        textoAcceso = (TextView) dialog.findViewById(R.id.textAcceso);

        //Algún ajuste:
        textoInfo.setText("¡Has conseguido una medalla!");


        if(juego==1) {
            if(nivel==1) {
                imagenMedalla.setImageResource(R.drawable.bronce);
                textoAcceso.setText("Acceso nivel 2");
            }
            if(nivel==2) {
                imagenMedalla.setImageResource(R.drawable.plata);
                textoAcceso.setText("Acceso nivel 3");
            }
            if(nivel==3) {
                imagenMedalla.setImageResource(R.drawable.oro);
                textoAcceso.setText("Completada Zona A");
            }

        } else if(juego==2){
            switch (nivel) {
                case 1:
                    imagenMedalla.setImageResource(R.drawable.bronce_juego2);
                    textoAcceso.setText("Nivel 2 desbloqueado!");
                    break;
                case 2:
                    imagenMedalla.setImageResource(R.drawable.plata_juego2);
                    textoAcceso.setText("Nivel 3 desbloqueado!");
                    break;
                default:
                    imagenMedalla.setImageResource(R.drawable.oro_juego2);
                    textoAcceso.setText("Completada Zona B");
            }

        }else if(juego==3){
            switch (nivel) {
                case 1:
                    imagenMedalla.setImageResource(R.drawable.juego31);
                    textoAcceso.setText("Nivel 2 desbloqueado!");
                    break;
                case 2:
                    imagenMedalla.setImageResource(R.drawable.juego32);
                    textoAcceso.setText("Nivel 3 desbloqueado!");
                    break;
                default:
                    imagenMedalla.setImageResource(R.drawable.juego33);
                    textoAcceso.setText("Completada Zona B");
            }
        }else if(juego==4){
            switch (nivel) {
                case 1:
                    imagenMedalla.setImageResource(R.drawable.juego41);
                    textoAcceso.setText("Nivel 2 desbloqueado!");
                    break;
                case 2:
                    imagenMedalla.setImageResource(R.drawable.juego42);
                    textoAcceso.setText("Nivel 3 desbloqueado!");
                    break;
                default:
                    imagenMedalla.setImageResource(R.drawable.juego43);
                    textoAcceso.setText("Completada Zona B");
            }
        }else if(juego==5){
            switch (nivel) {
                case 1:
                    imagenMedalla.setImageResource(R.drawable.juego51);
                    textoAcceso.setText("Nivel 2 desbloqueado!");
                    break;
                case 2:
                    imagenMedalla.setImageResource(R.drawable.juego52);
                    textoAcceso.setText("Nivel 3 desbloqueado!");
                    break;
                default:
                    imagenMedalla.setImageResource(R.drawable.juego53);
                    textoAcceso.setText("Completada Zona B");
            }
        }



        botonEntendido.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Podemos hacer algo aqui o llamar a un metodo de la clase que nos llama.
                //((Juego4)getActivity()).submitButtonClick();

                if(juego==2){
                    Juego2niveln.IniciarTemporizador();
                }


                dismiss();
                /*
                mListener.setOnSubmitListener(mEditText.getText().toString());
                dismiss();
                */
            }
        });
        return dialog;
    }


}
