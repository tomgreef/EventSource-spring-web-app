<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.eventsource.dto.UsuariosDTO" %>
<%@ page import="es.eventsource.dto.ChatsDTO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>chats</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/styles/normalize.css">
    <link rel="stylesheet" href="/styles/style.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
</head>
<%
    List<ChatsDTO> chats = (List)request.getAttribute("chats");
    UsuariosDTO usuario = (UsuariosDTO)session.getAttribute("usuario");
%>
<body>
<jsp:include page="navBar.jsp" />



<p class="titulo" style="margin-left: 5%;"> Mis conversaciones </p>
<div class="messages-square">
    <%
        if(usuario.getRol()==3){
    %>

    <form:form action="filtrarChats" method="POST" modelAttribute="filtro">
        <table>
            <tr>
                <td class="sinFondo">Filtrar por nombre:</td>
                <td class="sinFondo"><form:input  type="text" placeholder="Nombre" path="nombre"/></td>
                <td class="sinFondo"><input class="boton" type="submit" value="Filtrar"/></td>
            </tr>
        </table>
    </form:form>
    <%
        }
    %>
    <div class="container">
    <table class="tablaUsuarios">
        <thead>
            <tr>
                <th scope="col">Teleoperador</th>
                <%
                    if(usuario.getRol()== 3){
                %>
                <th scope="col">Cliente</th>
                <%
                } else {
                %>
                <th scope="col">Fecha de creación del chat</th>
                <%
                    }
                %>
                <th scope="col"></th>
                <th scope="col"></th>
                <th scope="col"></th>
            </tr>
        </thead>
        <tbody>
        <%
            for (ChatsDTO chat: chats){
        %>
            <tr>
                <%
                    //if(chat.getTeleoperadorId()!=null ){
                    if(chat.getTeleoperadorId().getRol()==3){ //Comprueba que el teleoperador_id de Chat es un teleoperador
                %>
                <td><%=chat.getTeleoperadorId().getNombre() %></td>
                <%
                } else {
                %>
                <td>Teleoperador no asignado</td>
                <%
                    }
                %>
                <%
                    if(usuario.getRol()== 3){
                %>
                <td><%=chat.getUsuarioId().getNombre() %></td>
                <%
                } else {
                %>
                <td><%=chat.getFecha().toString() %></td>
                <%
                    }
                %>
                <td class="sinFondo"><a href="mostrarMensajes/<%= chat.getChatId()%>" class="boton-chats-mostrar"> Mostrar chat</a></td>
                <%
                    if(usuario.getRol()== 3){
                        //if(chat.getTeleoperadorId()!=null){
                        if(chat.getTeleoperadorId().getRol()==3){ //Si teleoperador_id de chat es 3 sigifica que está asignado
                %>
                <td class="sinFondo"><a href="designarTeleoperador/<%= chat.getChatId()%>" class="boton-chats-desasignar"> Desasignar<br/>teleoperador</a></td>
                <%
                } else {
                %>
                <td class="sinFondo"><a href="asignarTeleoperador/<%= chat.getChatId()%>/<%= usuario.getUsuarioId()%>" class="boton-chats-asignar"> Asignarme<br/>este chat</a></td>
                <%
                    }
                %>
                <td class="sinFondo"><a href="borrarChat/<%= chat.getChatId()%>" class="boton-chats-borrar"> Borrar chat</a></td>
                <%
                    }
                %>
                <%
                    }
                %>

            </tr>
        </tbody>
    </table>
    </div>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>
