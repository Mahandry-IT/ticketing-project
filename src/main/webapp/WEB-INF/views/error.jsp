<%@page import="mg.itu.error.ErrorHandler"%>
<%
    ErrorHandler ex = (ErrorHandler) request.getAttribute("exception");
    java.io.StringWriter sw = new java.io.StringWriter();
    java.io.PrintWriter pw = new java.io.PrintWriter(sw);
    if (ex != null) {
        ex.getException().printStackTrace(pw);
    }
    String stackTrace = sw.toString();
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Page d'erreur</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
<div class="container">
    <div class="error-container">
        <div class="error-header">
            <div class="error-code"><%= ex != null ? ex.getCode() : 500 %></div>
            <h1 class="error-title"><%= ex != null ? ex.getTitre() : "Erreur interne du serveur" %></h1>
        </div>

        <div class="error-message">
            <%= ex != null ? ex.getException().getMessage() : "Une erreur inattendue s'est produite lors du traitement de votre requête. Notre équipe technique a été notifiée et travaille à résoudre le problème." %>
        </div>

        <% if (ex != null && stackTrace != null && !stackTrace.isEmpty()) { %>
        <div class="stack-trace">
            <%= stackTrace.replace(System.getProperty("line.separator"), "<br>").replace(" ", "&nbsp;") %>
        </div>
        <% } %>

        <div class="error-actions">
            <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Retour à l'accueil</a>
        </div>
    </div>

    <div class="footer">
        <p>&copy; 2025 Votre Application. Tous droits réservés.</p>
    </div>
</div>
</body>
</html>