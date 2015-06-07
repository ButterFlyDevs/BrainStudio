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
