package es.eventsource.service;

import es.eventsource.dao.EventosRepository;
import es.eventsource.dto.EventosDTO;
import es.eventsource.entity.Eventos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventosService {
    EventosRepository eventosRepository;

    @Autowired
    public void setEventosRepository(EventosRepository eventosRepository) {
        this.eventosRepository = eventosRepository;
    }

    protected static List<EventosDTO> convertirAListaDTO(List<Eventos> lista) {

        if (lista != null) {
            List<EventosDTO> listaDTO = new ArrayList<>();
            lista.forEach((evento) -> {
                listaDTO.add(evento.getDTO());
            });
            return listaDTO;
        } else {
            return null;
        }
    }

    public List<EventosDTO> listarEventos(String titulo, double coste) {
        List<Eventos> eventos;

        if (titulo.length() > 0) {
            if (coste > 0.0) {
                eventos = this.eventosRepository.filterTituloCoste(titulo, coste);
            } else {
                eventos = this.eventosRepository.filterTitulo(titulo);
            }
        } else {
            if (coste > 0.0) {
                eventos = this.eventosRepository.filterCoste(coste);
            } else {
                eventos = this.eventosRepository.findAll();
            }
        }

        return this.convertirAListaDTO(eventos);
    }

    public List<EventosDTO> listarTodosLosEventos() {
        List<Eventos> eventos;
        eventos = this.eventosRepository.findAll();
        return this.convertirAListaDTO(eventos);
    }


    public void save(EventosDTO eventosDTO) {
        Eventos evento;

        if (eventosDTO.getEventoId() == null) {
            evento = new Eventos();
        } else {
            evento = this.eventosRepository.findById(eventosDTO.getEventoId()).orElse(new Eventos());
        }

        evento.setTitulo(eventosDTO.getTitulo());
        evento.setDescripcion(eventosDTO.getDescripcion());
        evento.setFecha(eventosDTO.getFecha());
        evento.setFechaLimite(eventosDTO.getFechaLimite());
        evento.setAforo(eventosDTO.getAforo());
        evento.setFilas(eventosDTO.getFilas());
        evento.setColumnas(eventosDTO.getColumnas());
        evento.setEntradasMaxima(eventosDTO.getEntradasMaxima());
        evento.setCoste(eventosDTO.getCoste());

        this.eventosRepository.save(evento);
    }

    public EventosDTO find(Integer id) {
        Eventos evento = this.eventosRepository.findById(id).orElse(null);
        if (evento != null) {
            return evento.getDTO();
        } else {
            return null;
        }
    }

    public void remove(Integer id) {
        Eventos evento = this.eventosRepository.findById(id).orElse(null);
        this.eventosRepository.delete(evento);
    }
}
