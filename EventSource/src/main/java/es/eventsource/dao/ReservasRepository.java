package es.eventsource.dao;

import es.eventsource.dto.EventosDTO;
import es.eventsource.entity.Eventos;
import es.eventsource.entity.Reservas;
import es.eventsource.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservasRepository extends JpaRepository<Reservas, Integer> {

    @Query("SELECT e FROM Reservas r join Eventos e on " +
            "r.eventoId.eventoId = e.eventoId where r.usuarioId.usuarioId = :id order by r.reservaId desc ")
    List<Eventos> getReservasAsistedAndAsisting(Integer id);


    @Query("SELECT r FROM Reservas r join Eventos e on " +
            "r.eventoId.eventoId = e.eventoId where r.usuarioId.usuarioId = :id order by r.reservaId desc ")
    List<Reservas> getEventosAsistedAndAsisting(Integer id);


}