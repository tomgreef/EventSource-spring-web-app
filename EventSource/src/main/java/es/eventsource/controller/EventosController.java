package es.eventsource.controller;

import es.eventsource.dto.EventosDTO;
import es.eventsource.dto.UsuariosDTO;
import es.eventsource.service.EventosService;
import es.eventsource.vo.FiltroEventos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class EventosController {
    EventosService eventosService;

    @Autowired
    public void setEventosService(EventosService eventosService) {
        this.eventosService = eventosService;
    }


}
