/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.eventsource.entity;

import es.eventsource.dto.UsuariosDTO;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author tomvg
 */
@Entity
@Table(name = "USUARIOS")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u")
        , @NamedQuery(name = "Usuarios.findByUsuarioId", query = "SELECT u FROM Usuarios u WHERE u.usuarioId = :usuarioId")
        , @NamedQuery(name = "Usuarios.findByEmail", query = "SELECT u FROM Usuarios u WHERE u.email = :email")
        , @NamedQuery(name = "Usuarios.findByPassword", query = "SELECT u FROM Usuarios u WHERE u.password = :password")
        , @NamedQuery(name = "Usuarios.findByNombre", query = "SELECT u FROM Usuarios u WHERE u.nombre = :nombre")
        , @NamedQuery(name = "Usuarios.findByApellidos", query = "SELECT u FROM Usuarios u WHERE u.apellidos = :apellidos")
        , @NamedQuery(name = "Usuarios.findByDomicilio", query = "SELECT u FROM Usuarios u WHERE u.domicilio = :domicilio")
        , @NamedQuery(name = "Usuarios.findByCiudad", query = "SELECT u FROM Usuarios u WHERE u.ciudad = :ciudad")
        , @NamedQuery(name = "Usuarios.findByEdad", query = "SELECT u FROM Usuarios u WHERE u.edad = :edad")
        , @NamedQuery(name = "Usuarios.findBySexo", query = "SELECT u FROM Usuarios u WHERE u.sexo = :sexo")
        , @NamedQuery(name = "Usuarios.findByRol", query = "SELECT u FROM Usuarios u WHERE u.rol = :rol")})
public class Usuarios implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioId")
    private List<Estudios> estudiosList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "USUARIO_ID")
    private Integer usuarioId;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @Column(name = "EMAIL", nullable = false)
    private String email;
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @Column(name = "NOMBRE", nullable = false)
    private String nombre;
    @Basic(optional = false)
    @Column(name = "APELLIDOS", nullable = false)
    private String apellidos;
    @Basic(optional = false)
    @Column(name = "DOMICILIO", nullable = false)
    private String domicilio;
    @Basic(optional = false)
    @Column(name = "CIUDAD", nullable = false)
    private String ciudad;
    @Basic(optional = false)
    @Column(name = "EDAD", nullable = true)
    private int edad;
    @Basic(optional = false)
    @Column(name = "SEXO", nullable = true)
    private int sexo;
    @Basic(optional = false)
    @Column(name = "ROL", nullable = true)
    private int rol;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarios")
    private List<Mensajes> mensajesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioId")
    private List<Reservas> reservasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teleoperadorId")
    private List<Chats> chatsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioId")
    private List<Chats> chatsList1;

    public Usuarios() {
    }

    public Usuarios(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Usuarios(Integer usuarioId, String email, String nombre, String apellidos, String domicilio, String ciudad, int edad, int sexo, int rol) {
        this.usuarioId = usuarioId;
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.domicilio = domicilio;
        this.ciudad = ciudad;
        this.edad = edad;
        this.sexo = sexo;
        this.rol = rol;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    @XmlTransient
    public List<Mensajes> getMensajesList() {
        return mensajesList;
    }

    public void setMensajesList(List<Mensajes> mensajesList) {
        this.mensajesList = mensajesList;
    }

    @XmlTransient
    public List<Reservas> getReservasList() {
        return reservasList;
    }

    public void setReservasList(List<Reservas> reservasList) {
        this.reservasList = reservasList;
    }

    @XmlTransient
    public List<Chats> getChatsList() {
        return chatsList;
    }

    public void setChatsList(List<Chats> chatsList) {
        this.chatsList = chatsList;
    }

    @XmlTransient
    public List<Chats> getChatsList1() {
        return chatsList1;
    }

    public void setChatsList1(List<Chats> chatsList1) {
        this.chatsList1 = chatsList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuarioId != null ? usuarioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.usuarioId == null && other.usuarioId != null) || (this.usuarioId != null && !this.usuarioId.equals(other.usuarioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Usuarios[ usuarioId=" + usuarioId + " ]";
    }

    public UsuariosDTO getDTO() {
        UsuariosDTO dto = new UsuariosDTO();

        dto.setUsuarioId(usuarioId);
        dto.setNombre(nombre);
        dto.setApellidos(apellidos);
        dto.setDomicilio(domicilio);
        dto.setCiudad(ciudad);
        dto.setEdad(edad);
        dto.setSexo(sexo);
        dto.setRol(rol);
        dto.setEmail(email);

        return dto;
    }

    @XmlTransient
    public List<Estudios> getEstudiosList() {
        return estudiosList;
    }

    public void setEstudiosList(List<Estudios> estudiosList) {
        this.estudiosList = estudiosList;
    }
}
