<%@page import="java.util.List"%>
<%@ page import="es.eventsource.dto.MensajesDTO" %>
<%@ page import="es.eventsource.dto.UsuariosDTO" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Mensajes</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/styles/normalize.css">
    <link rel="stylesheet" href="/styles/style.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
</head>
<%
    List<MensajesDTO> mensajes = (List)request.getAttribute("mensajes");
    UsuariosDTO usuario = (UsuariosDTO)session.getAttribute("usuario");
    Integer chatId = (Integer)request.getAttribute("chatId");
%>
<body>
<jsp:include page="navBar.jsp" />

<p class="titulo" style="margin-left: 5%;"> Mensajes </p>
<div class="messages-square">
    <table class="tablaUsuarios">
        <thead>
            <tr>
                <th>Usuario</th>
                <th>Mensaje</th>
            </tr>
        </thead>
        <tbody>
        <%
            for (MensajesDTO mensaje: mensajes){
        %>
        <tr>
            <td class="sinFondo"><%=mensaje.getUsuarios().getNombre() %></td>
            <td class="sinFondo"><%=mensaje.getMensaje() %></td>
            <%
                }
            %>
        </tr>

        <form action="/enviarMensaje" method="POST">
            <tr>
            <td class="sinFondo"></td>
                <td class="sinFondo"><input class="campo-mensaje" type="text" placeholder="Escribe aqu&iacute; tu mensaje..." name="mensaje"/></td>
                <input hidden name="chatId" value="<%=chatId %>"/>
                <td class="sinFondo"><input class="boton" type="submit" value="Enviar"/></td>
            </tr>
        </form>
        </tbody>
    </table>
</div>



<!--<a href="RedactarMensaje?chatId=<%=chatId %>" class="boton" >Redactar mensaje</a>-->

<jsp:include page="footer.jsp" />
</body>
</html>
