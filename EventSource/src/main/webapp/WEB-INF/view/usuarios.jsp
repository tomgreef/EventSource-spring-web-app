<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@page import="java.util.List"%>
<%@ page import="es.eventsource.dto.UsuariosDTO" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Usuarios</title>

        <link rel="stylesheet" href="styles/normalize.css">
        <link rel="stylesheet" href="styles/style.css">
    </head>
    <%
        List<UsuariosDTO> lista = (List) request.getAttribute("usuarios");
    %>
    <body>
        <jsp:include page="navBar.jsp" />   
        <div class="container">
            <form:form action="/filtrarUsuarios" method="POST" modelAttribute="filtro">
                <div class="columnas">
                    <div class="columna">
                        <form:input class="campo" type="text" placeholder="Nombre" path="nombre"/>
                        <form:input class="campo" type="text" placeholder="Apellidos" path="apellidos"/>
                    </div>
                    <div class="columna">
                        <input type="submit" value="Filtrar" class="boton"/>
                    </div>
                    <div class="columna">
                        <a href="/signUp" class="boton">Registrar</a>
                    </div>
                </div>
            </form:form>
            <table class="tablaUsuarios">
                <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Nombre</th>
                        <th scope="col">Apellidos</th>
                        <th scope="col">Domicilio</th>
                        <th scope="col">Ciudad</th>
                        <th scope="col">Edad</th>
                        <th scope="col">Sexo</th>
                        <th scope="col">Rol</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (UsuariosDTO u : lista) {
                    %>
                    <tr>
                        <td><%= u.getUsuarioId()%></td>
                        <td><%= u.getNombre()%></td>
                        <td><%= u.getApellidos()%></td>
                        <td><%= u.getDomicilio()%></td>
                        <td><%= u.getCiudad()%></td>
                        <td><%= u.getEdad()%></td>
                        <%
                            if (u.getSexo() == 0) {
                        %>
                        <td>Hombre</td>
                        <%
                        } else {
                        %>
                        <td>Mujer</td>
                        <%
                            }
                        %>
                        <%
                            String [] listaRols = new String[]{"Usuario", "Creador", "Analista", "Teleoperador", "Administrador"};
                        %>
                        <td><%= listaRols[u.getRol()]%></td>
                        <td class="sinFondo pl-3"><a href="editarAgregarUsuario/<%= u.getUsuarioId()%>" class="boton boton-peque">Editar</a></td>
                        <td class="sinFondo"><a href="borrarUsuario/<%= u.getUsuarioId()%>" class="boton-rojo boton-peque">Borrar</a></td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
        <jsp:include page="footer.jsp" />   
    </body>
</html>
