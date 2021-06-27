package es.eventsource.controller;

import es.eventsource.dto.ChatsDTO;
import es.eventsource.dto.MensajesDTO;
import es.eventsource.dto.UsuariosDTO;
import es.eventsource.entity.MensajesPK;
import es.eventsource.service.MensajesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class MensajesController {

    private MensajesService mensajesService;

    @Autowired
    public void setMensajesService(MensajesService mensajesService) {
        this.mensajesService = mensajesService;
    }

    @GetMapping("/mostrarMensajes/{idChat}")
    public String doListar(@PathVariable("idChat") Integer id, HttpSession session, Model model){
        String goTo = "mensajes";
        List<MensajesDTO> mensajes = this.mensajesService.listarMensajes(id);
        model.addAttribute("chatId", id);
        model.addAttribute("mensajes", mensajes);
        return goTo;
    }

    @PostMapping("/enviarMensaje")
    public String doEnviar(@RequestParam("chatId") Integer chatId,

                           @RequestParam("mensaje") String contenido,
                           HttpSession session,
                           Model model){
        String strError, goTo = "/mostrarMensajes/"+chatId;
        UsuariosDTO usuario = (UsuariosDTO) session.getAttribute("usuario");
        Integer usuarioId = usuario.getUsuarioId();
        if(usuario != null){
            if(usuario.getRol() == 3 || usuario.getUsuarioId()==usuarioId) { //Es un teleoperador o el usuario que ha creado el chat
                this.mensajesService.create(contenido, chatId, usuarioId);
            }
        } else { //No está logeado y no puede mandar mensajes
            strError = "Hay que loggearse para ver los chats";
            model.addAttribute("error", strError);
            goTo = "/login";
        }

        return "redirect:" + goTo;
    }
}
