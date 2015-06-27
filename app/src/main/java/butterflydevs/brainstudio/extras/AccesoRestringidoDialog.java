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
package butterflydevs.brainstudio.extras;

import android.app.Dialog;
import android.app.DialogFragment;

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

import butterflydevs.brainstudio.R;

/**
 * Created by juan on 18/06/15.
 */
public class AccesoRestringidoDialog extends DialogFragment {

    public Button botonEntendido;



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

        //Asociación al layout del diálogo
        dialog.setContentView(R.layout.custom_dialog2);

        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));

        dialog.show();


        //Asociamos los elementos de la vista
        botonEntendido = (Button) dialog.findViewById(R.id.botonEntendido);



        botonEntendido.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Podemos hacer algo aqui o llamar a un metodo de la clase que nos llama.
                //((Juego4)getActivity()).submitButtonClick();

                Toast.makeText(getActivity(), "Yeeaahhh", Toast.LENGTH_SHORT).show();


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
