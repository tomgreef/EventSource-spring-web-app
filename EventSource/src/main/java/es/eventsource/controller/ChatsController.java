package es.eventsource.controller;

import es.eventsource.dto.ChatsDTO;
import es.eventsource.dto.UsuariosDTO;
import es.eventsource.service.ChatsService;
import es.eventsource.vo.FiltroChats;
import es.eventsource.vo.FiltroUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ChatsController {

    private ChatsService chatsService;

    @Autowired
    public void setChatsService(ChatsService chatsService) {
        this.chatsService = chatsService;
    }

    @GetMapping("/listarChats")
    public String doListar(HttpSession session, Model model){
        FiltroChats filtro = new FiltroChats();
        return this.doFiltrar(filtro, session, model);
    }

    @PostMapping("/filtrarChats")
    public  String doFiltrar(@ModelAttribute("filtro") FiltroChats filtro, HttpSession session, Model model) {
        String strError, goTo = "chats";
        UsuariosDTO usuario = (UsuariosDTO)session.getAttribute("usuario");
        if(usuario != null){ //El usuario está autenticado
            List<ChatsDTO> chats;
            if(usuario.getRol() == 3) {
                chats = this.chatsService.listarChatsTeleoperador(filtro.getNombre());
                model.addAttribute("chats", chats);
                model.addAttribute("filtro", filtro);
            } else {
                chats = this.chatsService.listarChatsUsuario(usuario.getUsuarioId());
            }
            model.addAttribute("chats", chats);
        } else { //No está logeado y no puede ver los chats
            strError = "Hay que loggearse para ver los chats";
            model.addAttribute("error", strError);
            goTo = "/login";
        }

        return goTo;
    }

    @GetMapping("/borrarChat/{id}")
    public String doBorrar(@PathVariable("id") Integer id, HttpSession session, Model model) {
        UsuariosDTO teleoperador = (UsuariosDTO) session.getAttribute("usuario");

        if(teleoperador == null || teleoperador.getRol() != 3){
            model.addAttribute("error", "Usuario sin permisos");
            return "signUp";
        } else {
            this.chatsService.borrarChat(id);
            return "redirect:/listarChats";
        }
    }

    @GetMapping("/designarTeleoperador/{id}")
    String doDesignar(@PathVariable("id") Integer id, HttpSession session, Model model){
        UsuariosDTO teleoperador = (UsuariosDTO) session.getAttribute("usuario");

        if(teleoperador == null || teleoperador.getRol() != 3){
            model.addAttribute("error", "Usuario sin permisos");
            return "signUp";
        } else {
            this.chatsService.designarChat(id);
            return "redirect:/listarChats";
        }
    }

    @GetMapping("/asignarTeleoperador/{idChat}/{idUsuario}")
    String doAsignar(@PathVariable("idChat") Integer idChat, @PathVariable("idUsuario") Integer idUsuario, HttpSession session, Model model){
        UsuariosDTO teleoperador = (UsuariosDTO) session.getAttribute("usuario");

        if(teleoperador == null || teleoperador.getRol() != 3){
            model.addAttribute("error", "Usuario sin permisos");
            return "signUp";
        } else {
            this.chatsService.asignarChat(idChat, idUsuario);
            return "redirect:/listarChats";
        }
    }

    @GetMapping("/crearChat")
    String doCrear(HttpSession session, Model model){
        UsuariosDTO usuario = (UsuariosDTO) session.getAttribute("usuario");
        String goTo;
        if(usuario != null) {
            this.chatsService.crearChat(usuario.getUsuarioId());
            //this.chatsService.crearChat();
            goTo = "redirect:/listarChats";
        } else {
            goTo = "index";
        }
        return goTo;

    }
}
