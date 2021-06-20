package es.eventsource.controller;

import es.eventsource.service.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import es.eventsource.dao.UsuariosRepository;
import es.eventsource.dto.UsuariosDTO;
import es.eventsource.entity.Usuarios;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class UsuariosController {
    UsuariosService usuariosService;

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

    @PostMapping("/autenticar")
    public String doAutenticar(HttpSession session, @RequestParam("email") String email,
                               @RequestParam("password") String password, Model model) {
        String strError, goTo = "login";
        UsuariosDTO usuario;

        if (email == null || email.isEmpty() ||
                password == null || password.isEmpty()) {  // Error de autenticación por email o clave
            // vacíos o nulos.
            strError = "Error de autenticación: alguno de los valores está vacío";
            model.addAttribute("error", strError);

        } else { //El usuario sí está en la base de datos
            usuario = this.usuariosService.findByEmailAndPassword(email, password);
            if (usuario == null) { //La contraseña introducida es incorrecta
                strError = "La clave es incorrecta";
                model.addAttribute("error", strError);
            } else { //Login correcto
                session.setAttribute("usuario", usuario);
                goTo = "index";

                // Redireccionamos por rol
                if (usuario.getRol() == 4) // Admin
                    goTo = "eventosAdmin";
            }
        }
        return goTo;
    }

    @GetMapping("/desautenticar")
    public String doExit(HttpSession session) {
        session.invalidate();
        return "redirect:/";
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
            strTo = "ListarUsuarios";
        }

        return "redirect:/" + strTo;
    }

    @GetMapping("borrarUsuario/{id}")
    public String doBorrar(@PathVariable("id") Integer id) {
        this.usuariosService.remove(id);
        return "redirect:/usuarios";
    }
}