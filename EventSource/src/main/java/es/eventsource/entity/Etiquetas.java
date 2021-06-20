package es.eventsource.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@Table(name = "ETIQUETAS")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Etiquetas.findAll", query = "SELECT e FROM Etiquetas e")
        , @NamedQuery(name = "Etiquetas.findByEtiquetaId", query = "SELECT e FROM Etiquetas e WHERE e.etiquetaId = :etiquetaId")
        , @NamedQuery(name = "Etiquetas.findByNombre", query = "SELECT e FROM Etiquetas e WHERE e.nombre = :nombre")})
public class Etiquetas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ETIQUETA_ID")
    private Integer etiquetaId;
    @Basic(optional = false)
    @Column(name = "NOMBRE", nullable = false)
    private String nombre;
    @JoinColumn(name = "EVENTO_ID", referencedColumnName = "EVENTO_ID")
    @ManyToOne(optional = false)
    private Eventos eventoId;

    public Etiquetas() {
    }

    public Etiquetas(Integer etiquetaId) {
        this.etiquetaId = etiquetaId;
    }

    public Etiquetas(Integer etiquetaId, String nombre) {
        this.etiquetaId = etiquetaId;
        this.nombre = nombre;
    }

    public Integer getEtiquetaId() {
        return etiquetaId;
    }

    public void setEtiquetaId(Integer etiquetaId) {
        this.etiquetaId = etiquetaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Eventos getEventoId() {
        return eventoId;
    }

    public void setEventoId(Eventos eventoId) {
        this.eventoId = eventoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (etiquetaId != null ? etiquetaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Etiquetas)) {
            return false;
        }
        Etiquetas other = (Etiquetas) object;
        if ((this.etiquetaId == null && other.etiquetaId != null) || (this.etiquetaId != null && !this.etiquetaId.equals(other.etiquetaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Etiquetas[ etiquetaId=" + etiquetaId + " ]";
    }

}
