<%@ page import="es.eventsource.dto.UsuariosDTO" %><%--
  Created by IntelliJ IDEA.
  User: tomvg
  Date: 13/06/2021
  Time: 18:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    UsuariosDTO usuario = (UsuariosDTO) session.getAttribute("usuario");
    if(usuario != null){
        switch (usuario.getRol()) {
            case 1: // Creador de eventos
%>
<nav>
    <div class="container columnas ptb-3 navbar">
        <div class="columna">
            <a href="/" class="objeto-col enlace-negro">
                <span class="logo">Eventsource</span>
            </a>
            <a href="/" class="objeto-col enlace-negro">Home</a>
            <a href="listarEventos" class="objeto-col enlace-negro">Ver eventos</a>
            <a href="ListarMisEventos" class="objeto-col enlace-negro">Mis eventos</a>
        </div>

        <div class="columna">
            <a href="/desautenticar" class="objeto-col boton">
                Logout
            </a>
        </div>
    </div>
</nav>
<%
        break;
    case 2: // Analista
%>
<nav>
    <div class="container columnas ptb-3 navbar">
        <div class="columna">
            <a href="/" class="objeto-col enlace-negro">
                <span class="logo">Eventsource</span>
            </a>
            <a href="/" class="objeto-col enlace-negro">Home</a>
            <a href="listarEventos" class="objeto-col enlace-negro">Ver eventos</a>
        </div>
        <div class="columna">
            <a href=/desautenticar" class="objeto-col boton">
                Logout
            </a>
        </div>
    </div>
</nav>
<%
        break;
    case 3: // Teleoperador
%>
<nav>
    <div class="container columnas ptb-3 navbar">
        <div class="columna">
            <a href="/" class="objeto-col enlace-negro">
                <span class="logo">Eventsource</span>
            </a>
            <a href="/" class="objeto-col enlace-negro">Home</a>
            <a href="ChatListar" class="objeto-col enlace-negro">Chats</a>
            <a href="listarEventos" class="objeto-col enlace-negro">Ver eventos</a>
            <a href="ListarMisEventos" class="objeto-col enlace-negro">Mis eventos</a>
        </div>
        <div class="columna">
            <a href="/desautenticar" class="objeto-col boton">
                Logout
            </a>
        </div>
    </div>
</nav>
<%
        break;
    case 4: // Admin

%>
<nav>
    <div class="container columnas ptb-3 navbar">
        <div class="columna">
            <a href="/" class="objeto-col enlace-negro">
                <span class="logo">Eventsource</span>
            </a>
            <a href="/" class="objeto-col enlace-negro">Home</a>
            <a href="listarUsuarios" class="objeto-col enlace-negro">Usuarios</a>
            <a href="listarEventos" class="objeto-col enlace-negro">Eventos</a>
        </div>

        <div class="columna">
            <a href="/desautenticar" class="objeto-col boton">
                Log Out
            </a>
        </div>
    </div>
</nav>
<%        break;
    default: // Usuario
%>
<nav>
    <div class="container columnas ptb-3 navbar">
        <div class="columna">
            <a href="/" class="objeto-col enlace-negro">
                <span class="logo">Eventsource</span>
            </a>
            <a href="/" class="objeto-col enlace-negro">Home</a>
            <a href="listarEventos" class="objeto-col enlace-negro">Ver eventos</a>
            <a href="ListarMisEventos" class="objeto-col enlace-negro">Mis eventos</a>
            <a href="ChatListar" class="objeto-col enlace-negro">Chats</a>
            <a href="ChatCrear" class="objeto-col enlace-negro">Crear Chat</a>
        </div>

        <div class="columna">
            <a href="/desautenticar" class="objeto-col boton">
                Logout
            </a>
        </div>
    </div>
</nav>
<%
    }
}else {
%>
<nav>
    <div class="container columnas ptb-3 navbar">
        <div class="columna">
            <a href="/" class="objeto-col enlace-negro">
                <span class="logo">Eventsource</span>
            </a>
            <a href="/" class="objeto-col enlace-negro">Home</a>
        </div>
        <div class="columna">
            <a href="login" class="objeto-col boton">
                Login
            </a>
        </div>
    </div>
</nav>
<%
    }
%>
