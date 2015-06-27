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
 * Created by juan on 1/05/15.
 */
public class Jugador {

    private String nombre;
    private int puntuacion;
    private String pais;

    public Jugador(){};

    public Jugador(String nombre, int puntuacion, String pais){
        this.nombre=nombre;
        this.puntuacion=puntuacion;
        this.pais=pais;
    }

    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    public String getNombre(){
        return this.nombre;
    }
    public void setPuntuacion(int puntuacion){
        this.puntuacion=puntuacion;
    }
    public int getPuntuacion(){
        return this.puntuacion;
    }
    public void setPais(String pais){
        this.pais=pais;
    }
    public String getPais(){
        return this.pais;
    }
}
