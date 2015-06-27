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

/**
 * Created by juan on 6/06/15.
 */
public class Nivel {
    
    private int puntuacion;
    private int porcentajeCompletado;

    public Nivel(int puntuacion, int porcentajeCompletado){
        this.puntuacion=puntuacion;
        this.porcentajeCompletado=porcentajeCompletado;
    }

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


    @Override
    public String toString(){
        return "Level [puntuacion="+puntuacion+", porcentaje="+porcentajeCompletado+" }";
    }

}
