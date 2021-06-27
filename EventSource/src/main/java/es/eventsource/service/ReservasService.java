package es.eventsource.service;

import es.eventsource.dao.EventosRepository;
import es.eventsource.dao.ReservasRepository;
import es.eventsource.dao.UsuariosRepository;
import es.eventsource.dto.EventosDTO;
import es.eventsource.dto.ReservasDTO;
import es.eventsource.dto.UsuariosDTO;
import es.eventsource.entity.Eventos;
import es.eventsource.entity.Reservas;
import es.eventsource.entity.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReservasService {
    ReservasRepository reservasRepository;
    EventosRepository eventosRepository;
    UsuariosRepository usuariosRepository;

    @Autowired
    private void setEventosRepository(EventosRepository eventosRepository) {
        this.eventosRepository = eventosRepository;
    }

    @Autowired
    private void setUsuariosRepository(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    @Autowired
    private void setReservasRepository(ReservasRepository reservasRepository) {
        this.reservasRepository = reservasRepository;
    }

    protected List<ReservasDTO> convertirAListaDTO(List<Reservas> lista) {

        if (lista != null) {
            List<ReservasDTO> listaDTO = new ArrayList<>();
            lista.forEach((reserva) -> {
                listaDTO.add(reserva.getDTO());
            });
            return listaDTO;
        } else {
            return null;
        }
    }


    public void save(ReservasDTO reservasDTO) {
        Reservas reserva;
        reserva = new Reservas();
/*
        if (reservasDTO.getEventoId() == null) {
            reserva = new Reservas();
        } else {
            reserva = this.reservasRepository.findById(reservasDTO.getEventoId()).orElse(new Reservas());
        }
*/
        reserva.setAsientoColumna(reservasDTO.getAsientoColumna());
        reserva.setAsientoFila(reservasDTO.getAsientoFila());
        reserva.setUsuarioId(usuariosRepository.findById(reservasDTO.getUsuarioId()).get());
        reserva.setEventoId(eventosRepository.findById(reservasDTO.getEventoId()).get());
        reserva.setCantidad(1);

        this.reservasRepository.save(reserva);
    }

    public ReservasDTO find(Integer id) {
        Reservas reserva = this.reservasRepository.findById(id).orElse(null);
        if (reserva != null) {
            return reserva.getDTO();
        } else {
            return null;
        }
    }



    public Map<Integer,EventosDTO> getAsistedAndAsisting(Integer usuarioId)
    {
        return convertirAMapaDTO(reservasRepository.getAsistedAndAsisting(usuarioId));
    }

    private Map<Integer,EventosDTO> convertirAMapaDTO(Map<Integer,Eventos> asistedAndAsisting) {
        Map<Integer,EventosDTO> resultado = new HashMap<>();
        for (Map.Entry<Integer,Eventos> entry : asistedAndAsisting.entrySet()) {
            resultado.put(entry.getKey(),entry.getValue().getDTO());
        }
        return resultado;
    }
}