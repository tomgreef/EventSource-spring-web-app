/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.eventsource.service;

import es.eventsource.dao.UsuariosRepository;
import es.eventsource.dto.UsuariosDTO;
import es.eventsource.entity.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tomvg
 */

@Service
public class UsuariosService {
    private UsuariosRepository usuariosRepository;

    @Autowired
    public void setUsuariosRepository(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    protected List<UsuariosDTO> convertirAListaDTO(List<Usuarios> lista) {

        if (lista != null) {
            List<UsuariosDTO> listaDTO = new ArrayList<>();
            lista.forEach((cliente) -> {
                listaDTO.add(cliente.getDTO());
            });
            return listaDTO;
        } else {
            return null;
        }
    }

    public List<UsuariosDTO> listarClientes(String nombre, String apellidos) {
        List<Usuarios> usuarios;

        if (nombre.length() > 0) {
            if (apellidos.length() > 0) {
                usuarios = this.usuariosRepository.filterNombreApellidos(nombre, apellidos);
            } else {
                usuarios = this.usuariosRepository.filterNombre(nombre);
            }
        } else {
            if (apellidos.length() > 0) {
                usuarios = this.usuariosRepository.filterApellidos(apellidos);
            } else {
                usuarios = this.usuariosRepository.findAll();
            }
        }

        return this.convertirAListaDTO(usuarios);
    }

    public UsuariosDTO find(Integer id) {
        Usuarios usuario = this.usuariosRepository.findById(id).orElse(null);
        if (usuario != null) {
            return usuario.getDTO();
        } else {
            return null;
        }
    }

    public void remove(Integer id) {
        Usuarios usuario = this.usuariosRepository.findById(id).orElse(null);
        this.usuariosRepository.delete(usuario);
    }

    public UsuariosDTO findByEmailAndPassword(String email, String password) {
        Usuarios usuario = this.usuariosRepository.findByEmailAndPassword(email, password);
        if(usuario != null)
            return usuario.getDTO();
        else
            return null;
    }

    public void save(UsuariosDTO usuarioDTO) {
        Usuarios usuario;

        if(usuarioDTO.getUsuarioId() == null) {
            usuario = new Usuarios();
        } else {
            usuario = this.usuariosRepository.findById(usuarioDTO.getUsuarioId()).orElse(new Usuarios());
        }

        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellidos(usuarioDTO.getApellidos());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setDomicilio(usuarioDTO.getDomicilio());
        usuario.setCiudad(usuarioDTO.getCiudad());
        usuario.setEdad(usuarioDTO.getEdad());
        usuario.setSexo(usuarioDTO.getSexo());
        usuario.setPassword(usuarioDTO.getPassword());
        usuario.setRol(usuarioDTO.getRol());

        this.usuariosRepository.save(usuario);
    }
}
