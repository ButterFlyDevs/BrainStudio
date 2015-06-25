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
import android.widget.TextView;

import java.util.List;

import butterflydevs.brainstudio.Juego5;
import butterflydevs.brainstudio.Juego5niveln;
import butterflydevs.brainstudio.R;

/**
 * Created by juan on 18/06/15.
 */
public class DialogoJuego5 extends DialogFragment {

    public static enum ComportamientoBoton{
        SALIR, CERRAR
    }

    public Juego5niveln padre;

    public Button botonSalir;
    public ComportamientoBoton comp;

    public TextView textoInfo;
    public TextView textoA;
    public TextView textoB;
    public TextView textoPorcentajeSuperado;


    public String textoInformacion;
    public String textoConvertidoA;
    public String textoConvertidoB;
    public int porcentajeSuperado;
    public String textoBoton;

    public onSubmitListener mListener;
    public String text = "";

    public int juego;
    public int nivel;


    interface onSubmitListener {
        void setOnSubmitListener(String arg);
    }


    public void setComportamientoBoton(ComportamientoBoton comportamiento){

        if(comportamiento==ComportamientoBoton.SALIR)
            textoBoton="Salir";
        if(comportamiento==ComportamientoBoton.CERRAR)
            textoBoton="Entendido";

        comp=comportamiento;
    }

    public void setPadre(Juego5niveln padrePasado){
        padre=padrePasado;
    }

    public void setDatos(String titulo, List<Integer> secuenciaJuego, List<Integer> secuenciaJugador, int porcentajeSuperado){

       textoInformacion=titulo;

       textoConvertidoA="Original: ";
       textoConvertidoB="Introducida: ";

       for(int a: secuenciaJuego)
           textoConvertidoA+=Integer.toString(a)+" ";

       for(int b: secuenciaJugador)
           textoConvertidoB+=Integer.toString(b)+" ";

       this.porcentajeSuperado=porcentajeSuperado;
    }

    public void setDatos(String titulo, String textoA, String textoB, int porcentaje){
        textoInformacion=titulo;
        textoConvertidoA=textoA;
        textoConvertidoB=textoB;
        porcentajeSuperado=porcentaje;

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
        dialog.setContentView(R.layout.dialogo_juego_5);
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
        textoInfo.setText(textoInformacion);

        botonSalir.setText(textoBoton);


        //Programación del boton "Salir"
        botonSalir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            if(comp==ComportamientoBoton.SALIR) {



                Intent intent = new Intent(getActivity(), Juego5.class);
                //Iniciamos la nueva actividad
                startActivity(intent);

                dismiss();
            }else
            if(comp==ComportamientoBoton.CERRAR){

                padre.desbloquearJuego();
                dismiss();
            }




            }
        });
        return dialog;
    }




}
