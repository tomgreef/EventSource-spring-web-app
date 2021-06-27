package es.eventsource.controller;

import es.eventsource.dto.EventosDTO;
import es.eventsource.dto.ReservasDTO;
import es.eventsource.dto.UsuariosDTO;
import es.eventsource.entity.Reservas;
import es.eventsource.service.EventosService;
import es.eventsource.service.ReservasService;
import es.eventsource.vo.FiltroEventos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.xml.ws.http.HTTPException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        model.addAttribute("eventosConReservas",
                reservasService.getAsistedAndAsisting(usuario.getUsuarioId()));
        model.addAttribute("esPantallaDeMisEventos",true);
        return "/eventos";
    }

    @PostMapping("/guardarReservaHecha")
    public String guardarReservaHecha(
            @RequestParam("asientoCheckboxes") String[] checkBoxesAsientos,
            @RequestParam("idEvento") Integer eventoID,
            HttpSession session,Model model) {
        UsuariosDTO usuario = (UsuariosDTO) session.getAttribute("usuario");
        ReservasDTO reservasDTO = new ReservasDTO();
        reservasDTO.setUsuarioId(usuario.getUsuarioId());
        reservasDTO.setEventoId(eventoID);
        if(checkBoxesAsientos != null && checkBoxesAsientos.length > 0)
        {
            for (String checkBox : checkBoxesAsientos)
            {
                String[] parts = checkBox.split("/");
                String fila = parts[0];
                String columna = parts[1];

                reservasDTO.setAsientoColumna(Integer.parseInt(columna));
                reservasDTO.setAsientoFila(Integer.parseInt(fila));
                this.reservasService.save(reservasDTO);
            }
        }
        else
        {
            reservasDTO.setAsientoColumna(0);
            reservasDTO.setAsientoFila(0);
        }

        this.reservasService.save(reservasDTO);

        model.addAttribute("eventos", eventosService.listarTodosLosEventos());
        return "redirect:/ListarEventos";
    }

/*
    @GetMapping("/borrarEvento/{id}")
    public String doBorrarEvento(@PathVariable("id") Integer id, HttpSession session, Model model) {
        UsuariosDTO admin = (UsuariosDTO) session.getAttribute("usuario");

        if (admin == null || admin.getRol() != 4) {
            model.addAttribute("error", "Usuario sin permisos");
            return "/login";
        } else {
            this.eventosService.remove(id);
            return this.doListarEventos(model, session);
        }
    }

    @PostMapping("/eventoGuardar")
    public String doGuardarEvento(@RequestParam("titulo") String titulo,
                                  @RequestParam("descripcion") String descripcion,
                                  @RequestParam("fecha") String fecha,
                                  @RequestParam("fechaLimite") String fechaLimite,
                                  @RequestParam("aforo") Integer aforo,
                                  @RequestParam("filas") Integer filas,
                                  @RequestParam("columnas") Integer columnas,
                                  @RequestParam("entradasMaximas") Integer entradasMaximas,
                                  @RequestParam("coste") Double coste,
                                  @RequestParam("id") Integer id,
                                  HttpSession session, Model model) throws ParseException {
        String strTo = "/";
        UsuariosDTO admin = (UsuariosDTO) session.getAttribute("usuario");

        if (admin == null || admin.getRol() == 0 || admin.getRol() == 2 || admin.getRol() == 3) {
            // Excluimos usuarios, analistas y teleoperadores
            model.addAttribute("error", "Usuario sin permisos");
            return "/login";
        }
        Date dateFecha = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
        Date dateFechaLimite = new SimpleDateFormat("yyyy-MM-dd").parse(fechaLimite);
        EventosDTO evento = new EventosDTO();

        if (id != 0)
            evento.setEventoId(id);
        evento.setTitulo(titulo);
        evento.setDescripcion(descripcion);
        evento.setFecha(dateFecha);
        evento.setFechaLimite(dateFechaLimite);
        evento.setAforo(aforo);
        evento.setFilas(filas);
        evento.setColumnas(columnas);
        evento.setEntradasMaxima(entradasMaximas);
        evento.setCoste(coste);

        // Comprobamos validez del evento //
        // Reserva tiene que ser anterior al avento
        if (dateFecha.before(dateFechaLimite)) {
            evento.setEventoId(0);
            model.addAttribute("evento", evento);
            model.addAttribute("error", "La fecha limite para reservas tiene que ser anterior a la fecha del evento");
            return "/newEvent";
        }

        this.eventosService.save(evento);

        if (admin.getRol() == 4) {
            strTo = "/ListarEventos";
        }

        return "redirect:" + strTo;
    }
*/
}
