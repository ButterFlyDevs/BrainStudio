package butterflydevs.brainstudio;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;

public class JuegoGrid12 extends ActionBarActivity {

    //Tamaño del grid
    final private int tamGrid=12;

    //Vector de botones
    private Button[] botones = new Button[tamGrid];

    Animation loopParpadeante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_grid12);

        loopParpadeante= AnimationUtils.loadAnimation(this, R.anim.animacionbotongrid12);


        //Asociamos los elementos de la vista:
        asociarElementosVista();
        //Animamos el grid
       // animarGrid();




    }

    @Override
    protected void onStart(){
        super.onStart();
        animarGrid();
        for(int i=0; i<10; i++) {
            obtenerMatrizJugada(2);
            System.out.println("---");
        }
    }

    public void asociarElementosVista(){
        String buttonID;
        int resID;

        for(int i=0; i<tamGrid; i++) {
            buttonID="boton"+Integer.toString(i);
            resID = getResources().getIdentifier(buttonID, "id","butterflydevs.brainstudio");
            botones[i]=(Button)findViewById(resID);
           // botones[i].startAnimation(loopParpadeante);
        }

    }
    public void animarGrid(){
        //Cargamos la animación del botón:
        botones[0].startAnimation(loopParpadeante);
        botones[11].startAnimation(loopParpadeante);
        //for(int i=0; i<tamGrid; i++)
          //  botones[i].startAnimation(loopParpadeante);
    }


    /**
     * Esta función es la que define que celdas serán las que se marquen en el juego. Si se le pasa
     * dos tendrá que calcular dos aleatoriamente y asignarlas en una matriz de booleanos.
     * @param numCeldas Es el número de celdas que tenra que calcular de forma aleatoria.
     */
    public boolean[] obtenerMatrizJugada(int numCeldas){

        //Creamos el vector que vamos a devolver:
        boolean [] matrizBooleanos = new boolean[12];

        //Inicializamos el vector:
        for(int i=0; i<tamGrid; i++)
            matrizBooleanos[i]=false;

        //Rellenamos la matriz con el número de celdas positivas que nos indique el parámetro:


            //Random que hace imposible repetir dos veces un número:
                ArrayList enteros = new ArrayList();
                for(int i=0; i<tamGrid; i++)
                    enteros.add(i);

        ArrayList finales = new ArrayList();

            //Sacamos cuantos numeros nos sean necesarios como celdas tengamos que rellenar.
            int elegido;
            int numFinal=tamGrid;
            Random rnd = new Random();
            for(int i=0; i<numCeldas; i++) {
                elegido = (int)(rnd.nextDouble() * numFinal); //Random de números entre el 0 y el numFinal
                enteros.remove(elegido);
                finales.add(elegido);
                numFinal--;
            }

            for(int i=0; i<finales.size(); i++)
                System.out.println("Elegido: "+finales.get(i));




        return matrizBooleanos;
    }

}
