package es.eventsource.service;

import es.eventsource.dao.ChatsRepository;
import es.eventsource.dao.MensajesRepository;
import es.eventsource.dao.UsuariosRepository;
import es.eventsource.dto.MensajesDTO;
import es.eventsource.dto.UsuariosDTO;
import es.eventsource.entity.Chats;
import es.eventsource.entity.Mensajes;
import es.eventsource.entity.MensajesPK;
import es.eventsource.entity.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MensajesService {

    private MensajesRepository mensajesRepository;

    @Autowired
    public void setMensajesRepository(MensajesRepository mensajesRepository) {
        this.mensajesRepository = mensajesRepository;
    }

    private UsuariosRepository usuariosRepository;

    @Autowired
    public void setUsuariosRepository(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    private ChatsRepository chatsRepository;

    @Autowired
    public void setChatsRepository(ChatsRepository chatsRepository) {
        this.chatsRepository = chatsRepository;
    }

    protected List<MensajesDTO> convertirAListaDTO(List<Mensajes> lista) {

        if (lista != null) {
            List<MensajesDTO> listaDTO = new ArrayList<>();
            lista.forEach((mensaje) -> {
                listaDTO.add(mensaje.getDTO());
            });
            return listaDTO;
        } else {
            return null;
        }
    }

    public List<MensajesDTO> listarMensajes(Integer chatId){
        List<Mensajes> mensajes = this.mensajesRepository.getMensajesById(chatId);

        return this.convertirAListaDTO(mensajes);
    }

    public void create(String contenido, Integer chatId, Integer usuarioId) {

        Date date = new Date();
        Mensajes m = new Mensajes(chatId, date, usuarioId);
        m.setMensaje(contenido);
        Usuarios u = this.usuariosRepository.getById(usuarioId);
        m.setUsuarios(u);
        Chats chat = this.chatsRepository.getById(chatId);
        this.mensajesRepository.save(m);
        this.usuariosRepository.save(u);

        this.chatsRepository.save(chat);
    }
}
