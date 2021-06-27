package es.eventsource.controller;

import es.eventsource.dto.EventosDTO;
import es.eventsource.dto.UsuariosDTO;
import es.eventsource.service.EventosService;
import es.eventsource.vo.FiltroEventos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class EventosController {
    EventosService eventosService;

    @Autowired
    public void setEventosService(EventosService eventosService) {
        this.eventosService = eventosService;
    }

    @GetMapping("/ListarEventos")
    public String doListarEventos(Model model, HttpSession session) {
        FiltroEventos filtro = new FiltroEventos();
        return doFiltarEventos(filtro, model, session);
    }

    @PostMapping("/filtrarEventos")
    public String doFiltarEventos(@ModelAttribute("filtro") FiltroEventos filtro, Model model, HttpSession session) {
        String strTo = "/eventos";
        UsuariosDTO admin = (UsuariosDTO) session.getAttribute("usuario");

        if (admin == null || admin.getRol() == 0 || admin.getRol() == 2 || admin.getRol() == 3) {
            // Excluimos usuarios, analistas y teleoperadores
            model.addAttribute("error", "Usuario sin permisos");
            return "/login";
        }
        List<EventosDTO> eventos = this.eventosService.listarEventos(filtro.getTitulo(), filtro.getCoste());
        model.addAttribute("eventos", eventos);
        model.addAttribute("filtro", filtro);

        if (admin.getRol() == 4) {
            strTo = "/eventosAdmin";
        }

        return strTo;
    }

    @GetMapping("/editarAgregarEvento")
    public String doAgregarEvento(HttpSession session) {
        return "/newEvent";
    }

    @GetMapping("/editarAgregarEvento/{id}")
    public String doEditarAgregarEvento(@PathVariable("id") Integer id, Model model, HttpSession session) {
        UsuariosDTO admin = (UsuariosDTO) session.getAttribute("usuario");

        if (admin == null || admin.getRol() == 0 || admin.getRol() == 2 || admin.getRol() == 3) {
            // Excluimos usuarios, analistas y teleoperadores
            model.addAttribute("error", "Usuario sin permisos");
            return "/login";
        }

        if (id != null) { // Es editar cliente
            EventosDTO evento = this.eventosService.find(id);
            model.addAttribute("evento", evento);
        }
        return "/newEvent";
    }

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

}
