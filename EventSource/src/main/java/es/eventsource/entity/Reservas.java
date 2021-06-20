package es.eventsource.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tomvg
 */
@Entity
@Table(name = "RESERVAS")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Reservas.findAll", query = "SELECT r FROM Reservas r")
        , @NamedQuery(name = "Reservas.findByReservaId", query = "SELECT r FROM Reservas r WHERE r.reservaId = :reservaId")
        , @NamedQuery(name = "Reservas.findByCantidad", query = "SELECT r FROM Reservas r WHERE r.cantidad = :cantidad")
        , @NamedQuery(name = "Reservas.findByAsientoFila", query = "SELECT r FROM Reservas r WHERE r.asientoFila = :asientoFila")
        , @NamedQuery(name = "Reservas.findByAsientoColumna", query = "SELECT r FROM Reservas r WHERE r.asientoColumna = :asientoColumna")})
public class Reservas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RESERVA_ID")
    private Integer reservaId;
    @Column(name = "CANTIDAD")
    private Integer cantidad;
    @Column(name = "ASIENTO_FILA")
    private Integer asientoFila;
    @Column(name = "ASIENTO_COLUMNA")
    private Integer asientoColumna;
    @JoinColumn(name = "EVENTO_ID", referencedColumnName = "EVENTO_ID")
    @ManyToOne(optional = false)
    private Eventos eventoId;
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "USUARIO_ID")
    @ManyToOne(optional = false)
    private Usuarios usuarioId;

    public Reservas() {
    }

    public Reservas(Integer reservaId) {
        this.reservaId = reservaId;
    }

    public Integer getReservaId() {
        return reservaId;
    }

    public void setReservaId(Integer reservaId) {
        this.reservaId = reservaId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
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

    public Eventos getEventoId() {
        return eventoId;
    }

    public void setEventoId(Eventos eventoId) {
        this.eventoId = eventoId;
    }

    public Usuarios getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuarios usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reservaId != null ? reservaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reservas)) {
            return false;
        }
        Reservas other = (Reservas) object;
        if ((this.reservaId == null && other.reservaId != null) || (this.reservaId != null && !this.reservaId.equals(other.reservaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Reservas[ reservaId=" + reservaId + " ]";
    }

}
