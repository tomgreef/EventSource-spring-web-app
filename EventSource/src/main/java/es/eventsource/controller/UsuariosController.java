package es.eventsource.controller;

import es.eventsource.dto.EventosDTO;
import es.eventsource.service.EventosService;
import es.eventsource.service.UsuariosService;
import es.eventsource.vo.FiltroEventos;
import es.eventsource.vo.FiltroUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import es.eventsource.dto.UsuariosDTO;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class UsuariosController {
    UsuariosService usuariosService;
    EventosService eventosService;

    @Autowired
    public void setEventosService(EventosService eventosService) {
        this.eventosService = eventosService;
    }

    @Autowired
    public void setUsuariosService(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    @GetMapping("/login")
    public String goLogin() {
        return "login";
    }

    @GetMapping("/signUp")
    public String goSignUp() {
        return "signUp";
    }

    // AUTENTICACIÓN
    @PostMapping("/autenticar")
    public String doAutenticar(HttpSession session, @RequestParam("email") String email,
                               @RequestParam("password") String password, Model model) {
        String strError, goTo = "/login";
        UsuariosDTO usuario;

        if (email == null || email.isEmpty() ||
                password == null || password.isEmpty()) {  // Error de autenticación por email o clave
            // vacíos o nulos.
            strError = "Error de autenticación: alguno de los valores está vacío";
            model.addAttribute("error", strError);

        } else { //El usuario sí está en la base de datos
            usuario = this.usuariosService.findByEmailAndPassword(email, password);
            if (usuario == null) { //La contraseña introducida es incorrecta
                strError = "Los datos son incorrectos";
                model.addAttribute("error", strError);
            } else { //Login correcto
                session.setAttribute("usuario", usuario);
                goTo = "/index";

                // Redireccionamos por rol
                if (usuario.getRol() == 4) // Admin
                    return this.doListarEventos(model, session);
            }
        }
        return goTo;
    }

    @GetMapping("/desautenticar")
    public String doExit(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // USUARIOS
    @GetMapping("/listarUsuarios")
    public String doListar (Model model, HttpSession session){
        FiltroUsuarios filtro = new FiltroUsuarios();
        return this.doFiltrar(filtro, model, session);
    }

    @PostMapping("/filtrarUsuarios")
    public String doFiltrar (@ModelAttribute("filtro") FiltroUsuarios filtro, Model model, HttpSession session) {
        String strTo;

        UsuariosDTO admin = (UsuariosDTO) session.getAttribute("usuario");

        if (admin == null || admin.getRol() != 4) {
            model.addAttribute("error", "Usuario sin permisos");
            strTo = "login.jsp";
        } else { // El usuario sí está autenticado
            List<UsuariosDTO> usuarios = this.usuariosService.listarClientes(filtro.getNombre(), filtro.getApellidos());
            model.addAttribute("usuarios", usuarios);
            model.addAttribute("filtro", filtro);
            strTo = "/usuarios";
        }
        return "/usuarios";
    }

    @GetMapping("/borrarUsuario/{id}")
    public String doBorrar(@PathVariable("id") Integer id, HttpSession session, Model model) {
        UsuariosDTO admin = (UsuariosDTO) session.getAttribute("usuario");

        if (admin == null || admin.getRol() != 4) {
            model.addAttribute("error", "Usuario sin permisos");
            return "signUp";
        } else {
            this.usuariosService.remove(id);
            return "redirect:/listarUsuarios";
        }
    }

    @GetMapping("/editarAgregarUsuario")
    public String doAgregar(HttpSession session) {
        return "signUp";
    }

    @GetMapping("/editarAgregarUsuario/{id}")
    public String doEditarAgregar(@PathVariable("id") Integer id, Model model, HttpSession session) {
        String strTo = "/signUp";
        UsuariosDTO admin = (UsuariosDTO) session.getAttribute("usuario");

        if (admin == null || admin.getRol() != 4) {
            model.addAttribute("error", "Usuario sin permisos");
            strTo = "/login";
        } else {
            if (id != null) { // Es editar cliente
                UsuariosDTO usuario = this.usuariosService.find(new Integer(id));
                model.addAttribute("usuario", usuario);
            }
        }
        return strTo;
    }

    @PostMapping("/usuarioGuardar")
    public String doGuardar(@RequestParam("nombre") String nombre,
                            @RequestParam("apellidos") String apellidos,
                            @RequestParam("email") String email,
                            @RequestParam("domicilio") String domicilio,
                            @RequestParam("ciudad") String ciudad,
                            @RequestParam("edad") Integer edad,
                            @RequestParam("sexo") Integer sexo,
                            @RequestParam("password") String password,
                            @RequestParam("rol") Integer rol,
                            @RequestParam("id") String id,
                            HttpSession session) {
        String strTo = "";
        UsuariosDTO usuario = new UsuariosDTO();

        if (!id.isEmpty())
            usuario.setUsuarioId(new Integer(id));
        usuario.setNombre(nombre);
        usuario.setApellidos(apellidos);
        usuario.setEmail(email);
        usuario.setDomicilio(domicilio);
        usuario.setCiudad(ciudad);
        usuario.setEdad(edad);
        usuario.setSexo(sexo);
        usuario.setPassword(password);
        usuario.setRol(rol);

        this.usuariosService.save(usuario);

        UsuariosDTO admin = (UsuariosDTO) session.getAttribute("usuario");
        if (admin != null && admin.getRol() == 4) {
            strTo = "listarUsuarios";
        }

        return "redirect:/" + strTo;
    }

    // EVENTOS
    @GetMapping("/listarEventos")
    public String doListarEventos (Model model, HttpSession session){
        FiltroEventos filtro = new FiltroEventos();
        return this.doFiltarEventos(filtro, model, session);
    }

    @PostMapping("filtrarEventos")
    public String doFiltarEventos (@ModelAttribute("filtro") FiltroEventos filtro, Model model, HttpSession session){
        String strTo = "/eventos";
        UsuariosDTO admin = (UsuariosDTO) session.getAttribute("usuario");

        if (admin == null || admin.getRol() == 0 || admin.getRol() == 2 || admin.getRol() == 3) {
            // Excluimos usuarios, analistas y teleoperadores
            model.addAttribute("error", "Usuario sin permisos");
            strTo = "/login";
        } else {
            List<EventosDTO> eventos = this.eventosService.listarEventos(filtro.getTitulo(), filtro.getCoste());
            model.addAttribute("eventos", eventos);
            model.addAttribute("filtro", filtro);

            if (admin.getRol() == 4) {
                strTo = "/eventosAdmin";
            }
        }
        return strTo;
    }

    @GetMapping("/editarAgregarEvento")
    public String doAgregarEvento (HttpSession session) {
        return "newEvent";
    }

    @GetMapping("/editarAgregarEvento/{id}")
    public String doEditarAgregarEvento (@PathVariable("id") Integer id, Model model, HttpSession session) {
        String strTo = "/newEvent";
        UsuariosDTO admin = (UsuariosDTO) session.getAttribute("usuario");

        if (admin == null || admin.getRol() == 0 || admin.getRol() == 2 || admin.getRol() == 3) {
            // Excluimos usuarios, analistas y teleoperadores
            model.addAttribute("error", "Usuario sin permisos");
            strTo = "/login";
        } else {
            if (id != null) { // Es editar cliente
                EventosDTO evento = this.eventosService.find(id);
                model.addAttribute("evento", evento);
            }
        }
        return strTo;
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
                            @RequestParam("aforo") int aforo,
                            @RequestParam("filas") Integer filas,
                            @RequestParam("columnas") Integer columnas,
                            @RequestParam("entradasMaximas") int entradasMaximas,
                            @RequestParam("coste") double coste,
                            @RequestParam("id") Integer id,
                            HttpSession session) throws ParseException {
        String strTo = "";
        EventosDTO evento = new EventosDTO();

        if (id != 0)
            evento.setEventoId(id);
        evento.setTitulo(titulo);
        evento.setDescripcion(descripcion);
        Date dateFecha = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
        Date dateFechaLimite =new SimpleDateFormat("yyyy-MM-dd").parse(fechaLimite);
        evento.setFecha(dateFecha);
        evento.setFechaLimite(dateFechaLimite);
        evento.setAforo(aforo);
        evento.setFilas(filas);
        evento.setColumnas(columnas);
        evento.setEntradasMaxima(entradasMaximas);
        evento.setCoste(coste);

        this.eventosService.save(evento);

        UsuariosDTO admin = (UsuariosDTO) session.getAttribute("usuario");
        if (admin != null && admin.getRol() == 4) {
            strTo = "listarEventos";
        }

        return "redirect:/" + strTo;
    }


}