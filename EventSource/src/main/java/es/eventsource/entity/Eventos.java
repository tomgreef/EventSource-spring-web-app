package es.eventsource.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "EVENTOS")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Eventos.findAll", query = "SELECT e FROM Eventos e")
        , @NamedQuery(name = "Eventos.findByEventoId", query = "SELECT e FROM Eventos e WHERE e.eventoId = :eventoId")
        , @NamedQuery(name = "Eventos.findByTitulo", query = "SELECT e FROM Eventos e WHERE e.titulo = :titulo")
        , @NamedQuery(name = "Eventos.findByDescripcion", query = "SELECT e FROM Eventos e WHERE e.descripcion = :descripcion")
        , @NamedQuery(name = "Eventos.findByFecha", query = "SELECT e FROM Eventos e WHERE e.fecha = :fecha")
        , @NamedQuery(name = "Eventos.findByFechaLimite", query = "SELECT e FROM Eventos e WHERE e.fechaLimite = :fechaLimite")
        , @NamedQuery(name = "Eventos.findByCoste", query = "SELECT e FROM Eventos e WHERE e.coste = :coste")
        , @NamedQuery(name = "Eventos.findByAforo", query = "SELECT e FROM Eventos e WHERE e.aforo = :aforo")
        , @NamedQuery(name = "Eventos.findByEntradasMaxima", query = "SELECT e FROM Eventos e WHERE e.entradasMaxima = :entradasMaxima")
        , @NamedQuery(name = "Eventos.findByFilas", query = "SELECT e FROM Eventos e WHERE e.filas = :filas")
        , @NamedQuery(name = "Eventos.findByColumnas", query = "SELECT e FROM Eventos e WHERE e.columnas = :columnas")})
public class Eventos implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "EVENTO_ID")
    private Integer eventoId;
    @Basic(optional = false)
    @Column(name = "TITULO", nullable = false)
    private String titulo;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "FECHA", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "FECHA_LIMITE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaLimite;
    @Basic(optional = false)
    @Column(name = "COSTE", nullable = false)
    private double coste;
    @Basic(optional = false)
    @Column(name = "AFORO", nullable = false)
    private int aforo;
    @Basic(optional = false)
    @Column(name = "ENTRADAS_MAXIMA", nullable = false)
    private int entradasMaxima;
    @Column(name = "FILAS")
    private Integer filas;
    @Column(name = "COLUMNAS")
    private Integer columnas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventoId")
    private List<Reservas> reservasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventoId")
    private List<Etiquetas> etiquetasList;

    public Eventos() {
    }

    public Eventos(Integer eventoId) {
        this.eventoId = eventoId;
    }

    public Eventos(Integer eventoId, String titulo, String descripcion, Date fecha, Date fechaLimite, double coste, int aforo, int entradasMaxima) {
        this.eventoId = eventoId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.fechaLimite = fechaLimite;
        this.coste = coste;
        this.aforo = aforo;
        this.entradasMaxima = entradasMaxima;
    }

    public Integer getEventoId() {
        return eventoId;
    }

    public void setEventoId(Integer eventoId) {
        this.eventoId = eventoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public double getCoste() {
        return coste;
    }

    public void setCoste(double coste) {
        this.coste = coste;
    }

    public int getAforo() {
        return aforo;
    }

    public void setAforo(int aforo) {
        this.aforo = aforo;
    }

    public int getEntradasMaxima() {
        return entradasMaxima;
    }

    public void setEntradasMaxima(int entradasMaxima) {
        this.entradasMaxima = entradasMaxima;
    }

    public Integer getFilas() {
        return filas;
    }

    public void setFilas(Integer filas) {
        this.filas = filas;
    }

    public Integer getColumnas() {
        return columnas;
    }

    public void setColumnas(Integer columnas) {
        this.columnas = columnas;
    }

    @XmlTransient
    public List<Reservas> getReservasList() {
        return reservasList;
    }

    public void setReservasList(List<Reservas> reservasList) {
        this.reservasList = reservasList;
    }

    @XmlTransient
    public List<Etiquetas> getEtiquetasList() {
        return etiquetasList;
    }

    public void setEtiquetasList(List<Etiquetas> etiquetasList) {
        this.etiquetasList = etiquetasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventoId != null ? eventoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Eventos)) {
            return false;
        }
        Eventos other = (Eventos) object;
        if ((this.eventoId == null && other.eventoId != null) || (this.eventoId != null && !this.eventoId.equals(other.eventoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Eventos[ eventoId=" + eventoId + " ]";
    }

}
