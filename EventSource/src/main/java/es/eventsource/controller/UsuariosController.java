package es.eventsource.controller;

import es.eventsource.dto.UsuariosDTO;
import es.eventsource.service.UsuariosService;
import es.eventsource.vo.FiltroUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UsuariosController {
    UsuariosService usuariosService;

    @Autowired
    public void setUsuariosService(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    @GetMapping("/login")
    public String goLogin() {
        return "/login";
    }

    @GetMapping("/signUp")
    public String goSignUp() {
        return "/signUp";
    }

    // AUTENTICACIÓN
    @RequestMapping("/autenticar")
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
                    goTo = "redirect:/listarEventos";
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
    public String doListar(Model model, HttpSession session) {
        FiltroUsuarios filtro = new FiltroUsuarios();
        return this.doFiltrar(filtro, model, session);
    }

    @PostMapping("/filtrarUsuarios")
    public String doFiltrar(@ModelAttribute("filtro") FiltroUsuarios filtro, Model model, HttpSession session) {
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
                            HttpSession session,
                            Model model) {
        String strTo = "/";
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
            strTo = "/listarUsuarios";
        } else {
            return this.doAutenticar(session, email, password, model);
        }

        return "redirect:" + strTo;
    }
}