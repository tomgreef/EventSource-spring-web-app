package es.eventsource.vo;

import java.util.ArrayList;
import java.util.List;

public class FiltroUsuarios {
    private String nombre;
    private String apellidos;

    public FiltroUsuarios() {
        this.nombre = "";
        this.apellidos = "";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
}
