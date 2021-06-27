package es.eventsource.dto;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kevin
 */
public class ReservasDTO {

    private Integer reservaId;
    private Integer asientoFila;
    private Integer asientoColumna;
    private Integer eventoId;
    private Integer usuarioId;

    public ReservasDTO() {
    }

    public ReservasDTO(Integer reservaId, Integer asientoFila, Integer asientoColumna, Integer eventoId, Integer usuarioId) {
        this.reservaId = reservaId;
        this.asientoFila = asientoFila;
        this.asientoColumna = asientoColumna;
        this.eventoId = eventoId;
        this.usuarioId = usuarioId;
    }

    public Integer getReservaId() {
        return reservaId;
    }

    public void setReservaId(Integer reservaId) {
        this.reservaId = reservaId;
    }

    public Integer getAsientoFila() {
        return asientoFila;
    }

    public void setAsientoFila(Integer asientoFila) {
        this.asientoFila = asientoFila;
    }

    public Integer getAsientoColumna() {
        return asientoColumna;
    }

    public void setAsientoColumna(Integer asientoColumna) {
        this.asientoColumna = asientoColumna;
    }

    public Integer getEventoId() {
        return eventoId;
    }

    public void setEventoId(Integer eventoId) {
        this.eventoId = eventoId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

}
