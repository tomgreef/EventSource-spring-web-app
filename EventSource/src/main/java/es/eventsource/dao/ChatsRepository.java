package es.eventsource.dao;

import es.eventsource.entity.Chats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatsRepository extends JpaRepository<Chats, Integer> {

    @Query("SELECT c FROM Chats c WHERE c.usuarioId.usuarioId = :id")
    public List<Chats> getChatsUsuario(Integer id);

    @Query("SELECT c FROM Chats c WHERE lower(c.usuarioId.nombre) LIKE lower(CONCAT('%',:nombre, '%')) OR lower(c.teleoperadorId.nombre) LIKE lower(CONCAT('%',:nombre, '%')) ORDER BY c.chatId ASC ")
    public List<Chats> getChatsUsuarioByNombre(String nombre);

    @Query("SELECT c FROM Chats c ORDER BY c.chatId ASC")
    public List<Chats> getChatsOrdenado();
}
