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

import butterflydevs.brainstudio.Juego4;
import butterflydevs.brainstudio.Juego5;
import butterflydevs.brainstudio.Juego5niveln;
import butterflydevs.brainstudio.R;

/**
 * Clase de configuración del Dialogo para la salida de las partidas. Con este conseguimos ofrecer
 * al usuario un resumen de su partida y un botón salir que lo devuelve a la pantalla de su juego.
 *
 * Esta clase ofrece los suficientes metodos para configurar el DialogFragment como se quiera, uno
 * de los mas importantes es el que establece el nivel al que se devuelve.
 */
public class DialogoSalidaJuegos extends DialogFragment {

    public static enum ComportamientoBoton{
        SALIR, CERRAR
    }

    public Juego5niveln padreJuego5;

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
        padreJuego5=padrePasado;
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

    public void setNivel(int nivelPasado){
        nivel=nivelPasado;
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


                if(nivel==4){
                    Intent intent = new Intent(getActivity(), Juego4.class);
                    //Iniciamos la nueva actividad
                    startActivity(intent);
                }

                if(nivel==5) {
                    Intent intent = new Intent(getActivity(), Juego5.class);
                    //Iniciamos la nueva actividad
                    startActivity(intent);
                }

                dismiss();
            }else

            /**
             * Juego5niveln usa este diálogo para informar de cosas específicas durante el juego y por eso requiere
             * este método que es sólo específico de el.
             */
            if(comp==ComportamientoBoton.CERRAR){

                padreJuego5.desbloquearJuego();
                dismiss();
            }




            }
        });
        return dialog;
    }




}
