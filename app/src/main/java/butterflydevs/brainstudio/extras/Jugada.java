package butterflydevs.brainstudio.extras;

import java.util.List;

/**
 * Created by juan on 6/06/15.
 */
public class Jugada {

    private int puntuacion;
    private int porcentajeCompletado;
    private int id;

    public Jugada(int puntuacion, int porcentajeCompletado){
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
    public void setId(int id){ this.id=id;}
    public int getId(){return id;}


    @Override
    public String toString(){
        return "[puntuacion="+puntuacion+", porcentaje="+porcentajeCompletado+"]\n";
    }

    /**
     * Método estático de la clase ya que nos hace falta en varios lugares
     * @param jugadas
     * @return
     */
    static public Jugada obtenMaximaJugada(List<Jugada> jugadas){

        int puntuacion = 0;
        int porcentaje = 0;

        for(Jugada jugada: jugadas)
            if(jugada.getPuntuacion()>puntuacion) {
                puntuacion = jugada.getPuntuacion();
                porcentaje = jugada.getPorcentaje();
            }
        return new Jugada(puntuacion,porcentaje);
    }

}
