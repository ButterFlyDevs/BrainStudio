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
