package butterflydevs.brainstudio.extras;

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
 * Created by JOSE ANTONIO  on 22/06/15.
 */
public class DialogoJuego extends DialogFragment {

    private Button botonEntendido;
    private TextView textoTitulo;
    private ImageView imagen;
    private TextView texto_a_mostrar;


    private EditText mEditText;
    private onSubmitListener mListener;
    private String texto_recibido, titulo_recibido;

    private int juego;

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
        textoTitulo = (TextView) dialog.findViewById(R.id.textoInfo);
        imagen = (ImageView) dialog.findViewById(R.id.imageMedalla);
        texto_a_mostrar = (TextView) dialog.findViewById(R.id.textAcceso);

        texto_a_mostrar.setText(texto_recibido);
        textoTitulo.setText(titulo_recibido);
        imagen.setImageResource(R.drawable.question);

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

    /**
     * Función para establecer los parámetros a mostrar en el Fragment de diálogo que se muestra durante el juego
     *
     * @param texto_recibido => texto que se quiere mostrar en el fragment
     * @param titulo_recibido ==> texto que se quiere mostrar como título del fragment
     * @param juego ==> número de juego desde el que se está llamando al fragment
     */
    public void establecerInformacionDialogo(String texto_recibido, String titulo_recibido, int juego){
        this.texto_recibido = texto_recibido;
        this.titulo_recibido = titulo_recibido;
        this.juego=juego;
    }

}