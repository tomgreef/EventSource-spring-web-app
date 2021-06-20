package es.eventsource.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ESTUDIOS")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Estudios.findAll", query = "SELECT e FROM Estudios e")
        , @NamedQuery(name = "Estudios.findByEstudioId", query = "SELECT e FROM Estudios e WHERE e.estudioId = :estudioId")
        , @NamedQuery(name = "Estudios.findByNombre", query = "SELECT e FROM Estudios e WHERE e.nombre = :nombre")
        , @NamedQuery(name = "Estudios.findByFechaInicial", query = "SELECT e FROM Estudios e WHERE e.fechaInicial = :fechaInicial")
        , @NamedQuery(name = "Estudios.findByFechaFinal", query = "SELECT e FROM Estudios e WHERE e.fechaFinal = :fechaFinal")
        , @NamedQuery(name = "Estudios.findByEdadInferior", query = "SELECT e FROM Estudios e WHERE e.edadInferior = :edadInferior")
        , @NamedQuery(name = "Estudios.findByEdadSuperior", query = "SELECT e FROM Estudios e WHERE e.edadSuperior = :edadSuperior")
        , @NamedQuery(name = "Estudios.findBySexo", query = "SELECT e FROM Estudios e WHERE e.sexo = :sexo")
        , @NamedQuery(name = "Estudios.findByCantidad", query = "SELECT e FROM Estudios e WHERE e.cantidad = :cantidad")})
public class Estudios implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ESTUDIO_ID")
    private Integer estudioId;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "FECHA_INICIAL")
    @Temporal(TemporalType.DATE)
    private Date fechaInicial;
    @Column(name = "FECHA_FINAL")
    @Temporal(TemporalType.DATE)
    private Date fechaFinal;
    @Column(name = "EDAD_INFERIOR")
    private Integer edadInferior;
    @Column(name = "EDAD_SUPERIOR")
    private Integer edadSuperior;
    @Basic(optional = false)
    @Column(name = "SEXO", nullable = false)
    private int sexo;
    @Basic(optional = false)
    @Column(name = "CANTIDAD", nullable = false)
    private int cantidad;
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "USUARIO_ID")
    @ManyToOne(optional = false)
    private Usuarios usuarioId;

    public Estudios() {
    }

    public Estudios(Integer estudioId) {
        this.estudioId = estudioId;
    }

    public Estudios(Integer estudioId, int sexo, int cantidad) {
        this.estudioId = estudioId;
        this.sexo = sexo;
        this.cantidad = cantidad;
    }

    public Integer getEstudioId() {
        return estudioId;
    }

    public void setEstudioId(Integer estudioId) {
        this.estudioId = estudioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Integer getEdadInferior() {
        return edadInferior;
    }

    public void setEdadInferior(Integer edadInferior) {
        this.edadInferior = edadInferior;
    }

    public Integer getEdadSuperior() {
        return edadSuperior;
    }

    public void setEdadSuperior(Integer edadSuperior) {
        this.edadSuperior = edadSuperior;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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
        hash += (estudioId != null ? estudioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estudios)) {
            return false;
        }
        Estudios other = (Estudios) object;
        if ((this.estudioId == null && other.estudioId != null) || (this.estudioId != null && !this.estudioId.equals(other.estudioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Estudios[ estudioId=" + estudioId + " ]";
    }

}

