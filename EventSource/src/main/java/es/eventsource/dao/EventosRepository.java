package es.eventsource.dao;

import es.eventsource.entity.Eventos;
import es.eventsource.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventosRepository extends JpaRepository<Eventos, Integer> {

    @Query("SELECT a FROM Eventos a WHERE a.titulo LIKE CONCAT('%',:titulo, '%') AND a.coste <= :coste")
    List<Eventos> filterTituloCoste(String titulo, Double coste);

    @Query("SELECT a FROM Eventos a WHERE a.titulo LIKE CONCAT('%',:titulo, '%')")
    List<Eventos> filterTitulo(String titulo);

    @Query("SELECT a FROM Eventos a WHERE a.coste <= :coste")
    List<Eventos> filterCoste(double coste);
}
