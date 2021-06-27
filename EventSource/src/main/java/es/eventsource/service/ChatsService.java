package es.eventsource.service;

import es.eventsource.dao.ChatsRepository;
import es.eventsource.dao.UsuariosRepository;
import es.eventsource.dto.ChatsDTO;
import es.eventsource.entity.Chats;
import es.eventsource.entity.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ChatsService {
    private ChatsRepository chatsRepository;

    @Autowired
    public void setChatsRepository(ChatsRepository chatsRepository) {
        this.chatsRepository = chatsRepository;
    }

    private UsuariosRepository usuariosRepository;

    @Autowired
    public void setUsuariosRepository(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    protected List<ChatsDTO> convertirAListaDTO(List<Chats> lista) {

        if (lista != null) {
            List<ChatsDTO> listaDTO = new ArrayList<>();
            lista.forEach((chat) -> {
                listaDTO.add(chat.getDTO());
            });
            return listaDTO;
        } else {
            return null;
        }
    }

    public List<ChatsDTO> listarChatsTeleoperador(String nombre){
        List<Chats> chats;
        if(nombre != null && nombre.length() > 0){
            chats = this.chatsRepository.getChatsUsuarioByNombre(nombre);
        } else{
            chats = this.chatsRepository.findAll();
        }
        return this.convertirAListaDTO(chats);
    }

    public List<ChatsDTO> listarChatsUsuario(Integer usuarioId){
        List<Chats> chats;
        chats = this.chatsRepository.getChatsUsuario(usuarioId);

        return this.convertirAListaDTO(chats);
    }

    public void asignarChat(Integer chatId, Integer usuarioId){
        Chats chat = this.chatsRepository.findById(chatId).orElse(null);
        if(chat != null){
            chat.setTeleoperadorId(this.usuariosRepository.getById(usuarioId));
            this.chatsRepository.save(chat);
        }
    }

    public void designarChat(Integer chatId){
        Chats chat = this.chatsRepository.findById(chatId).orElse(null);
        if(chat != null){
            //chat.setTeleoperadorId(null);
            chat.setTeleoperadorId(this.usuariosRepository.getAdmin().get(0));
            this.chatsRepository.save(chat);
        }
    }
    public void remove(Integer id) {
        Chats usuario = this.chatsRepository.findById(id).orElse(null);
        this.chatsRepository.delete(usuario);
    }

    public void borrarChat(Integer chatId){
        Chats chat = this.chatsRepository.findById(chatId).orElse(null);
        if(chat != null){
            this.chatsRepository.delete(chat);
        }
    }

    public void crearChat(Integer usuarioId){
        Chats chat = new Chats();
        Usuarios usuario = this.usuariosRepository.getById(usuarioId);
        Date date = new Date();
        chat.setFecha(date);
        chat.setUsuarioId(usuario);
        chat.setTeleoperadorId(this.usuariosRepository.getAdmin().get(0));
        // chat.setTeleoperadorId(null);
        this.chatsRepository.save(chat);

    }

}
