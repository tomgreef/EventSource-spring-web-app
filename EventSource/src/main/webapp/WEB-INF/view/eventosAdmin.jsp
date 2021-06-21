<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@ page import="es.eventsource.dto.EventosDTO" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Eventos</title>
        <link rel="stylesheet" href="/styles/normalize.css">
        <link rel="stylesheet" href="/styles/style.css">
    </head>
    <%
        List<EventosDTO> lista = (List) request.getAttribute("eventos");
    %>
    <body>
        <jsp:include page="navBar.jsp" />   
        <div class="container">
            <form:form action="/filtrarEventos" method="POST" modelAttribute="filtro">
                <div class="columnas">
                    <div class="columna">
                        <form:input class="campo" type="text" placeholder="Titulo" path="titulo"/>
                        <form:input class="campo" type="text" placeholder="Coste máximo" path="coste"/>
                    </div>
                    <div class="columna">
                        <input type="submit" value="Filtrar" class="boton"/>
                    </div>
                    <div class="columna">
                        <a href="/editarAgregarEvento" class="boton">Crear evento</a>
                    </div>
                </div>
            </form:form>
            <table class="tablaUsuarios">
                <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Titulo</th>
                        <th scope="col">Descripción</th>
                        <th scope="col">Fecha</th>
                        <th scope="col">Fecha límite</th>
                        <th scope="col">Coste</th>
                        <th scope="col">Aforo</th>
                        <th scope="col">Entradas maxima</th>
                        <th scope="col">Filas</th>
                        <th scope="col">Columnas</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (EventosDTO u : lista) {
                            String fecha = new SimpleDateFormat("dd-MM-yyyy").format(u.getFecha());
                            String fecha_limite = new SimpleDateFormat("dd-MM-yyyy").format(u.getFechaLimite());
                    %>
                    <tr>
                        <td><%= u.getEventoId()%></td>
                        <td><%= u.getTitulo()%></td>
                        <td><%= u.getDescripcion()%></td>
                        <td><%= fecha%></td>
                        <td><%= fecha_limite%></td>
                        <td><%= u.getCoste()%></td>
                        <td><%= u.getAforo()%></td>
                        <td><%= u.getEntradasMaxima()%></td>
                        <% if (u.getFilas() != 0 || u.getColumnas() != 0) {%>
                        <td><%= u.getFilas()%></td>
                        <td><%= u.getColumnas()%></td>
                        <% } else {%>
                        <td>0</td>
                        <td>0</td>
                        <% }%>
                        <td><a href="editarAgregarEvento/<%= u.getEventoId()%>" class="boton boton-peque">Editar</a></td>
                        <td class="sinFondo"><a href="borrarEvento/<%= u.getEventoId()%>" class="boton-rojo boton-peque">Borrar</a></td>
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
