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
import java.util.List;

/**
 * Clase extra que nos sirve para abstraer el concepto de Jugada.
 * Así podemos usar información entre el juego y la base de datos de forma más simple,
 * además la depuración gracias a la sobrecarga del método toString se hace más simple.
 *
 * Un objeto de tipo juagada podría tener más variables como el juego del que viene o el nivel,
 * pero como solo es una abstracción para el intercambio de información de forma más fluida no es
 * necesario.
 *
 */
public class Jugada {


    private int puntuacion; //Puntuación de la jugada (según el algoritmo de cálculo del juego en cuestión.
    private int porcentajeCompletado; //Porcentaje del juego completado, tmb. según el alg. del juego.
    private int id; //Identificador usado para la base de datos pare que toda jugada sea unívoca.

    /**
     * Constructor de la clase.
     * Inicializa el objeto de tipo jugada con los datos puntuación y porcentaje.
     * @param puntuacion Puntuación de la jugada.
     * @param porcentajeCompletado Porcentaje completado en esa jugada en el juego en cuestión.
     */
    public Jugada(int puntuacion, int porcentajeCompletado){
        this.puntuacion=puntuacion;
        this.porcentajeCompletado=porcentajeCompletado;
    }

    //Geters y Seters de la clase para las variables privadas

    public int getPuntuacion(){
        return puntuacion;
    }
    public void setPuntuacion(int nuevaPuntuacion){
        puntuacion=nuevaPuntuacion;
    }

    public int getPorcentaje(){
        return porcentajeCompletado;
    }
    public void setPorcetaje(int nuevoPorcentaje){
        porcentajeCompletado=nuevoPorcentaje;
    }

    public void setId(int id){ this.id=id;}
    public int getId(){return id;}

    /**
     * Sobrecarga del método toString para describir en formato texto un objeto del tipo Jugada.
     * @return Una cadena de texto con la información necesaria y en el formato específico de un objeto.
     */
    @Override
    public String toString(){
        return "[puntuacion="+puntuacion+", porcentaje="+porcentajeCompletado+"]\n";
    }

    /**
     * Método estático que no tiene que ver con un objeto de tipo Jugada en concreto pero que los maneja
     * y como la necesitaremos en más de un lugar lo hacemos de clase para poder llamarlo sin instanciar
     * un objeto.
     *
     * Nos será útil cuando queramos cargar de entre una lista de jugadas recuperadas de la base de datos
     * la más alta para colocarla en los distintos marcadores que tenemos por la aplicación.
     *
     * @param jugadas Lista de jugadas para trabajar con ella.
     * @return La jugada más alta basándonos sólo en la PUNTUACIÓN.
     */
    static public Jugada obtenMaximaJugada(List<Jugada> jugadas){

        /**
         * Con esta iniciación de variables si no existe jugada maxima porque aún no
         * hay ninguna jugada se devuelve una jugada con 0, 0 para que pueda mostrar algo .
         */
        int puntuacion = 0;
        int porcentaje = 0;

        for(Jugada jugada: jugadas)
            //El parámetro decisivo es la puntuación.
            if(jugada.getPuntuacion()>puntuacion) {
                puntuacion = jugada.getPuntuacion();
                porcentaje = jugada.getPorcentaje();
            }
        return new Jugada(puntuacion,porcentaje);
    }

}