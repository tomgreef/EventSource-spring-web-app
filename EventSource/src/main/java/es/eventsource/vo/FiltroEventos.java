package es.eventsource.vo;

public class FiltroEventos {
    private String titulo;
    private double coste;

    public FiltroEventos() {
        this.titulo = "";
        this.coste = 0.0;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getCoste() {
        return coste;
    }

    public void setCoste(double coste) {
        this.coste = coste;
    }
}
