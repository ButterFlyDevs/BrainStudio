package butterflydevs.brainstudio.extras;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import butterflydevs.brainstudio.R;

/**
 * Created by juan on 11/09/15.
 */
public class utilidades {


    public static void cargarColorFondo(Activity padre){

        SharedPreferences prefe=padre.getSharedPreferences("datos", Context.MODE_PRIVATE);

        // textNombreUsuario.setText(prefe.getString("alias","Nombre"));
        String colorElegido = prefe.getString("colorFondo","a");

        if(colorElegido.contains("a"))
            padre.getWindow().getDecorView().setBackgroundColor(padre.getResources().getColor(R.color.colorfondoA));

        if(colorElegido.contains("b"))
            padre.getWindow().getDecorView().setBackgroundColor(padre.getResources().getColor(R.color.colorfondoB));

        if(colorElegido.contains("c"))
            padre.getWindow().getDecorView().setBackgroundColor(padre.getResources().getColor(R.color.colorfondoC));

        if(colorElegido.contains("d"))
            padre.getWindow().getDecorView().setBackgroundColor(padre.getResources().getColor(R.color.colorfondoD));


    }

    public static String colorUsado(Activity padre){

        SharedPreferences prefe=padre.getSharedPreferences("datos", Context.MODE_PRIVATE);
        return prefe.getString("colorFondo","a");

    }
}
