package butterflydevs.brainstudio.extras.Dialogos;

import android.app.Dialog;
import android.app.DialogFragment;
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

import java.util.List;

import butterflydevs.brainstudio.Juego2niveln;
import butterflydevs.brainstudio.Juego5;
import butterflydevs.brainstudio.R;

/**
 * Created by juan on 18/06/15.
 */
public class DialogoFinJuego5 extends DialogFragment {

    public Button botonSalir;

    public TextView textoInfo;
    public TextView textoA;
    public TextView textoB;
    public TextView textoPorcentajeSuperado;

    public String textoConvertidoA;
    public String textoConvertidoB;

    public int porcentajeSuperado;


    public onSubmitListener mListener;
    public String text = "";

    public int juego;
    public int nivel;


    interface onSubmitListener {
        void setOnSubmitListener(String arg);
    }


    public void setDatos(List<Integer> secuenciaJuego, List<Integer> secuenciaJugador, int porcentajeSuperado){

       textoConvertidoA="Original: ";
       textoConvertidoB="Introducida: ";

       for(int a: secuenciaJuego)
           textoConvertidoA+=Integer.toString(a)+" ";

       for(int b: secuenciaJugador)
           textoConvertidoB+=Integer.toString(b)+" ";

       this.porcentajeSuperado=porcentajeSuperado;
    }


    /**
     * Donde vamos a tener toda la programación del diálogo.
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog dialog = new Dialog(getActivity());

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.dialogo_fin_juego_5);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));

        dialog.show();


        //Asociamos los elementos de la vista
        botonSalir=(Button)dialog.findViewById(R.id.botonEntendido);
        textoInfo=(TextView)dialog.findViewById(R.id.textoInfo);
        textoPorcentajeSuperado=(TextView)dialog.findViewById(R.id.textoPorcentaje);

        textoA=(TextView)dialog.findViewById(R.id.textoA);
        textoB=(TextView)dialog.findViewById(R.id.textoB);

        textoA.setText(textoConvertidoA);
        textoB.setText(textoConvertidoB);

        textoPorcentajeSuperado.setText(Integer.toString(porcentajeSuperado)+"% superado");

        //Algún ajuste:
        textoInfo.setText("¡Te has equivocado!");




        //Programación del boton "Salir"
        botonSalir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getActivity(), Juego5.class);
                //Iniciamos la nueva actividad
                startActivity(intent);


                dismiss();

            }
        });
        return dialog;
    }


}
