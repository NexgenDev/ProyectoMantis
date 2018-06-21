package mantis.nexgen.com.proyectomantis;

public class ModeloPlagas {
    private String plaga;
    private String estado;
    private String intensidad;

    public ModeloPlagas(String plaga, String estado, String intensidad) {
        this.plaga = plaga;
        this.estado = estado;
        this.intensidad = intensidad;
    }

    public String getPlaga() {
        return plaga;
    }

    public void setPlaga(String plaga) {
        this.plaga = plaga;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIntensidad() {
        return intensidad;
    }

    public void setIntensidad(String intensidad) {
        this.intensidad = intensidad;
    }
}
