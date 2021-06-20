<%@ page import="es.eventsource.dto.UsuariosDTO" %><%--
  Created by IntelliJ IDEA.
  User: tomvg
  Date: 13/06/2021
  Time: 18:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>login</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="styles/normalize.css">
    <link rel="stylesheet" href="styles/style.css">
</head>
<%
    UsuariosDTO usuario = (UsuariosDTO)session.getAttribute("usuario");
    if(usuario != null){
%>
<jsp:forward page="index.jsp"></jsp:forward>
<%
    }
    String strError = (String)request.getAttribute("error");
    if(strError == null) strError = "";
%>
<body>
<div class="login-backround" style="height: 100vh">
    <div class="login-square">
        <h2 class="titulo logo" align="center">Eventsource</h2>
        <form class="formulario" method="POST" action="autenticar">
            <p class="centrar" style="color:red; font-weight: bold"><%= strError %><p>
            <input class="campo" type="text" align="center" placeholder="Email" name="email">
            <input class="campo" type="password" align="center" placeholder="Contrase&ntilde;a" name="password">
            <input class="submit" type="submit" align="center" value="Iniciar sesión">
        </form>
        <div align="center">
            <a href="signUp" class="forgot">¡Registrate aquí!</a>
        </div>
    </div>
</div>
</body>
</html>
