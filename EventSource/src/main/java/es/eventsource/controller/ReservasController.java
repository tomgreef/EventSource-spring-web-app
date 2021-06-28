package es.eventsource.controller;

import es.eventsource.dto.EventosDTO;
import es.eventsource.dto.ReservasDTO;
import es.eventsource.dto.UsuariosDTO;
import es.eventsource.service.EventosService;
import es.eventsource.service.ReservasService;
import es.eventsource.vo.FiltroEventos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class ReservasController {
    EventosService eventosService;

    ReservasService reservasService;

    @Autowired
    public void setReservasService(ReservasService reservasService) {
        this.reservasService = reservasService;
    }

    @Autowired
    public void setEventosService(EventosService eventosService) {
        this.eventosService = eventosService;
    }


    @GetMapping("/CrearReserva/{idEvento}")
    public String doCrearReserva(@PathVariable("idEvento") Integer idEvento, Model model) {
        EventosDTO evento = eventosService.find(idEvento);
        model.addAttribute("evento",evento);
        return "/crearLaReserva";
    }

    @GetMapping("/ListarMisEventos")
    public String listarMisEventos(Model model,HttpSession session) {
        UsuariosDTO usuario = (UsuariosDTO) session.getAttribute("usuario");
        model.addAttribute("eventos", reservasService.getEventosAsistedAndAsisting(usuario.getUsuarioId()));
        model.addAttribute("reservas",reservasService.getReservasAsistedAndAsisting(usuario.getUsuarioId()));
        model.addAttribute("filtro",new FiltroEventos());
        model.addAttribute("esPantallaDeMisEventos",true);
        return "/eventos";
    }

    @PostMapping("/guardarReservaHecha")
    public String guardarReservaHecha(
            @RequestParam ("asientoCheckboxes") Optional<String[]> checkBoxesAsientos,
            @RequestParam("idEvento") Integer eventoID,
            HttpSession session,Model model) {
        UsuariosDTO usuario = (UsuariosDTO) session.getAttribute("usuario");

        if(checkBoxesAsientos.isPresent())
        {
            for (String checkBox : checkBoxesAsientos.get())
            {
                String[] parts = checkBox.split("/");
                String fila = parts[0];
                String columna = parts[1];

                ReservasDTO reservasDTO = new ReservasDTO();
                reservasDTO.setUsuarioId(usuario.getUsuarioId());
                reservasDTO.setEventoId(eventoID);

                reservasDTO.setAsientoColumna(Integer.parseInt(columna));
                reservasDTO.setAsientoFila(Integer.parseInt(fila));
                this.reservasService.save(reservasDTO);
            }
        }
        else
        {
            ReservasDTO reservasDTO = new ReservasDTO();
            reservasDTO.setUsuarioId(usuario.getUsuarioId());
            reservasDTO.setEventoId(eventoID);

            reservasDTO.setAsientoColumna(0);
            reservasDTO.setAsientoFila(0);

            this.reservasService.save(reservasDTO);
        }

        model.addAttribute("eventos", eventosService.listarTodosLosEventos());
        return "redirect:/ListarEventos";
    }


    @GetMapping("/EliminarReserva/{id}")
    public String doEliminarReserva(@PathVariable("id") Integer idReserva, HttpSession session,Model model) {
        this.reservasService.delete(idReserva);

        model.addAttribute("eventos", eventosService.listarTodosLosEventos());

        return "redirect:/ListarEventos";
    }

    //editar
    @GetMapping("/CrearReserva/{idReserva}/{idEvento}")
    public String doEditarReserva(@PathVariable("idReserva") Integer idReserva, @PathVariable("idEvento") Integer idEvento, HttpSession session,Model model) {
        ReservasDTO reserva = this.reservasService.find(idReserva);

        model.addAttribute("columna",reserva.getAsientoColumna());
        model.addAttribute("fila",reserva.getAsientoFila());

        model.addAttribute("evento", eventosService.find(reserva.getEventoId()));

        return "crearLaReserva";
    }
}