package es.eventsource.dao;

import es.eventsource.entity.Mensajes;
import es.eventsource.entity.MensajesPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MensajesRepository extends JpaRepository<Mensajes, MensajesPK> {

    @Query("SELECT m FROM Mensajes m WHERE m.mensajesPK.chatId = :id")
    public List<Mensajes> getMensajesById(Integer id);

}
