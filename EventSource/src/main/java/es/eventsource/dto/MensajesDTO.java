package es.eventsource.dto;

import es.eventsource.entity.MensajesPK;

public class MensajesDTO {

    protected MensajesPK mensajesPK;
    private String mensaje;
    private ChatsDTO chats;
    private UsuariosDTO usuarios;

    public MensajesDTO(){

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

    public ChatsDTO getChats() {
        return chats;
    }

    public void setChats(ChatsDTO chats) {
        this.chats = chats;
    }

    public UsuariosDTO getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(UsuariosDTO usuarios) {
        this.usuarios = usuarios;
    }


}

