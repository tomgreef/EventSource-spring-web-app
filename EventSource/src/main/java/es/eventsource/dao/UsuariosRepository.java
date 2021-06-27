package es.eventsource.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import es.eventsource.entity.Usuarios;

import java.util.List;

public interface UsuariosRepository extends JpaRepository<Usuarios, Integer> {

    @Query("SELECT a FROM Usuarios a WHERE a.email = :email AND a.password = :password")
    public Usuarios findByEmailAndPassword(String email, String password) ;

    @Query("SELECT a FROM Usuarios a WHERE a.rol = 3")
    public List<Usuarios> getTeleoperador() ;

    @Query("SELECT a FROM Usuarios a WHERE a.rol = 4")
    public List<Usuarios> getAdmin() ;

    @Query("SELECT a FROM Usuarios a WHERE a.nombre LIKE CONCAT('%',:nombre, '%') ")
    public List<Usuarios> filterNombre(String nombre);

    @Query("SELECT a FROM Usuarios a WHERE a.apellidos LIKE CONCAT('%',:apellidos, '%')")
    public List<Usuarios> filterApellidos(String apellidos) ;

    @Query("SELECT a FROM Usuarios a WHERE a.nombre LIKE CONCAT('%',:nombre, '%') AND a.apellidos LIKE CONCAT('%',:apellidos, '%')")
    public List<Usuarios> filterNombreApellidos(String nombre, String apellidos) ;
}
