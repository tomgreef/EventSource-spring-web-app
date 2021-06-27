package es.eventsource.dto;

import java.util.Date;
import java.util.List;

public class ChatsDTO {

    private Integer chatId;
    private Date fecha;
    private List<MensajesDTO> mensajesList;
    private UsuariosDTO teleoperadorId;
    private UsuariosDTO usuarioId;

    public ChatsDTO(){

    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<MensajesDTO> getMensajesList() {
        return mensajesList;
    }

    public void setMensajesList(List<MensajesDTO> mensajesList) {
        this.mensajesList = mensajesList;
    }

    public UsuariosDTO getTeleoperadorId() {
        return teleoperadorId;
    }

    public void setTeleoperadorId(UsuariosDTO teleoperadorId) {
        this.teleoperadorId = teleoperadorId;
    }

    public UsuariosDTO getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UsuariosDTO usuarioId) {
        this.usuarioId = usuarioId;
    }



}
