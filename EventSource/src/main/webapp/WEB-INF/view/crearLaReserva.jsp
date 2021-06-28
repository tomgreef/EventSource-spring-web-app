<%--
    Document   : crearReserva
    Created on : 27-may-2021, 13:09:05
    Author     : Kevin
--%>
<%@ page import="es.eventsource.dto.EventosDTO" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%
    EventosDTO evento = (EventosDTO) request.getAttribute("evento");
    Integer fila = request.getAttribute("fila")==null?0:(Integer) request.getAttribute("fila");
    Integer columna = request.getAttribute("columna")==null?0:(Integer) request.getAttribute("columna");
%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/styles/normalize.css">
    <link rel="stylesheet" href="/styles/style.css">
</head>

<body>
<jsp:include page="navBar.jsp" />
<h2 class="centrar">Crear reserva para evento: </h2>
<h1 class="titulo centrar"><%= evento.getTitulo()%></h1>
<h3 class="centrar"><%=evento.getDescripcion()%></h3>

<div class="crearUyE-square">
    <form action="/guardarReservaHecha" method="post">
        <input type="hidden" value="<%=evento.getEventoId()%>" name="idEvento">
        <input type="hidden" value="<%= evento.getEventoId() %>" name="id">
        <% if (evento.getFilas() != 0 && evento.getColumnas() != 0) { %>
        <p style="color: white">Eligir asientos:</p>
        <table width="100%">
            <% for (int i = 0; i < evento.getFilas(); i++) { %>
            <tr>
                <% for (int j = 0; j < evento.getColumnas(); j++) {%>
                <td><input type="checkbox" name="asientoCheckboxes"<%if(fila-1==i && columna-1==j){%> checked <%}%> value="<%= i+1%>/<%= j+1%>"> - <%= i + 1 %> <%= j + 1%></td>
                <% } %>
            </tr>

            <%
                }
            %>
        </table>
        <input type="submit" value="OK" class="boton"/>
        <% }else {  %>
        <h2>Confirmar reserva <h2>
            <input type="submit" value="Reservar" class="boton" style="width: 20vh"/>
                <% } %>
    </form>
</div>
</body>
</html>