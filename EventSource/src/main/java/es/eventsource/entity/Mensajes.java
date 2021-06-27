package es.eventsource.entity;

import com.sun.istack.NotNull;
import es.eventsource.dto.MensajesDTO;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "MENSAJES")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Mensajes.findAll", query = "SELECT m FROM Mensajes m")
        , @NamedQuery(name = "Mensajes.findByChatId", query = "SELECT m FROM Mensajes m WHERE m.mensajesPK.chatId = :chatId")
        , @NamedQuery(name = "Mensajes.findByFecha", query = "SELECT m FROM Mensajes m WHERE m.mensajesPK.fecha = :fecha")
        , @NamedQuery(name = "Mensajes.findByUsuarioId", query = "SELECT m FROM Mensajes m WHERE m.mensajesPK.usuarioId = :usuarioId")
        , @NamedQuery(name = "Mensajes.findByMensaje", query = "SELECT m FROM Mensajes m WHERE m.mensaje = :mensaje")})
public class Mensajes implements Serializable {
    @EmbeddedId
    protected MensajesPK mensajesPK;
    @Basic(optional = false)
    @Column(name = "MENSAJE", nullable = false)
    private String mensaje;
    @JoinColumn(name = "CHAT_ID", referencedColumnName = "CHAT_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Chats chats;
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "USUARIO_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuarios usuarios;

    public Mensajes() {
    }

    public Mensajes(MensajesPK mensajesPK) {
        this.mensajesPK = mensajesPK;
    }

    public Mensajes(MensajesPK mensajesPK, String mensaje) {
        this.mensajesPK = mensajesPK;
        this.mensaje = mensaje;
    }

    public Mensajes(int chatId, Date fecha, int usuarioId) {
        this.mensajesPK = new MensajesPK(chatId, fecha, usuarioId);
    }

    public MensajesPK getMensajesPK() {
        return mensajesPK;
    }

    public void setMensajesPK(MensajesPK mensajesPK) {
        this.mensajesPK = mensajesPK;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Chats getChats() {
        return chats;
    }

    public void setChats(Chats chats) {
        this.chats = chats;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mensajesPK != null ? mensajesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mensajes)) {
            return false;
        }
        Mensajes other = (Mensajes) object;
        if ((this.mensajesPK == null && other.mensajesPK != null) || (this.mensajesPK != null && !this.mensajesPK.equals(other.mensajesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Mensajes[ mensajesPK=" + mensajesPK + " ]";
    }

    public MensajesDTO getDTO(){
        MensajesDTO dto = new MensajesDTO();

        dto.setMensaje(mensaje);
        dto.setMensajesPK(mensajesPK);
        dto.setUsuarios(usuarios.getDTO());

        return dto;
    }
}
